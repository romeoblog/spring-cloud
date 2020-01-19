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
package com.cloud.mesh.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 权限实体DTO
 *
 * @author willlu.zheng
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
