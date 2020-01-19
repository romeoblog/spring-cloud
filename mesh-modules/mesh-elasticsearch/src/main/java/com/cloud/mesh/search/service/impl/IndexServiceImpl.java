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
package com.cloud.mesh.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.mesh.search.service.IIndexService;
import com.cloud.mesh.search.utils.ElasticsearchUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Index a document interface implement service
 *
 * @author willlu.zheng
 * @date 2019-08-17
 */
@Service
public class IndexServiceImpl implements IIndexService {

    @Override
    public Boolean createIndex(String index) throws IOException {
        return ElasticsearchUtils.createIndex(index);
    }

    @Override
    public Boolean createMapping(String index, JSONObject mappingJson) throws IOException {
        return ElasticsearchUtils.createMapping(index, mappingJson);
    }

    @Override
    public Boolean deleteIndex(String index) throws IOException {
        return ElasticsearchUtils.deleteIndex(index);
    }

}
