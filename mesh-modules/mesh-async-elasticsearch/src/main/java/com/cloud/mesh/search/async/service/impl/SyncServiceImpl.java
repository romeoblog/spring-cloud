/*
 *  Copyright 2019 https://github.com/romeoblog/spring-cloud.git Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.cloud.mesh.search.async.service.impl;

import com.cloud.mesh.search.async.entity.SyncByTableRequest;
import com.cloud.mesh.search.async.mapper.BaseMapperExt;
import com.cloud.mesh.search.async.service.ElasticsearchService;
import com.cloud.mesh.search.async.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 异步动态处理类
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Service
public class SyncServiceImpl implements SyncService, InitializingBean, DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(SyncServiceImpl.class);

    @Autowired
    private DataSource dataSource;
    /**
     * 使用线程池控制并发数量
     */
    private ExecutorService cachedThreadPool;

    @Resource
    private BaseMapperExt baseMapperExt;

    @Resource
    private ElasticsearchService elasticsearchService;


    @Override
    public boolean syncByTable(SyncByTableRequest request) {
        String primaryKey = primaryKeyColumnName(request.getDatabase(), request.getTable());

        long minPK = Optional.ofNullable(request.getFrom()).orElse(baseMapperExt.selectMinPK(primaryKey, request.getDatabase(), request.getTable()));
        long maxPK = Optional.ofNullable(request.getTo()).orElse(baseMapperExt.selectMaxPK(primaryKey, request.getDatabase(), request.getTable()));
        cachedThreadPool.submit(() -> {
            try {
                for (long i = minPK; i < maxPK; i += request.getStepSize()) {
                    batchInsertElasticsearch(request, primaryKey, i, i + request.getStepSize(), request.getTable());
                    LOGGER.info(String.format("当前同步pk=%s，总共total=%s，进度=%s%%", i, maxPK, new BigDecimal(i * 100).divide(new BigDecimal(maxPK), 3, BigDecimal.ROUND_HALF_UP)));
                }
            } catch (Exception e) {
                LOGGER.error("批量转换并插入Elasticsearch异常", e);
            }
        });
        return true;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public void batchInsertElasticsearch(SyncByTableRequest request, String primaryKey, long from, long to, String index) {
        List<Map<String, Object>> dataList = baseMapperExt.selectByPKIntervalLockInShareMode(primaryKey, from, to, request.getDatabase(), request.getTable());
        if (dataList == null || dataList.isEmpty()) {
            return;
        }
        dataList = convertDateType(dataList);
        Map<String, Map<String, Object>> dataMap = dataList.parallelStream().collect(Collectors.toMap(strObjMap -> String.valueOf(strObjMap.get(primaryKey)), map -> map));
        elasticsearchService.batchInsertById(index, dataMap);
    }

    private List<Map<String, Object>> convertDateType(List<Map<String, Object>> source) {
        source.parallelStream().forEach(map -> map.forEach((key, value) -> {
            if (value instanceof Timestamp) {
                map.put(key, LocalDateTime.ofInstant(((Timestamp) value).toInstant(), ZoneId.systemDefault()));
            }
        }));
        return source;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cachedThreadPool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), (ThreadFactory) Thread::new);
    }

    @Override
    public void destroy() throws Exception {
        if (cachedThreadPool != null) {
            cachedThreadPool.shutdown();
        }
    }

    private String primaryKeyColumnName(String database, String table) {
        String primaryKeyColumnName = "";
        try {
            ResultSet primaryKeyResultSet = dataSource.getConnection().getMetaData().getPrimaryKeys(database, null, table);

            while (primaryKeyResultSet.next()) {
                primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
            }

        } catch (Exception ex) {

            LOGGER.error("Get primaryKey ColumnName Error: database={}, table={}", database, table, ex);

        }

        return Optional.ofNullable(primaryKeyColumnName).orElse("id");
    }
}
