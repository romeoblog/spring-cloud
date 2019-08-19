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
import com.cloud.mesh.search.service.IIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Index a document Controller
 *
 * @author Benji
 * @date 2019-08-17
 */
@RestController
@RequestMapping("/v7/es/index")
@Api(value = "IndexController", description = "IndexController")
public class IndexController {

    @Autowired
    private IIndexService indexService;

    @ApiOperation(value = "Creates an index using the Create Index API.")
    @PostMapping(value = "/createIndex/{index}", produces = {"application/json"})
    public ResultMsg<Boolean> createIndex(@ApiParam(value = "index", required = true) @PathVariable String index) throws IOException {
        return ResultMsg.ok(indexService.createIndex(index));
    }

    @ApiOperation(value = "Creates Or Updates the mappings on an index using the Put Mapping API.")
    @PutMapping(value = "/createMapping/{index}", produces = {"application/json"})
    public ResultMsg<Boolean> createMapping(
            @ApiParam(value = "index", required = true) @PathVariable String index,
            @ApiParam(value = "jsonObject", required = true) @RequestBody JSONObject jsonObject) throws IOException {
        return ResultMsg.ok(indexService.createMapping(index, jsonObject));
    }

    @ApiOperation(value = " Deletes an index using the Delete Index API.")
    @DeleteMapping(value = "/deleteIndex/{index}", produces = {"application/json"})
    public ResultMsg<Boolean> deleteIndex(@ApiParam(value = "index", required = true) @PathVariable String index) throws IOException {
        return ResultMsg.ok(indexService.deleteIndex(index));
    }


}
