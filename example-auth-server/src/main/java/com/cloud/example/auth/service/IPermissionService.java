package com.cloud.example.auth.service;

import com.cloud.example.auth.entity.PermissionDTO;

/**
 * 权限检查
 *
 * @author Benji
 * @date 2019-06-17
 */
public interface IPermissionService {

    Boolean checkSign();

    /**
     * 检查Token合法性
     *
     * @param token the token
     * @return b
     */
    Boolean checkToken(String token);

    /**
     * 权限检查
     *
     * @param permissionDTO
     * @return
     */
    Boolean checkPermission(PermissionDTO permissionDTO);

}
