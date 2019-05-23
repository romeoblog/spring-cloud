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
package com.dg.mall.platform.security;

import com.dg.mall.common.constant.HeadConstant;
import com.dg.mall.common.enums.ResultCode;
import com.dg.mall.common.model.ResultMsg;
import com.dg.mall.common.utils.JacksonUtils;
import com.dg.mall.platform.common.props.PermitUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


/**
 * Token interceptor filter
 *
 * @author Benji
 * @date 2019-04-15
 */
@Component
@Slf4j
@EnableConfigurationProperties(PermitUrlProperties.class)
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired(required = false)
    private PermitUrlProperties permitUrlProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Value("${checkToken:true}")
    private Boolean checkToken;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        if (!checkToken) {
            if (log.isDebugEnabled()) {
                log.debug("Current application Check Token is Disable.");
            }
            chain.doFilter(request, response);
            return;
        }

        if (!handlerNextChain(request.getRequestURI())) {
            String authToken = extractToken(request);

            log.info("Get Token-->[{}]", authToken);

            if (StringUtils.isNotEmpty(authToken)) {
                Boolean hasKeyToken = redisTemplate.hasKey("auth:" + authToken);
                log.info("====Check Token return Result[{}]====", hasKeyToken);

                if (!hasKeyToken) {
                    ResultMsg result = ResultMsg.create().status(ResultCode.ERROR_TOKEN);
                    try {
                        log.error("Check Token is failed，Token is Invalid -> {}", JacksonUtils.toJson(result));
                        handlerResponse(response, result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                ResultMsg result = ResultMsg.create().status(ResultCode.NO_PREMISSION);
                try {
                    log.error("Check Token is failed，Token is Not Exist -> {}", JacksonUtils.toJson(result));
                    handlerResponse(response, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    /**
     * Get Token information by header
     *
     * @param request
     * @return
     */
    protected String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HeadConstant.AUTHORIZATION_HEAD);
        String authToken = null;
        if (authHeader != null) {
            authToken = authHeader.substring(HeadConstant.BEARER_HEAD.length()).trim();
        }
        return authToken;
    }

    /**
     * Handler Next chain
     *
     * @param requestURI
     * @return
     */
    private Boolean handlerNextChain(String requestURI) {
        boolean nextChain = false;
        List<String> ignoreUrls = Arrays.asList(permitUrlProperties.getIgnored());

        for (String path : ignoreUrls) {
            if (pathMatcher.match(path, requestURI)) {
                nextChain = true;
                break;
            }
        }
        log.info("Ignore resource->[{}]，Request path->[{}]，Request path is ignore->[{}]", ignoreUrls.toString(), requestURI, nextChain);

        return nextChain;
    }

    /**
     * Handler Request return Response with json
     *
     * @param response
     * @param responseObject
     */
    protected void handlerResponse(HttpServletResponse response, Object responseObject) {
        String json = JacksonUtils.toJson(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(json);
        } catch (IOException ex) {
            log.error("Handler response error, ErrorMsg={}", ex.getMessage(), ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}