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
package com.cloud.mesh.service.user.controller;

import com.cloud.mesh.common.model.ResultMsg;
import com.cloud.mesh.model.user.AccountVO;
import com.cloud.mesh.service.user.service.IAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 账号控制层
 *
 * @author Benji
 * @date 2019-04-28
 */
@RestController
@RequestMapping("/account")
@Slf4j
@Api(value = "AccountController", description = "用户模块")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @ApiOperation(value = "扣除金额，参与发")
    @PostMapping(path = "/debit", produces = {"application/json"})
    public ResultMsg<Boolean> debit(@RequestBody AccountVO accountVO) {
        accountService.debit(accountVO);

        return ResultMsg.ok();
    }

    @ApiOperation(value = "测试")
    @GetMapping(path = "/test", produces = {"application/json"})
    public ResultMsg<Boolean> test() {
        log.info("test success.");
        return ResultMsg.ok();
    }
}
