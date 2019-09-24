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
package com.cloud.mesh.search.async.mq.rabbitmq.consume;

import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.search.async.canal.CanalMsgContent;
import com.cloud.mesh.search.async.mq.AbstractMessageReceiver;
import com.cloud.mesh.search.async.mq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ message listener
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Component
@RabbitListener(queues = "q-canal")
public class CanalRabbitReceiver extends AbstractMessageReceiver {

    private static Logger logger = LoggerFactory.getLogger(CanalRabbitReceiver.class);

    @Override
    @RabbitHandler
    protected void onMessage(String message) {
        logger.info("CanalRabbitReceiver=> Receive message body={}", message);
        Message msg = JacksonUtils.fromJson(message, Message.class);

        CanalMsgContent entry = JacksonUtils.fromJson(msg.getMsg(), CanalMsgContent.class);

        super.publishCanalEvent(entry);

    }
}
