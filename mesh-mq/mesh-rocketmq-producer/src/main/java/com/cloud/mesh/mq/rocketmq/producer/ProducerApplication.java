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
package com.cloud.mesh.mq.rocketmq.producer;

import com.cloud.mesh.mq.rocketmq.producer.service.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

/**
 * SpringApplication run.
 *
 * @author willlu.zheng
 * @date 2019-06-30
 */
@SpringBootApplication
@EnableBinding({Source.class})
@EnableDiscoveryClient
public class ProducerApplication implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(ProducerApplication.class);

    @Autowired
    private ProviderService providerService;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
        logger.info("RocketMQProducerApplication is running!");
    }


    /**
     * 实现了 CommandLineRunner 接口，仅仅为了测试
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        providerService.send("WelCome to RocketMQ ... ");
        System.out.println("send success.");
    }
}
