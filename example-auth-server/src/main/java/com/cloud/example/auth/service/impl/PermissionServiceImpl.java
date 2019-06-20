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
package com.cloud.example.auth.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cloud.example.auth.entity.PermissionDTO;
import com.cloud.example.auth.service.IPermissionService;
import com.cloud.example.common.constant.Constants;
import com.cloud.example.platform.exception.DuplicateMachineException;
import com.cloud.example.platform.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 权限检查 实现类
 *
 * @author Benji
 * @date 2019-06-17
 */
@Service
@Slf4j
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Boolean checkSign() {
        return null;
    }

    @Override
    public Boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) {
            new PermissionException("当前Token值：Token[" + token + "]");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            new PermissionException("认证信息不存在：Token[" + token + "]");
        }

        String name = authentication.getName();
        String cacheJwtId = redisTemplate.opsForValue().get(Constants.JWT_ID_USERNAME + name);

        log.info("UserName=[{}], JWT Id=[{}],", name, cacheJwtId);

        DecodedJWT jwt = JWT.decode(token);

        if (StringUtils.isEmpty(cacheJwtId) || jwt == null) {
            new DuplicateMachineException("Token过期或在其他设备上登陆：UserName[" + name + "], Token[" + token + "], CacheJwtId[" + cacheJwtId + "]");
        }

        String jwtId = jwt.getId();
        if (!Objects.equals(jwtId, cacheJwtId)) {
            new DuplicateMachineException("Token在其他设备上登陆：UserName[" + name + "], Token[" + token + "], CacheJwtId[" + cacheJwtId + "], JwtId[" + jwtId + "]");
        }

        return true;
    }

    @Override
    public Boolean checkPermission(PermissionDTO permissionDTO) {
        return null;
    }


}
