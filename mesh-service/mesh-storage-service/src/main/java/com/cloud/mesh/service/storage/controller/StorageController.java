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
package com.cloud.mesh.service.storage.controller;

import com.cloud.mesh.common.model.ResultMsg;
import com.cloud.mesh.service.storage.service.IStorageService;
import com.cloud.mesh.model.storage.StorageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存控制层
 *
 * @author willlu.zheng
 * @date 2019-04-28
 */
@RestController
@RequestMapping("/storage")
@Slf4j
@Api(value = "StorageController", description = "库存模块")
public class StorageController {

    @Autowired
    private IStorageService storageService;

    @ApiOperation(value = "扣库存，参与方")
    @PostMapping(path = "/deduct", produces = {"application/json"})
    public ResultMsg<Boolean> deduct(@RequestBody StorageVO storageVO) {
        storageService.deduct(storageVO);
        log.info("库存扣除成功！");

        return ResultMsg.ok();
    }

}
