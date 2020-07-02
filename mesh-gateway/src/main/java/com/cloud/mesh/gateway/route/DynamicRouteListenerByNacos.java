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
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.cloud.mesh.common.utils.JacksonUtils;
import com.cloud.mesh.common.utils.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * The type Nacos Dynamic route listener
 *
 * @author willlu.zheng
 * @date 2020-06-16
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DynamicRouteListenerByNacos extends AbstractDynamicRouteListener {

    private String serverAddr = "localhost:8848";

    private String dataId = "mesh-gateway";

    private String groupId = "GATEWAY_ROUTER_GROUP";

    private Long timeout = 5000L;

    /**
     * Loading listener config info
     *
     * @param dynamicRouteService the dynamic Route service.
     */
    @Override
    public void handlerListener(IDynamicRouteService dynamicRouteService) {
        try {
            Properties properties = new Properties();
            properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
            log.info("Loading listener data: remoteAddress: [{}], groupId: [{}], dataId: [{}]", serverAddr, groupId, dataId);
            ConfigService configService = NacosFactory.createConfigService(properties);
            setDynamicRouteService(configService.getConfig(dataId, groupId, timeout), dynamicRouteService);

            // add nacos Listener
            configService.addListener(dataId, groupId, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    setDynamicRouteService(configInfo, dynamicRouteService);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("Nacos ConfigService Listener Exception ErrorMsg={}", e.getErrMsg(), e);
        }
    }

    /**
     * the type setting Nacos Dynamic RouteService
     *
     * @param configInfo
     * @param dynamicRouteService
     */
    private void setDynamicRouteService(String configInfo, IDynamicRouteService dynamicRouteService) {
        if (StringUtils.isBlank(configInfo)) {
            return;
        }

        List<RouteDefinition> list = JacksonUtils.toCollection(configInfo, new TypeReference<List<RouteDefinition>>() {
        });
        list.forEach(definition -> {
            log.info("Receive config info from the Nacos ConfigService Listener: {}", definition);
            dynamicRouteService.update(definition);
        });
    }

}