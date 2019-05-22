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
package com.dg.mall.order.controller;

import com.dg.mall.common.model.ResultMsg;
import com.dg.mall.order.entity.DemoVO;
import com.dg.mall.order.service.InventoryService;
import com.dg.mall.order.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Created with default template
 * Author: Joe Benji
 * Date: 2019-04-03
 * Time: 15:40
 * Description:
 */
@RestController
@RequestMapping("/order")
@Api(value = "OrderController", description = "订单微服务")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private InventoryService inventoryService;

    @ApiOperation("获取用户信息")
    @GetMapping("/getUser")
    public ResultMsg<DemoVO> getUser() {
        System.out.println("======== getUser Controller " + Thread.currentThread().getThreadGroup() + " - " + Thread.currentThread().getId() + " - " + Thread.currentThread().getName());
        return userService.getInformation1();
    }

    @ApiOperation("获取订单详情")
    @GetMapping("/getOrderDetail/{orderId}")
    public String getOrderDetail(@PathVariable Long orderId) {
        System.out.println("======== getOrderDetail Controller " + Thread.currentThread().getThreadGroup() + " - " + Thread.currentThread().getId() + " - " + Thread.currentThread().getName());
        System.out.println(orderId);
        return "success";
    }

    @ApiOperation("查看库存")
    @GetMapping("/countInventory/{inventoryId}")
    public Integer countInventory(@PathVariable(value = "inventoryId") Integer inventoryId) {
        System.out.println("======== countInventory Controller " + Thread.currentThread().getThreadGroup() + " - " + Thread.currentThread().getId() + " - " + Thread.currentThread().getName());
        System.out.println(inventoryId);
        return inventoryService.countInventory(inventoryId);
    }
}