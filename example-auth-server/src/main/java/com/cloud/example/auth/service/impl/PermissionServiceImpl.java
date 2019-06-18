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
