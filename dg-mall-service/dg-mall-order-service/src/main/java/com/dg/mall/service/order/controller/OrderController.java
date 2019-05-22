/*
 *  Copyright 2015-2019 dg-mall.com Group.
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
package com.dg.mall.service.order.controller;

import com.dg.mall.common.model.ResultMsg;
import com.dg.mall.model.order.OrderVO;
import com.dg.mall.service.order.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单控制层
 *
 * @author Benji
 * @date 2019-04-28
 */
@RestController
@RequestMapping("/order")
@Slf4j
@Api(value = "OrderController", description = "订单模块")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @ApiOperation(value = "创建订单", notes = "非开放接口，需登陆")
    @PostMapping(value = "/create", produces = {"application/json"})
    public ResultMsg<Boolean> create(@RequestBody OrderVO orderVO) {

        orderService.create(orderVO);

        log.info("创建成功");

        return ResultMsg.ok();
    }

}
