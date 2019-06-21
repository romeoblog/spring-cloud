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
package com.cloud.example.core.feign;

import com.cloud.example.core.feign.logger.InfoFeignLoggerFactory;
import feign.Logger;
import feign.Request;
import org.springframework.cloud.openfeign.FeignLoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Feign Configuration
 *
 * @author Benji
 * @date 2019-06-04
 */
@Configuration
public class FeignConfig {

    /**
     * Set the requested connection and timeout
     *
     * @return the Request.Options
     */
    @Bean
    public Request.Options feignRequestOptions() {
        return new Request.Options(10000, 60000);
    }


    /**
     * The Logger.Level object that you may configure per client, tells Feign how much to log. Choices are:
     * <p>
     * 1. NONE, No logging (DEFAULT).<br>
     * 2. BASIC, Log only the request method and URL and the response status code and execution time.<br>
     * 3. HEADERS, Log the basic information along with request and response headers.<br>
     * 4. FULL, Log the headers, body, and metadata for both requests and responses.<br>
     *
     * @return
     */
    @Bean
    Logger.Level feignLevel() {
        return Logger.Level.BASIC;
    }


    /**
     * Custom implementation of the log expansion class
     *
     * @return the FeignLoggerFactory
     */
    @Bean
    FeignLoggerFactory infoFeignLoggerFactory() {
        return new InfoFeignLoggerFactory();
    }

//    /**
//     * 重试
//     *
//     * @return
//     */
//    @Bean
//    public Retryer feignRetry() {
//        return new Retryer.Default();
//    }
//
//
//    /**
//     * 使用feign默认的契约，而不是使用spring mvc的契约
//     * 注意： 此时ProductService01Feign类上的方法的写法发生了改变
//     *
//     * @return
//     */
//    @Bean
//    public Contract feignContract() {
//        return new feign.Contract.Default();
//    }
//
//    /**
//     * 增加 RequestInterceptor 可以添加额外的请求信息，比如请求头，请求参数
//     * 增加一个basic认证的请求头，当某些微服务需要basic认证时，可以使用
//     *
//     * @return
//     */
//    @Bean
//    public RequestInterceptor basicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("root", "admin", Charset.forName("utf-8"));
//    }
//
//    /**
//     * 添加一个请求拦截器：增加一个自定义的请求头token=1234567890
//     *
//     * @return
//     */
//    @Bean
//    public RequestInterceptor tokenRequestInterceptor() {
//        return template -> template.header("token", "1234567890");
//    }

}