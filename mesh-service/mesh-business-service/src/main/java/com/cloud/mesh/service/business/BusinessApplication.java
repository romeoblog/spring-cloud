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
package com.cloud.mesh.service.business;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * BusinessApplication
 *
 * @author willlu.zheng
 * @date 2019-04-28
 */
@Slf4j
@RefreshScope
@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {"com.cloud.mesh"})
@MapperScan(basePackages = {"com.cloud.mesh.service.business.mapper", "com.cloud.mesh.mapper"})
@EnableFeignClients(basePackages = {"com.cloud.mesh.api"})
@EnableDiscoveryClient
public class BusinessApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BusinessApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        log.info("BusinessApplication is running!");
    }

}
