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
package com.cloud.mesh.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.mesh.common.model.ResultMsg;
import com.cloud.mesh.search.entity.DocumentDTO;
import com.cloud.mesh.search.service.IDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * A document Controller
 *
 * @author Benji
 * @date 2019-08-17
 */
@RestController
@RequestMapping("/v7/es/document")
@Api(value = "DocumentController", description = "DocumentController")
public class DocumentController {

    @Autowired
    private IDocumentService documentService;

    @ApiOperation(value = "Index a document using the Index API.")
    @PutMapping(value = "/createDocument/{index}", produces = {"application/json"})
    public ResultMsg<String> createDocument(
            @ApiParam(value = "index", required = true) @PathVariable String index,
            @ApiParam(value = "document", required = true) @RequestBody DocumentDTO document) throws IOException {
        return ResultMsg.ok(documentService.createDocument(index, document));
    }

    @ApiOperation(value = "Index a document using the Index API.")
    @PostMapping(value = "/createDocument/{index}", produces = {"application/json"})
    public ResultMsg<String> createDocument(
            @ApiParam(value = "index", required = true) @PathVariable String index,
            @ApiParam(value = "document", required = true) @RequestBody JSONObject document) throws IOException {
        return ResultMsg.ok(documentService.createDocument(index, document));
    }

    @ApiOperation(value = "Executes a bulk request using the Bulk API.")
    @PostMapping(value = "/batchCreateDocument/{index}", produces = {"application/json"})
    public ResultMsg<Boolean> batchCreateDocument(
            @ApiParam(value = "index", required = true) @PathVariable String index,
            @ApiParam(value = "documents", required = true) @RequestBody List<DocumentDTO> documents) throws IOException {
        return ResultMsg.ok(documentService.batchCreateDocument(index, documents));
    }

    @ApiOperation(value = "Updates a document using the Update API.")
    @PostMapping(value = "/updateDocumentById/{index}", produces = {"application/json"})
    public ResultMsg<Boolean> updateDocumentById(
            @ApiParam(value = "index", required = true) @PathVariable String index,
            @ApiParam(value = "document", required = true) @RequestBody DocumentDTO document) throws IOException {
        return ResultMsg.ok(documentService.updateDocumentById(index, document));
    }


    @ApiOperation(value = "Deletes a document by id using the Delete API.")
    @DeleteMapping(value = "/deleteDocumentById/{index}/{id}", produces = {"application/json"})
    public ResultMsg<Boolean> updateDocumentById(
            @ApiParam(value = "index", required = true) @PathVariable String index,
            @ApiParam(value = "id", required = true) @PathVariable String id) throws IOException {
        return ResultMsg.ok(documentService.deleteDocumentById(index, id));
    }

}
