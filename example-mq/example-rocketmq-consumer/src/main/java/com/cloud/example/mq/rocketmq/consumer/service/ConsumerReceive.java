package com.cloud.example.mq.rocketmq.consumer.service;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

/**
 * 消息消费者服务，来订阅名为 input 的 Binding 中接收的消息
 *
 * @author Benji
 * @date 2019-07-02
 */
@Service
public class ConsumerReceive {

    @StreamListener("input")
    public void receiveInput(String message) {
        System.out.println("Receive input: " + message);
    }

}