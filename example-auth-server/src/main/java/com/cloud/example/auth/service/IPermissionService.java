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
