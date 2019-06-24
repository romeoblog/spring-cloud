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
import com.cloud.example.api.AuthFeignClient;
import com.cloud.example.auth.entity.PermissionDTO;
import com.cloud.example.auth.service.IPermissionService;
import com.cloud.example.common.constant.Constants;
import com.cloud.example.common.utils.JacksonUtils;
import com.cloud.example.core.exception.DuplicateMachineException;
import com.cloud.example.core.exception.PermissionException;
import com.cloud.example.model.auth.ResultMessageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private AuthFeignClient authFeignClient;

    @Override
    public Boolean checkSign() {
        return null;
    }

    @Override
    public ResultMessageVO checkToken(String token) {

        if (StringUtils.isEmpty(token)) {
            throw new PermissionException("当前Token值：Token[" + token + "]");
        }
        ResultMessageVO authentication;
        try {
            authentication = authFeignClient.checkToken(token);
        } catch (Exception ex) {
            authentication = new ResultMessageVO();
            authentication.setError("invalid_token");
            authentication.setErrorDescription(ex.getMessage());
        }

        log.info("调用check_token接口返回结果信息：{}", JacksonUtils.toJson(authentication));

        if (StringUtils.isNotEmpty(authentication.getError())) {
            throw new PermissionException(authentication.getError() + "：Token[" + token + "]");
        }

        String name = authentication.getUserName();
        String cacheJwtId = redisTemplate.opsForValue().get(Constants.JWT_ID_USERNAME + name);

        log.info("UserName=[{}], JWT Id=[{}],", name, cacheJwtId);

        DecodedJWT jwt = JWT.decode(token);

        if (jwt == null) {
            throw new DuplicateMachineException("Decoded JWT is Empty：UserName[" + name + "], Token[" + token + "], CacheJwtId[" + cacheJwtId + "]");
        }

        if (StringUtils.isEmpty(cacheJwtId)) {
            throw new DuplicateMachineException("Token过期或在其他设备上登陆：UserName[" + name + "], Token[" + token + "], CacheJwtId[" + cacheJwtId + "]");
        }

        String jwtId = jwt.getId();
        if (!Objects.equals(jwtId, cacheJwtId)) {
            throw new DuplicateMachineException("Token在其他设备上登陆：UserName[" + name + "], Token[" + token + "], CacheJwtId[" + cacheJwtId + "], JwtId[" + jwtId + "]");
        }

        return authentication;
    }

    @Override
    public Boolean checkPermission(PermissionDTO permissionDTO) {
        return null;
    }


}
