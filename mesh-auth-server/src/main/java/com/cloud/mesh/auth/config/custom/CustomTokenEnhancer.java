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
package com.cloud.mesh.auth.config.custom;

import com.cloud.mesh.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义生成token携带的信息，将额外的信息添加到token中
 *
 * @author Benji
 * @date 2019-06-08
 */
@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        final Map<String, Object> additionalInfo = new HashMap<>(1);
        //获取登录信息
        UserDetails user = (UserDetails) oAuth2Authentication.getUserAuthentication().getPrincipal();
        additionalInfo.put("userName", user.getUsername());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);

        String jwtId = oAuth2AccessToken.getValue();

        log.info("UserName=[{}], JWT Id=[{}]", user.getUsername(), jwtId);
        redisTemplate.opsForValue().set(Constants.JWT_ID_USERNAME + user.getUsername(), jwtId);

        return oAuth2AccessToken;
    }

}