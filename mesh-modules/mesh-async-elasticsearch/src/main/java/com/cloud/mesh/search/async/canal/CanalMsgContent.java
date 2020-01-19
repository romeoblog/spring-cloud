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
package com.cloud.mesh.search.async.canal;

import lombok.Data;

import java.util.List;

/**
 * Canal基础信息 包括表名等
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Data
public class CanalMsgContent {

    private String binLogFile;
    private Long binlogOffset;
    private String dbName;
    private String tableName;
    private String eventType;
    private List<CanalChangeInfo> dataBefore;
    private List<CanalChangeInfo> dataAfter;

}
