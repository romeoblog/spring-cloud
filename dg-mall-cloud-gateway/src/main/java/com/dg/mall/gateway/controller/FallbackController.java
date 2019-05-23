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
package com.dg.mall.gateway.controller;

import com.dg.mall.common.enums.ResultCode;
import com.dg.mall.common.model.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 熔断处理
 *
 * @author Benji
 * @date 2019-04-03
 */
@RestController
@Slf4j
public class FallbackController {
    @GetMapping("/fallback")
    public ResultMsg fallback() {
        log.error("====> 进入熔断机制 <====");
        ResultMsg resultMsg = ResultMsg.create().status(ResultCode.EXCEPTION);
        return resultMsg;
    }

}