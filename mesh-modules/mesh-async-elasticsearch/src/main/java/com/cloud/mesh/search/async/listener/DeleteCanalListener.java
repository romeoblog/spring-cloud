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
import com.cloud.mesh.search.async.event.DeleteCanalEvent;
import com.cloud.mesh.search.async.service.ElasticsearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * The delete event canal listeners.
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Component
public class DeleteCanalListener extends AbstractCanalListener<DeleteCanalEvent> {
    private static final Logger logger = LoggerFactory.getLogger(DeleteCanalListener.class);

    @Resource
    private ElasticsearchService elasticsearchService;

    @Override
    protected void doSync(String database, String table,String primaryKey, String index, CanalMsgContent rowData) {
        List<CanalChangeInfo> columns = rowData.getDataBefore();
        CanalChangeInfo idColumn = columns.stream().filter(column -> primaryKey.equals(column.getName())).findFirst().orElse(null);
        if (idColumn == null || StringUtils.isBlank(idColumn.getValue())) {
            logger.warn("delete_column_find_null_warn insert从column中找不到主键,database=" + database + ",table=" + table);
            return;
        }
        logger.info("delete_column_id_info delete主键id,database=" + database + ",table=" + table + ",id=" + idColumn.getValue());
        elasticsearchService.deleteById(index, idColumn.getValue());
        logger.info("delete_es_info 同步es删除操作成功！database=" + database + ",table=" + table + ",id=" + idColumn.getValue());

    }
}
