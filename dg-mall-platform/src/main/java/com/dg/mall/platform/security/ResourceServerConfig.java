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
//package com.dg.mall.platform.common.security;
//
//import com.dg.mall.platform.common.props.PermitUrlProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 〈OAuth资源服务配置〉
// *
// * @author Curise
// * @create 2018/12/14
// * @since 1.0.0
// */
////@Configuration
////@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableConfigurationProperties(PermitUrlProperties.class)
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Autowired(required = false)
//    private PermitUrlProperties permitUrlProperties;
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // 关闭跨站请求防护
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                .and()
//                .requestMatchers().antMatchers("/business1/**")
//                .and()
//                .authorizeRequests()
//                .antMatchers(permitUrlProperties.getIgnored()).permitAll()
//                .antMatchers("/business1/**").authenticated()
//                .and()
//                .httpBasic();
//        // filter
//        // http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//    }
//
////    @Bean
////    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
////        return new AuthenticationTokenFilter();
////    }
//
//}
