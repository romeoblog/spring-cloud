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
package com.cloud.example.common.constant;

/**
 * Head Constant Flag
 *
 * @author Benji
 * @date 2019-04-30
 */
public class HeadConstant {

    /**
     * 认证标识
     */
    public static final String AUTHORIZATION_HEAD = "Authorization";

    /**
     * 认证带的token前缀：Authorization:Bearer token
     */
    public static final String BEARER_HEAD = "Bearer ";

    /**
     * 签名标识
     */
    public static final String SIGN_HEAD = "sign";


}
