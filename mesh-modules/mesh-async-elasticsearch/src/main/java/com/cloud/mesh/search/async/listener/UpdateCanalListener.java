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
package com.cloud.mesh.search.async.listener;

import com.cloud.mesh.common.utils.StringUtils;
import com.cloud.mesh.search.async.canal.CanalChangeInfo;
import com.cloud.mesh.search.async.canal.CanalMsgContent;
import com.cloud.mesh.search.async.event.UpdateCanalEvent;
import com.cloud.mesh.search.async.service.ElasticsearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * The update event canal listeners.
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Component
public class UpdateCanalListener extends AbstractCanalListener<UpdateCanalEvent> {
    private static final Logger logger = LoggerFactory.getLogger(UpdateCanalListener.class);

    @Resource
    private ElasticsearchService elasticsearchService;

    @Override
    protected void doSync(String database, String table, String primaryKey, String index, CanalMsgContent rowData) {
        List<CanalChangeInfo> columns = rowData.getDataAfter();
        CanalChangeInfo idColumn = columns.stream().filter(column -> primaryKey.equals(column.getName())).findFirst().orElse(null);
        if (idColumn == null || StringUtils.isBlank(idColumn.getValue())) {
            logger.warn("update_column_find_null_warn update从column中找不到主键,database=" + database + ",table=" + table);
            return;
        }
        logger.info("update_column_id_info update主键id,database=" + database + ",table=" + table + ",id=" + idColumn.getValue());
        Map<String, Object> dataMap = parseColumnsToMap(columns);
        elasticsearchService.update(index, idColumn.getValue(), dataMap);
        logger.info("update_es_info 同步es更新操作成功！database=" + database + ",table=" + table + ",data=" + dataMap);

        logger.info("33333");
    }
}
