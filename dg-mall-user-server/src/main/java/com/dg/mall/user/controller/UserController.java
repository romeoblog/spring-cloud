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
package com.dg.mall.user.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.dg.mall.platform.common.model.ResultMsg;
import com.dg.mall.entity.Demo;
import com.dg.mall.user.entity.DemoVO;
import com.dg.mall.user.service.IDemoService;
import com.dg.mall.user.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Created with default template
 * Author: Joe Benji
 * Date: 2019-04-03
 * Time: 15:42
 * Description:
 */
@RestController
@RequestMapping("/user")
@Api(value = "HomeController", description = "用户微服务")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${server.port}")
    String serverPort;

    @Autowired
    private IDemoService demoService;

    @Autowired
    private InventoryService inventoryService;

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/index", produces = {"application/json"}, method = RequestMethod.GET)
    public String index() {
        return "success";
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/getInformation1", produces = {"application/json"}, method = RequestMethod.GET)
    public ResultMsg<DemoVO> getInformation1() {

        logger.info("==== 调用服务的端口号为：{} ====", serverPort);

        DemoVO demo = demoService.getInformation(1);

        return ResultMsg.ok(demo);
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/getInformation2", produces = {"application/json"}, method = RequestMethod.GET)
    public ResultMsg<Demo> getInformation2() {
        Demo demo = new Demo().selectById(1);

        return ResultMsg.ok(demo);
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/listInformation1", produces = {"application/json"}, method = RequestMethod.GET)
    public ResultMsg<List<Demo>> listInformation1() {
        return ResultMsg.ok(new Demo().selectAll());
    }

    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/listInformation2", produces = {"application/json"}, method = RequestMethod.GET)
    public ResultMsg<Page<Demo>> listInformation2() {

        Page<Demo> page = new Page<>(1, 10);

        List<Demo> list = new Demo().selectList("age = {0}", 22);

        return ResultMsg.ok(page.setRecords(list));
    }

    @ApiOperation("查看库存")
    @GetMapping("/countInventory/{inventoryId}")
    public ResultMsg<Integer> countInventory(@PathVariable(value = "inventoryId") Integer inventoryId) {
        return inventoryService.countInventory(inventoryId);
    }

}
