package com.cloud.example.mq.rocketmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * SpringApplication run.
 *
 * @author Benji
 * @date 2019-07-02
 */
@SpringBootApplication
@EnableBinding({Sink.class})
public class ConsumerApplication {

    private final static Logger logger = LoggerFactory.getLogger(ConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        logger.info("RocketMQProducerApplication is running!");
    }

}
