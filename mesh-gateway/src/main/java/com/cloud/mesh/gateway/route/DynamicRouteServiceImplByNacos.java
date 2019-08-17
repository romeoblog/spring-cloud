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
package com.cloud.mesh.gateway.route;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.cloud.mesh.common.utils.JacksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Nacos动态路由配置
 *
 * @author Benji
 * @date 2019-06-05
 */
@Component
public class DynamicRouteServiceImplByNacos {

    private String serverAddr = "localhost:8848";

    private String dataId = "mesh-gateway";

    private String groupId = "GATEWAY_ROUTER_GROUP";

    private Long timeout = 5000L;

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    public DynamicRouteServiceImplByNacos() {
        dynamicRouteByNacosListener(dataId, groupId);
    }

    /**
     * 监听Nacos Server下发的动态路由配置
     *
     * @param dataId  the dataId
     * @param groupId the groupId
     */
    public void dynamicRouteByNacosListener(String dataId, String groupId) {
        try {
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            String content = configService.getConfig(dataId, groupId, timeout);
            System.out.println(content);
            configService.addListener(dataId, groupId, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    List<RouteDefinition> list = JacksonUtils.toCollection(configInfo, new TypeReference<List<RouteDefinition>>() {
                    });
                    list.forEach(definition -> {
                        dynamicRouteService.update(definition);
                    });
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            //todo 提醒:异常自行处理此处省略
        }
    }

}