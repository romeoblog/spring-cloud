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
package com.cloud.mesh.service.mybatis.controller;

import com.cloud.mesh.common.model.ResultMsg;
import com.cloud.mesh.model.mybatis.TestVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MYBATIS栗子 控制层
 *
 * @author willlu.zheng
 * @date 2019-06-20
 */
@RefreshScope
@RestController
@RequestMapping("/refresh")
@Slf4j
@Api(value = "RefreshController", description = "MYBATIS栗子")
public class RefreshController {

    @Value("${user.name}")
    String userName;

    @Value("${user.age}")
    private String userAge;

    @Value("${checkToken}")
    private String checkToken;

    @ApiOperation(value = "getInfo")
    @GetMapping(value = "/getInfo", produces = {"application/json"})
    public ResultMsg<TestVO> getInfo() {
        String content = "user name :" + userName + "; age: " + userAge + "; checkToken: " + checkToken;
        return ResultMsg.error(content);
    }

}
