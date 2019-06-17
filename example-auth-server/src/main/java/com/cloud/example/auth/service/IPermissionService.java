package com.cloud.example.auth.service;

/**
 * 权限检查
 *
 * @author Benji
 * @date 2019-06-17
 */
public interface IPermissionService {

    Boolean checkSign();

    Boolean checkToken();

}
