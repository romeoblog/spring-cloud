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
package com.dg.mall.platform.common.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * url白名单处理 application.yml中配置需要放权的url白名单
 *
 * @author Joe-Benji
 * @date 2019-04-08
 * @since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "security.oauth2")
public class PermitUrlProperties {

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {
        "/**/actuator/**", "/**/actuator/**/**",  //断点监控
        "/**/v2/api-docs/**", "/**/swagger-ui.html", "/**/swagger-resources/**", "/**/webjars/**", //swagger
        "/**/turbine.stream", "/**/turbine.stream**/**", "/**/hystrix", "/**/hystrix.stream", "/**/hystrix/**", "/**/hystrix/**/**", "/**/proxy.stream/**", //熔断监控
        "/**/druid/**", "/**/favicon.ico", "/**/prometheus"
    };

    private String[] ignored;

    /**
     * 需要放开权限的url
     *
     * @return 自定义的url和监控中心需要访问的url集合
     */
    public String[] getIgnored() {
        if (ignored == null || ignored.length == 0) {
            return ENDPOINTS;
        }

        List<String> list = new ArrayList<>();
        for (String url : ENDPOINTS) {
            list.add(url);
        }
        for (String url : ignored) {
            list.add(url);
        }

        return list.toArray(new String[list.size()]);
    }

    public void setIgnored(String[] ignored) {
        this.ignored = ignored;
    }

}
