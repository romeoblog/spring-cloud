/*
 *  Copyright 2015-2019 dg-mall.com Group.
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
package com.dg.mall.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {"com.dg.mall"})
@MapperScan(basePackages = {"com.dg.mall.inventory.mapper", "com.dg.mall.mapper"})
@EnableEurekaClient
@EnableFeignClients
public class InventoryServiceMain {

    private final static Logger logger = LoggerFactory.getLogger(InventoryServiceMain.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(InventoryServiceMain.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        logger.info("InventoryServiceMain is running!");
    }
}

