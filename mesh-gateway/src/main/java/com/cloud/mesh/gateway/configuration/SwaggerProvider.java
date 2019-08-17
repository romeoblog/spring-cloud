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
package com.cloud.mesh.gateway.configuration;

import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Swagger Provider
 *
 * @author Benji
 * @date 2019-04-03
 */
//@Component
//@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v2/api-docs";

    public static final String EUREKA_SUB_PREFIX = "CompositeDiscoveryClient_";

    private final DiscoveryClientRouteDefinitionLocator routeLocator;

    private final RouteLocator routeLocator1;
    private final GatewayProperties gatewayProperties;

    public SwaggerProvider(DiscoveryClientRouteDefinitionLocator routeLocator, RouteLocator routeLocator1, GatewayProperties gatewayProperties) {
        this.routeLocator = routeLocator;
        this.routeLocator1 = routeLocator1;
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        List<String> gatewayRoutes = this.gatewayProperties.getRoutes().stream().map(routeDefinition -> routeDefinition.getId().toUpperCase()).collect(Collectors.toList());

        System.out.println(gatewayRoutes.toString());
        //从DiscoveryClientRouteDefinitionLocator 中取出routes，构造成swaggerResource
        routeLocator.getRouteDefinitions().subscribe(routeDefinition -> {
            // 重写路由逻辑，只取有效节点
            gatewayRoutes.forEach(id -> {
                if (routeDefinition.getId().contains(id)) {
                    resources.add(swaggerResource(routeDefinition.getId().substring(EUREKA_SUB_PREFIX.length()),
                            routeDefinition.getPredicates().get(0).getArgs().get("pattern").replace("/**", API_URI)));
                    return;
                }
            });
        });
        return resources;
    }

    // RouteDefinition{id='CompositeDiscoveryClient_DG-MALL-ORDER-SERVICE', predicates=[PredicateDefinition{name='Path', args={pattern=/MESH-ORDER-SERVICE/**}}], filters=[FilterDefinition{name='RewritePath', args={regexp=/EXAMPLE-ORDER-SERVICE/(?<remaining>.*), replacement=/${remaining}}}], uri=lb://EXAMPLE-ORDER-SERVICE, order=0}
    // RouteDefinition{id='CompositeDiscoveryClient_DG-MALL-CLOUD-GATEWAY', predicates=[PredicateDefinition{name='Path', args={pattern=/MESH-GATEWAY/**}}], filters=[FilterDefinition{name='RewritePath', args={regexp=/EXAMPLE-CLOUD-GATEWAY/(?<remaining>.*), replacement=/${remaining}}}], uri=lb://EXAMPLE-GATEWAY, order=0}
    // RouteDefinition{id='CompositeDiscoveryClient_DG-MALL-EUREKA-EUREKA', predicates=[PredicateDefinition{name='Path', args={pattern=/MESH-EUREKA-EUREKA/**}}], filters=[FilterDefinition{name='RewritePath', args={regexp=/EXAMPLE-EUREKA-EUREKA/(?<remaining>.*), replacement=/${remaining}}}], uri=lb://EXAMPLE-EUREKA, order=0}


    /*@Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        //取出gateway的route
        routeLocator1.getRoutes().subscribe(route -> routes.add(route.getId()));
        //结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> {
                    System.out.println(routeDefinition);
                    routeDefinition.getPredicates().stream()
                            .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                            .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId().toUpperCase(), "/" + routeDefinition.getId().toUpperCase() + API_URI)));
                });
        return resources;
    }*/

    // RouteDefinition{id='mesh-order-service', predicates=[PredicateDefinition{name='Path', args={_genkey_0=/order/**}}], filters=[], uri=lb://mesh-order-service, order=0}

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("1.0.0");
        return swaggerResource;

    }

}
