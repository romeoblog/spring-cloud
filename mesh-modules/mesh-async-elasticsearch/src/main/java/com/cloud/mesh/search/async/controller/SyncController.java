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
package com.cloud.mesh.search.async.controller;

import com.cloud.mesh.common.model.ResultMsg;
import com.cloud.mesh.common.utils.JsonUtils;
import com.cloud.mesh.search.async.entity.SyncByTableRequest;
import com.cloud.mesh.search.async.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author willlu.zheng
 * @date 2019-10-08
 */
@RestController
public class SyncController {
    private static final Logger logger = LoggerFactory.getLogger(SyncController.class);

    @Resource
    private SyncService syncService;

    /**
     * 通过库名和表名全量同步数据
     *
     * @param request 请求参数
     */
    @RequestMapping(value = "/byTable", method = RequestMethod.POST, produces = {"application/json"})
    public ResultMsg syncTable(@Validated @RequestBody SyncByTableRequest request) {
        logger.debug("request_info: " + JsonUtils.toJson(request));
        if (syncService.syncByTable(request)) {
            return ResultMsg.ok();
        } else {
            return ResultMsg.error("同步失败");
        }
    }
}
