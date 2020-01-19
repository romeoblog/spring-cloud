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

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayParamFlowItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.cloud.mesh.common.utils.JsonUtils;
import com.cloud.mesh.gateway.utils.NacosConfigUtil;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * The type gateway configuration
 *
 * @author willlu.zheng
 * @date 2019-05-25
 */
@Configuration
@Slf4j
public class GatewayConfiguration {

    @Value("${spring.application.name}")
    private String appName;

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // Register the block exception handler for Spring Cloud Gateway.
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(-1)
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void doInit() {
//        initCustomizedApis();
//        initGatewayRules();
//        initDegradeRule();

        registerSentinelProperty();
        registerNacosDataSource();
    }

    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();
        ApiDefinition api1 = new ApiDefinition("some_customized_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/ahas"));
                    add(new ApiPathPredicateItem().setPattern("/product/**")
                            .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_PREFIX));
                }});
        ApiDefinition api2 = new ApiDefinition("another_customized_api")
                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
                    add(new ApiPathPredicateItem().setPattern("/**")
                            .setMatchStrategy(SentinelGatewayConstants.PARAM_MATCH_STRATEGY_PREFIX));
                }});
        definitions.add(api1);
        definitions.add(api2);
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    private void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();

        rules.add(new GatewayFlowRule("business_service_route")
                        .setCount(10)
                        .setIntervalSec(1)
                //.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)
                //.setMaxQueueingTimeoutMs(3000)
        );

        rules.add(new GatewayFlowRule("baidu_route")
                .setCount(10)
                .setIntervalSec(1)
        );
        rules.add(new GatewayFlowRule("baidu_route")
                .setCount(2)
                .setIntervalSec(2)
                .setBurst(2)
                .setParamItem(new GatewayParamFlowItem()
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_CLIENT_IP)
                )
        );
        rules.add(new GatewayFlowRule("qq_route")
                .setCount(10)
                .setIntervalSec(1)
                .setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER)
                .setMaxQueueingTimeoutMs(600)
                .setParamItem(new GatewayParamFlowItem()
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_HEADER)
                        .setFieldName("X-Sentinel-Flag")
                )
        );
        rules.add(new GatewayFlowRule("qq_route")
                .setCount(1)
                .setIntervalSec(1)
                .setParamItem(new GatewayParamFlowItem()
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
                        .setFieldName("pa")
                )
        );

        rules.add(new GatewayFlowRule("some_customized_api")
                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_CUSTOM_API_NAME)
                .setCount(5)
                .setIntervalSec(1)
                .setParamItem(new GatewayParamFlowItem()
                        .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM)
                        .setFieldName("pn")
                )
        );
        GatewayRuleManager.loadRules(rules);
    }

    private void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();

        // 平均响应时间 count: ms/number TimeWindow: s
        rules.add(new DegradeRule("business_service_route")
                .setCount(10).setGrade(RuleConstant.DEGRADE_GRADE_RT)
                .setTimeWindow(3));
        // 异常比例 count异常比率的阈值范围是 [0.0, 1.0]，代表 0% - 100%。 TimeWindow: s
        rules.add(new DegradeRule("business_service_route")
                .setCount(0.3).setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO)
                .setTimeWindow(10));

        // 异常数 count: number TimeWindow: min
        rules.add(new DegradeRule("business_service_route")
                .setCount(1).setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT)
                .setTimeWindow(1));

        DegradeRuleManager.loadRules(rules);
    }

    private void registerSentinelProperty() {
        log.info("Loading flow data source rules: remoteAddress: [{}], groupId: [{}], dataId: [{}]",
                NacosConfigUtil.REMOTE_ADDRESS,
                NacosConfigUtil.GROUP_ID,
                appName + NacosConfigUtil.FLOW_DATA_ID_POSTFIX);
        ReadableDataSource<String, Set<GatewayFlowRule>> flowRuleDataSource = new NacosDataSource<>(
                NacosConfigUtil.REMOTE_ADDRESS,
                NacosConfigUtil.GROUP_ID,
                appName + NacosConfigUtil.FLOW_DATA_ID_POSTFIX,
                source -> JsonUtils.fromJson(source, new TypeToken<Set<GatewayFlowRule>>() {
                }));
        GatewayRuleManager.register2Property(flowRuleDataSource.getProperty());

        log.info("Loading degrade data source rules: remoteAddress: [{}], groupId: [{}], dataId: [{}]",
                NacosConfigUtil.REMOTE_ADDRESS,
                NacosConfigUtil.GROUP_ID,
                appName + NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX);
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(
                NacosConfigUtil.REMOTE_ADDRESS,
                NacosConfigUtil.GROUP_ID,
                appName + NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX,
                source -> JsonUtils.fromJson(source, new TypeToken<List<DegradeRule>>() {
                }));
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());

        log.info("Loading system data source rules: remoteAddress: [{}], groupId: [{}], dataId: [{}]",
                NacosConfigUtil.REMOTE_ADDRESS,
                NacosConfigUtil.GROUP_ID,
                appName + NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX);
        ReadableDataSource<String, List<SystemRule>> systemRuleDataSource = new NacosDataSource<>(
                NacosConfigUtil.REMOTE_ADDRESS,
                NacosConfigUtil.GROUP_ID,
                appName + NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                source -> JsonUtils.fromJson(source, new TypeToken<List<SystemRule>>() {
                }));
        SystemRuleManager.register2Property(systemRuleDataSource.getProperty());
    }

    private void registerNacosDataSource() {
        WritableDataSource<List<FlowRule>> flowWritableDataSource = new NacosWritableDataSource<>(
                NacosConfigUtil.REMOTE_ADDRESS,
                NacosConfigUtil.GROUP_ID,
                appName + NacosConfigUtil.FLOW_DATA_ID_POSTFIX,
                source -> JsonUtils.toJson(source));
        WritableDataSourceRegistry.registerFlowDataSource(flowWritableDataSource);

        WritableDataSource<List<DegradeRule>> degradeWritableDataSource = new NacosWritableDataSource<>(
                NacosConfigUtil.REMOTE_ADDRESS,
                NacosConfigUtil.GROUP_ID,
                appName + NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX,
                source -> JsonUtils.toJson(source));
        WritableDataSourceRegistry.registerDegradeDataSource(degradeWritableDataSource);

        WritableDataSource<List<SystemRule>> systemWritableDataSource = new NacosWritableDataSource<>(
                NacosConfigUtil.REMOTE_ADDRESS,
                NacosConfigUtil.GROUP_ID,
                appName + NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX,
                source -> JsonUtils.toJson(source));
        WritableDataSourceRegistry.registerSystemDataSource(systemWritableDataSource);

    }


}