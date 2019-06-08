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
package com.cloud.example.gateway.filter.global;

import com.cloud.example.common.enums.ResultCode;
import com.cloud.example.common.model.ResultMsg;
import com.cloud.example.common.utils.EncryptUtils;
import com.cloud.example.common.constant.HeadConstant;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Authentication Check Filter
 *
 * @author Benji
 * @date 2019-04-02
 */
@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter {

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Value("${checkSign}")
    private Boolean checkSign;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (!checkSign) {
            if (log.isDebugEnabled()) {
                log.debug("Current application check sign is Disable.");
            }
            return chain.filter(exchange);
        }

        log.info("=====开启权限认证=====");

        ServerHttpRequest request = exchange.getRequest();
        String method = request.getMethodValue();
        URI requestUri = request.getURI();
        String url = requestUri.getPath();
        MultiValueMap<String, String> queryMap = request.getQueryParams();
        // ObjectMapper mapper = new ObjectMapper();
        String queryString = getQueryString(queryMap);
        String filterPath = "/v2/api-docs";

        ServerHttpRequest newRequest = null;

        if (pathMatcher.match(filterPath, url)) {
            return chain.filter(exchange);
        }

        try {
            // log.error("=={}===", queryMap.toString());
            // queryString = mapper.writeValueAsString(queryMap);
        } catch (Exception e) {
        }
        log.info("==== 请求开始, 各个参数, url: {}, method: {}, params: {} ====", url, method, queryString);

        StringBuffer strParam = new StringBuffer();
        String sign = request.getHeaders().getFirst(HeadConstant.SIGN_HEAD);
        String token = getTokenValue(request);
        String preSign = null;

        boolean isBody;

        if (queryMap.size() > 0 || "GET".equals(method)) {
            preSign = queryString;
            isBody = false;
        } else {
            String body = getBodyString(request);
            if (body != null) {
                preSign = body;
            }
            isBody = true;

            // 解决request 读取body只能读取一次的问题
            URI uri = request.getURI();
            newRequest = request.mutate().uri(uri).build();
            DataBuffer bodyDataBuffer = dataBuffer(body);
            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

            newRequest = new ServerHttpRequestDecorator(newRequest) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return bodyFlux;
                }
            };
        }

        log.info("=====>token:{},sign:{}", token, sign);

        boolean verifySign = verifySign(preSign, sign, token);

        log.info("=====>鉴权结果:[{}]<=======", verifySign);

        if (!verifySign) {
            ServerHttpResponse response = exchange.getResponse();
            ResultMsg resultMsg = ResultMsg.create().status(ResultCode.ERROR_SIGN);

            byte[] data = resultMsg.toString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(data);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }

        if (isBody) {
            //封装request，传给下一级
            return chain.filter(exchange.mutate().request(newRequest).build());
        } else {
            return chain.filter(exchange);
        }

    }

    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     *
     * @return 请求体
     */
    private String getBodyString(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        //获取request body
        return bodyRef.get();
    }

    private boolean verifySign(String queryString, String originSign, String token) {
        String generateSign = StringUtils.isNotBlank(queryString) ?
                EncryptUtils.encodeMD5String(queryString + token) : EncryptUtils.encodeMD5String(token);
        boolean result = false;
        if (StringUtils.isNotBlank(originSign) && StringUtils.equalsIgnoreCase(generateSign, originSign)) {
            result = true;
        }
        log.info("签名校验: Result={}, queryString={}, token={}, originSign={}, generateSign={}",
                result, queryString, token, originSign, generateSign);
        return result;
    }

    private DataBuffer dataBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    protected String getTokenValue(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst(HeadConstant.AUTHORIZATION_HEAD);
        String authToken = "";
        if (authHeader != null) {
            authToken = authHeader.substring(HeadConstant.BEARER_HEAD.length()).trim();
        }
        return authToken;
    }

    public static String getQueryString(MultiValueMap<String, String> map) {
        StringBuffer buffer = new StringBuffer();
        if (map != null && !map.isEmpty()) {
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                String value = map.get(key).get(0);
                if (StringUtils.isEmpty(buffer.toString())) {
                    buffer.append(key).append("=").append(value);
                } else {
                    buffer.append("&").append(key).append("=").append(value);
                }
            }
        }
        return buffer.toString();
    }

}