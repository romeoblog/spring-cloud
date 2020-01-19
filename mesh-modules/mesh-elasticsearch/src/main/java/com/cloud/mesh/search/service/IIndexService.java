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
package com.cloud.mesh.search.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * Index a document interface service
 *
 * @author willlu.zheng
 * @date 2019-08-17
 */
public interface IIndexService {

    /**
     * Creates an index using the Create Index API.
     *
     * @param index the index
     * @return b
     * @throws IOException the IOException
     */
    Boolean createIndex(String index) throws IOException;

    /**
     * Create or Updates the mappings on an index using the Put Mapping API (Outer JSON format).
     *
     * @param index       the index
     * @param mappingJson the json object
     * @return b
     * @throws IOException the IOException
     */
    Boolean createMapping(String index, JSONObject mappingJson) throws IOException;

    /**
     * Delete an index using the Delete Index API.
     *
     * @param index the index
     * @return b
     * @throws IOException the IOException
     */
    Boolean deleteIndex(String index) throws IOException;

}
