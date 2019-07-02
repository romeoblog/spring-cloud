package com.cloud.example.mq.rocketmq.producer;

import com.cloud.example.mq.rocketmq.producer.service.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

/**
 * SpringApplication run.
 *
 * @author Benji
 * @date 2019-06-30
 */
@SpringBootApplication
@EnableBinding({Source.class})
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
