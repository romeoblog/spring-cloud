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

import com.cloud.mesh.common.model.ResultMsg;
import com.cloud.mesh.model.search.QueryRequestVO;
import com.cloud.mesh.search.service.IQueryService;
import com.cloud.mesh.search.utils.ElasticsearchPage;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * A Query that matches documents matching Controller
 *
 * @author Benji
 * @date 2019-08-17
 */
@RestController
@RequestMapping("/v7/es/query")
@Api(value = "QueryController", description = "QueryController")
public class QueryController {

    @Autowired
    private IQueryService queryService;

    @ApiOperation(value = "Retrieves a document by id using the Get API.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = -1, message = "error")})
    @GetMapping(value = "/getDocumentById/{index}/{id}", produces = {"application/json"})
    public ResultMsg<Map<String, Object>> createDocument(
            @ApiParam(value = "index", required = true) @PathVariable String index,
            @ApiParam(value = "id", required = true) @PathVariable String id,
            @ApiParam(value = "fields") @RequestParam(value = "fields", required = false) String fields) throws IOException {
        return ResultMsg.ok(queryService.getDocumentById(index, id, fields));
    }

    @Deprecated
    @ApiOperation(value = "A Query that matches documents matching using the matches List API.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = -1, message = "error")})
    @GetMapping(value = "/queryForList/{index}", produces = {"application/json"})
    public ResultMsg<List<Map<String, Object>>> queryForList(
            @ApiParam(value = "index", required = true) @PathVariable String index,
            @ApiParam(value = "termName") @RequestParam(value = "termName", required = false) String termName,
            @ApiParam(value = "startTime") @RequestParam(value = "startTime", defaultValue = "0") Long startTime,
            @ApiParam(value = "endTime") @RequestParam(value = "endTime", defaultValue = "0") Long endTime,
            @ApiParam(value = "size") @RequestParam(value = "size", required = false) Integer size,
            @ApiParam(value = "fields") @RequestParam(value = "fields", required = false) String fields,
            @ApiParam(value = "sortField") @RequestParam(value = "sortField", required = false) String sortField,
            @ApiParam(value = "matchPhrase") @RequestParam(value = "matchPhrase", defaultValue = "false") Boolean matchPhrase,
            @ApiParam(value = "highlightField") @RequestParam(value = "highlightField", required = false) String highlightField,
            @ApiParam(value = "matchStr") @RequestParam(value = "matchStr", required = false) String matchStr) throws IOException {
        return ResultMsg.ok(queryService.queryForList(index, termName, startTime, endTime, size, fields, sortField, matchPhrase, highlightField, matchStr));
    }

    @ApiOperation(value = "A Query that matches documents matching using the matches List API.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = -1, message = "error")})
    @PostMapping(value = "/queryForList", produces = {"application/json"})
    public ResultMsg<List<Map<String, Object>>> queryForList(@Validated @RequestBody QueryRequestVO data) throws IOException {
        return ResultMsg.ok(queryService.queryForList(data));
    }

    @Deprecated
    @ApiOperation(value = "A Query that matches documents matching using the matches Page API.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = -1, message = "error")})
    @GetMapping(value = "/queryForPage/{index}", produces = {"application/json"})
    public ResultMsg<ElasticsearchPage> queryForPage(
            @ApiParam(value = "index", required = true) @PathVariable String index,
            @ApiParam(value = "currentPage") @RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
            @ApiParam(value = "pageSize") @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @ApiParam(value = "termName") @RequestParam(value = "termName", required = false) String termName,
            @ApiParam(value = "startTime") @RequestParam(value = "startTime", defaultValue = "0") Long startTime,
            @ApiParam(value = "endTime") @RequestParam(value = "endTime", defaultValue = "0") Long endTime,
            @ApiParam(value = "fields") @RequestParam(value = "fields", required = false) String fields,
            @ApiParam(value = "sortField") @RequestParam(value = "sortField", required = false) String sortField,
            @ApiParam(value = "matchPhrase") @RequestParam(value = "matchPhrase", defaultValue = "false") Boolean matchPhrase,
            @ApiParam(value = "highlightField") @RequestParam(value = "highlightField", required = false) String highlightField,
            @ApiParam(value = "matchStr") @RequestParam(value = "matchStr", required = false) String matchStr) throws IOException {
        return ResultMsg.ok(queryService.queryForPage(index, currentPage, pageSize, termName, startTime, endTime, fields, sortField, matchPhrase, highlightField, matchStr));
    }

    @ApiOperation(value = "A Query that matches documents matching using the matches Page API.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok"), @ApiResponse(code = -1, message = "error")})
    @PostMapping(value = "/queryForPage", produces = {"application/json"})
    public ResultMsg<ElasticsearchPage> queryForPage(@Validated @RequestBody QueryRequestVO data) throws IOException {
        return ResultMsg.ok(queryService.queryForPage(data));
    }

}
