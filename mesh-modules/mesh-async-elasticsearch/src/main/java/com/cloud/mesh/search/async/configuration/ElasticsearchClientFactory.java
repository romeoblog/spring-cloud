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
package com.cloud.mesh.search.async.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The type elasticsearch client factory for Spring
 *
 * @author willlu.zheng
 * @date 2019-08-13
 */
public class ElasticsearchClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchClientFactory.class);

    /**
     * Assigns connection timeout
     */
    public static int CONNECT_TIMEOUT_MILLIS = 1000;
    /**
     * Assigns socket timeout
     */
    public static int SOCKET_TIMEOUT_MILLIS = 30000;
    /**
     * Assigns connection request timeout
     */
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;
    /**
     * Assigns maximum connection per route value.
     */
    public static int MAX_CONN_PER_ROUTE = 10;
    /**
     * Assigns maximum total connection value.
     */
    public static int MAX_CONN_TOTAL = 30;

    private static HttpHost[] HTTP_HOSTS;

    private RestClientBuilder builder;
    private RestClient restClient;
    private RestHighLevelClient restHighLevelClient;

    private static ElasticsearchClientFactory elasticsearchClientFactory = new ElasticsearchClientFactory();

    private ElasticsearchClientFactory() {
    }

    public static ElasticsearchClientFactory build(HttpHost[] httpHosts,
                                                   Integer maxConnectNum, Integer maxConnectPerRoute) {
        HTTP_HOSTS = httpHosts;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        return elasticsearchClientFactory;
    }

    public static ElasticsearchClientFactory build(HttpHost[] httpHosts, Integer connectTimeOut, Integer socketTimeOut,
                                                   Integer connectionRequestTime, Integer maxConnectNum, Integer maxConnectPerRoute) {
        HTTP_HOSTS = httpHosts;
        CONNECT_TIMEOUT_MILLIS = connectTimeOut;
        SOCKET_TIMEOUT_MILLIS = socketTimeOut;
        CONNECTION_REQUEST_TIMEOUT_MILLIS = connectionRequestTime;
        MAX_CONN_TOTAL = maxConnectNum;
        MAX_CONN_PER_ROUTE = maxConnectPerRoute;
        return elasticsearchClientFactory;
    }


    public void init() {
        builder = RestClient.builder(HTTP_HOSTS);
        setConnectTimeOutConfig();
        setMultiConnectConfig();
        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(builder);

        LOGGER.info("Init elasticsearch factory successfully.");
    }

    /**
     * Set connect timeOut config
     */
    public void setConnectTimeOutConfig() {
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestConfigBuilder;
        });
    }

    /**
     * Set Multi connect config
     */
    public void setMultiConnectConfig() {
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            return httpClientBuilder;
        });
    }

    public RestClient getClient() {
        return restClient;
    }

    public RestHighLevelClient getRhlClient() {
        return restHighLevelClient;
    }

    public void close() {
        if (restClient != null) {
            try {
                restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Close elasticsearch client successfully.");
    }
}