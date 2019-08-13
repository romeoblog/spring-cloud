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
package com.cloud.example.search.configuration;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * The type elasticsearch configuration
 *
 * @author Benji
 * @date 2019-08-13
 */
@Configuration
@ComponentScan(basePackageClasses = ElasticsearchClientFactory.class)
public class ElasticsearchConfig {

    /**
     * Elasticsearch cluster ip values, with split ','
     */
    @Value("${elasticsearch.ip}")
    private String ip;

    /**
     * Elasticsearch port
     */
    @Value("${elasticsearch.port}")
    private int port;

    /**
     * Assigns maximum total connection value.
     */
    @Value("${elasticsearch.maxConnTotal}")
    private Integer maxConnTotal;

    /**
     * Assigns maximum total connection value.
     */
    @Value("${elasticsearch.maxConnPerRoute}")
    private Integer maxConnPerRoute;

    @Bean
    public HttpHost httpHost() {
        return new HttpHost(ip, port, "http");
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public ElasticsearchClientFactory getFactory() {
        return ElasticsearchClientFactory.build(httpHost(), maxConnTotal, maxConnPerRoute);
    }

    @Bean
    @Scope("singleton")
    public RestClient getRestClient() {
        return getFactory().getClient();
    }

    @Bean
    @Scope("singleton")
    public RestHighLevelClient getRHLClient() {
        return getFactory().getRhlClient();
    }

}
