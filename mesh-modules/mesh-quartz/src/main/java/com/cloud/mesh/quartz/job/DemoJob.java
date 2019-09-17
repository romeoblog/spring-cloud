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
package com.cloud.mesh.quartz.job;

import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.common.utils.SQLRunner;
import com.cloud.mesh.entity.Demo;
import com.cloud.mesh.quartz.configuration.AbstractJdbcJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

/**
 * demo job
 *
 * @author willlu.zheng
 * @date 2019-09-16
 */
@Slf4j
@Service("demoJob")
public class DemoJob extends AbstractJdbcJob {

    @Override
    public void execute(Connection conn) {
        try {
            String sql = "select * from demo";

            List<Demo> result = SQLRunner.queryBeanListWithCamelCase(conn, sql, Demo.class);

            logger.info("The DemoJob result={}", JacksonUtils.toJson(result));

        } catch (Exception ex) {
            logger.error("The DemoJob Exception Error=[{}]", ex.getMessage(), ex);
        }

    }
}
