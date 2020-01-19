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

import com.cloud.mesh.search.async.canal.CanalChangeInfo;
import com.cloud.mesh.search.async.canal.CanalMsgContent;
import com.cloud.mesh.search.async.event.AbstractCanalEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractCanalListener<EVENT extends AbstractCanalEvent> implements ApplicationListener<EVENT> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCanalListener.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public void onApplicationEvent(EVENT event) {
        CanalMsgContent entry = event.getEntry();
        String database = entry.getDbName();
        String table = entry.getTableName();

        String primaryKey = primaryKeyColumnName(database, table);

        doSync(database, table, primaryKey, table, entry);

    }

    Map<String, Object> parseColumnsToMap(List<CanalChangeInfo> columns) {
        Map<String, Object> jsonMap = new HashMap<>(columns.size());
        columns.forEach(column -> {
            if (column == null) {
                return;
            }
            jsonMap.put(column.getName(), column.getValue());
        });
        return jsonMap;
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

    protected abstract void doSync(String database, String table, String primaryKey, String index, CanalMsgContent rowData);


}
