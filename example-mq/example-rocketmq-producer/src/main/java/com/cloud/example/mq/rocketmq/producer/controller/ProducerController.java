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
package com.cloud.example.mq.rocketmq.producer.controller;

import com.cloud.example.common.model.ResultMsg;
import com.cloud.example.core.common.dto.MqMessageDTO;
import com.cloud.example.mq.rocketmq.producer.service.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息生产者服务
 *
 * @author Benji
 * @date 2019-07-05
 */
@RestController
public class ProducerController {

    private Logger log = LoggerFactory.getLogger(ProducerController.class);

    private final static int DEFAULT_SEND_ERROR_CODE = -1;

    @Autowired
    private ProviderService providerService;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST, consumes = "application/json")
    public ResultMsg sendMessage(@RequestBody MqMessageDTO messageDTO) {
        try {
            Boolean result = providerService.send(messageDTO);
            return ResultMsg.ok(result);
        } catch (Exception ex) {
            log.error("SEND_MQ_MESSAGE_ERROR, messageDTO={}, ErrorMessage={}", messageDTO, ex.getMessage(), ex);

            return ResultMsg.error(DEFAULT_SEND_ERROR_CODE, ex.getMessage());
        }
    }

}
