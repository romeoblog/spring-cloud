package com.cloud.example.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限实体DTO
 *
 * @author Benji
 * @date 2019-06-18
 */
@Data
public class PermissionDTO implements Serializable {

    /**
     * 签名
     */
    private String sign;

    /**
     * token
     */
    private String token;

    /**
     * 参数或body值
     */
    private String queryString;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 请求uri
     */
    private String uri;

}
