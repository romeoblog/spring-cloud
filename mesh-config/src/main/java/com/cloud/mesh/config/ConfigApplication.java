package com.cloud.mesh.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * spring cloud config
 *
 * @author willlu.zheng
 * @date 2020-06-03
 */
@SpringBootApplication
@EnableConfigServer
@RefreshScope
@Slf4j
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConfigApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        log.info("BusinessApplication is running!");
    }
}