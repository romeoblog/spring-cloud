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
package com.cloud.mesh.search.async.mq.kafka;

import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.search.async.mq.IMessageSender;
import com.cloud.mesh.search.async.mq.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka Producer Implement
 *
 * @author willlu.zheng
 * @date 2019-09-24
 */
@Component("kafkaProducer")
public class KafkaProducerImpl implements IMessageSender {

    private static Logger logger = LoggerFactory.getLogger(KafkaProducerImpl.class);

    private static final String MY_TOPIC = "test";

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * Send the producer record.
     *
     * @param message the message
     */
    @Override
    public void send(Message message) {

        String content = JacksonUtils.toJson(message);

        logger.info("KafkaProducer=> Send the producer record: {}", content);

        kafkaTemplate.send(MY_TOPIC, content);

    }
}