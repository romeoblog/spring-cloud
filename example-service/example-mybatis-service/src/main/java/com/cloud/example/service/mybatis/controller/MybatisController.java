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
package com.cloud.example.service.mybatis.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.example.common.model.ResultMsg;
import com.cloud.example.model.mybatis.TestVO;
import com.cloud.example.service.mybatis.service.IMybatisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * MYBATIS栗子 控制层
 *
 * @author Benji
 * @date 2019-06-20
 */
@RestController
@RequestMapping("/mybatis")
@Slf4j
@Api(value = "MybatisController", description = "MYBATIS栗子")
public class MybatisController {

    @Autowired
    private IMybatisService mybatisService;

    @ApiOperation(value = "MybatisPlus栗子 - 获取单条信息")
    @GetMapping(value = "/getTest/{id}", produces = {"application/json"})
    public ResultMsg<TestVO> getTest(@ApiParam(value = "Id", required = true) @PathVariable Integer id) {
        return ResultMsg.ok(mybatisService.getTest(id));
    }

    @ApiOperation(value = "MybatisPlus栗子 - 获取多条信息")
    @GetMapping(value = "/listTest", produces = {"application/json"})
    public ResultMsg<List<TestVO>> listTest() {
        return ResultMsg.ok(mybatisService.listTest());
    }

    @ApiOperation(value = "MybatisPlus栗子 - 获取多条信息（分页）")
    @GetMapping(value = "/listRecord", produces = {"application/json"})
    public ResultMsg<Page<TestVO>> listRecord(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "条数", required = true) @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize) {
        return ResultMsg.ok(mybatisService.listRecord(pageNum, pageSize));
    }

    @ApiOperation(value = "XML栗子 - 获取多条信息（分页）")
    @GetMapping(value = "/listRecord2", produces = {"application/json"})
    public ResultMsg<Page<TestVO>> listRecord2(
            @ApiParam(value = "页码", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "条数", required = true) @RequestParam(value = "pageSize", defaultValue = "2") Integer pageSize) {
        return ResultMsg.ok(mybatisService.listRecord2(pageNum, pageSize));
    }

    @ApiOperation(value = "MybatisPlus栗子 - 更新信息")
    @PostMapping(value = "/updateTest", produces = {"application/json"})
    public ResultMsg<Boolean> updateTest(
            @ApiParam(value = "实体信息提交参数", required = true) @RequestBody TestVO testVO) {
        return ResultMsg.ok(mybatisService.updateTest(testVO));
    }

    @ApiOperation(value = "MybatisPlus栗子 - 删除单条信息")
    @DeleteMapping(value = "/deleteTest/{id}", produces = {"application/json"})
    public ResultMsg<Boolean> deleteTest(@ApiParam(value = "Id", required = true) @PathVariable Integer id) {
        return ResultMsg.ok(mybatisService.deleteTest(id));
    }

    @ApiOperation(value = "MybatisPlus栗子 - 新增信息")
    @PutMapping(value = "/insertTest", produces = {"application/json"})
    public ResultMsg<Boolean> insertTest(
            @ApiParam(value = "实体信息提交参数", required = true) @RequestBody TestVO testVO) {
        return ResultMsg.ok(mybatisService.insertTest(testVO));
    }



}
