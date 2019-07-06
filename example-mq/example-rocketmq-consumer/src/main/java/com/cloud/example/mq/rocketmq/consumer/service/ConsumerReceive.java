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