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
package com.cloud.mesh.search.async.service;

import com.cloud.mesh.search.async.entity.SyncByTableRequest;

/**
 * @author <a href="mailto:wangchao.star@gmail.com">wangchao</a>
 * @version 1.0
 * @since 2017-08-31 17:48:00
 */
public interface SyncService {
    /**
     * 通过database和table同步数据库
     *
     * @param request 请求参数
     * @return 后台同步进程执行成功与否
     */
    boolean syncByTable(SyncByTableRequest request);


    /**
     * 开启事务的读取mysql并插入到Elasticsearch中（读锁）
     *
     * @param request
     * @param primaryKey
     * @param from
     * @param to
     * @param index
     */
    void batchInsertElasticsearch(SyncByTableRequest request, String primaryKey, long from, long to, String index);
}
