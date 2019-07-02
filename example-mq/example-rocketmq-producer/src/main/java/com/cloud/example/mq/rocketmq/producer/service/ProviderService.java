package com.cloud.example.mq.rocketmq.producer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * 消息生产者服务
 *
 * @author Benji
 * @date 2019-06-30
 */
@Service
public class ProviderService {

    @Autowired
    private MessageChannel output;

    public void send(String message) {
        output.send(MessageBuilder.withPayload(message).build());
    }

}