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
package com.cloud.mesh.quartz.configuration;

import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Execute the actual job of the abstract JDBC job
 *
 * @author willlu.zheng
 * @date 2019-09-16
 */
public abstract class AbstractJdbcJob implements BaseJob {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataSource dataSource;

    private Connection conn;

    public void preExecute() throws SQLException {
        conn = dataSource.getConnection();
    }

    public void postExecute() {
        DbUtils.closeQuietly(conn);
    }

    /**
     * Execute the actual job
     */
    @Override
    public void execute() {
        try {
            this.preExecute();
        } catch (SQLException ex) {
            logger.error("preExecuteError，ErrorMsg={}", ex.getMessage(), ex);
        }
        try {
            conn.setAutoCommit(true);
            this.execute(conn);
        } catch (Exception ex) {
            logger.error("JdbcJobError[{}]，ErrorMsg={}", ex.getMessage(), ex);
        } finally {
            this.postExecute();
        }
    }

    /**
     * Execute the actual job, Handling timed tasks with JDBC
     *
     * @param conn the connection
     */
    public abstract void execute(Connection conn);
}
