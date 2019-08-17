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
package com.cloud.mesh.mq.rocketmq.producer.service;

import com.cloud.mesh.core.common.dto.MqMessageDTO;
import lombok.NonNull;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 消息生产者服务
 *
 * @author Benji
 * @date 2019-06-30
 */
@Service
public class ProviderService {

    private Logger log = LoggerFactory.getLogger(ProviderService.class);

    @Autowired
    private MessageChannel output;

    public void send(String message) {
        output.send(MessageBuilder.withPayload(message).build());
    }

    public Boolean send(MqMessageDTO messageDTO) {

        log.info("RECEIVE_MQ_MSG, msgDto={}", messageDTO);

        MessageBuilder<@NonNull String> messageBuilder = MessageBuilder.withPayload(messageDTO.getBody());
        messageBuilder.setHeader(RocketMQHeaders.TAGS, messageDTO.getTag());
        messageBuilder.setHeader(RocketMQHeaders.KEYS, messageDTO.getKey());
        if (messageDTO.getDelayTimeLevel() != null && messageDTO.getDelayTimeLevel() > 0) {
            messageBuilder.setHeader("DELAY", messageDTO.getDelayTimeLevel().toString());
        }

        Message message = messageBuilder.build();

        output.send(message);

        log.info("SEND_MQ_MSG_SUCCESS, messageDTO={}", messageDTO);

        return true;
    }

    public <T> void sendWithTags(T msg, String tag) {
        Message message = MessageBuilder.createMessage(msg,
                new MessageHeaders(Stream.of(tag).collect(Collectors
                        .toMap(str -> MessageConst.PROPERTY_TAGS, String::toString))));
        output.send(message);
    }

    public <T> void sendObject(T msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_TAGS, tag)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        output.send(message);
    }

}