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
import com.cloud.mesh.model.search.DocumentVO;

import java.io.IOException;
import java.util.List;

/**
 * Index a document interface service
 *
 * @author Benji
 * @date 2019-08-18
 */
public interface IDocumentService {

    /**
     * Index a document using the Index API.
     *
     * @param index       the index
     * @param documentVO the document dto
     * @return id
     * @throws IOException the IOException
     */
    String createDocument(String index, DocumentVO documentVO) throws IOException;

    /**
     * Index a document using the Index API.
     *
     * @param index      the index
     * @param jsonObject the json object
     * @return id
     * @throws IOException the IOException
     */
    String createDocument(String index, JSONObject jsonObject) throws IOException;

    /**
     * Executes a bulk request using the Bulk API.
     *
     * @param index           the index
     * @param batchJsonObject the batch json object
     * @return b
     * @throws IOException the IOException
     */
    Boolean batchCreateDocument(String index, List<DocumentVO> batchJsonObject) throws IOException;

    /**
     * Updates a document using the Update API.
     *
     * @param index       the index
     * @param documentVO the document dto
     * @return b
     * @throws IOException the IOException
     */
    Boolean updateDocumentById(String index, DocumentVO documentVO) throws IOException;

    /**
     * Deletes a document by id using the Delete API.
     *
     * @param index the index
     * @param id    the id
     * @return b
     * @throws IOException the IOException
     */
    Boolean deleteDocumentById(String index, String id) throws IOException;

}
