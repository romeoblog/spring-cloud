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
import com.cloud.mesh.search.entity.DocumentDTO;
import com.cloud.mesh.search.service.IDocumentService;
import com.cloud.mesh.search.utils.ElasticsearchUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Index a document interface implements service
 *
 * @author Benji
 * @date 2019-08-18
 */
@Service
public class DocumentServiceImpl implements IDocumentService {
    @Override
    public String createDocument(String index, DocumentDTO documentDTO) throws IOException {
        return ElasticsearchUtils.createDocument(documentDTO.getJsonObject(), index);
    }

    @Override
    public String createDocument(String index, JSONObject jsonObject) throws IOException {
        return ElasticsearchUtils.createDocument(jsonObject, index);
    }

    @Override
    public Boolean batchCreateDocument(String index, List<DocumentDTO> batchJsonObject) throws IOException {
        List<JSONObject> jsonObjects = Lists.newArrayListWithCapacity(batchJsonObject.size());
        List<String> ids = Lists.newArrayListWithCapacity(batchJsonObject.size());

        batchJsonObject.stream().forEach(o -> {
            jsonObjects.add(o.getJsonObject());
            ids.add(o.getId());
        });

        ElasticsearchUtils.batchCreateDocument(jsonObjects, index, ids.toArray(new String[]{}));
        return true;
    }

    @Override
    public Boolean updateDocumentById(String index, DocumentDTO documentDTO) throws IOException {
        ElasticsearchUtils.updateDocumentById(documentDTO.getJsonObject(), index, documentDTO.getId());
        return true;
    }

    @Override
    public Boolean deleteDocumentById(String index, String id) throws IOException {
        ElasticsearchUtils.deleteDocumentById(index, id);
        return true;
    }

}
