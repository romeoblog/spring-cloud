package com.cloud.mesh.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author willlu.zheng
 * @date 2019-09-06
 */
@Slf4j
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
public class MongodbApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MongodbApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        log.info("MongodbApplication is running!");
    }

}
