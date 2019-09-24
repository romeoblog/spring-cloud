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
package com.cloud.mesh.search.async.mq.kafka.consume;

import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.search.async.canal.CanalMsgContent;
import com.cloud.mesh.search.async.mq.AbstractMessageReceiver;
import com.cloud.mesh.search.async.mq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka message listener
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Component
public class CanalKafkaReceiver extends AbstractMessageReceiver {

    private static Logger logger = LoggerFactory.getLogger(CanalKafkaReceiver.class);

    @Override
    @KafkaListener(topics = "test")
    protected void onMessage(String message) {
        logger.info("CanalKafkaReceiver=> Receive message body={}", message);
        Message msg = JacksonUtils.fromJson(message, Message.class);

        CanalMsgContent entry = JacksonUtils.fromJson(msg.getMsg(), CanalMsgContent.class);

        super.publishCanalEvent(entry);
    }

}
