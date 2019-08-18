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
package com.cloud.mesh.service.business.controller;

import com.cloud.mesh.common.model.ResultMsg;
import com.cloud.mesh.service.business.service.IBusinessService;
import com.cloud.mesh.model.order.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo Controller
 *
 * @author Benji
 * @date 2019-04-28
 */
@Slf4j
@RestController
@RequestMapping("/business")
@Api(value = "BusinessController", description = "业务模块")
public class BusinessController {

    @Autowired
    private IBusinessService businessService;

    @ApiOperation(value = "业务发起方", notes = "开放接口")
    @PostMapping(value = "/purchase", produces = {"application/json"})
    public ResultMsg<Boolean> purchase(@RequestBody OrderVO orderVO) {

        businessService.purchase(orderVO);

        return ResultMsg.ok();
    }
}
