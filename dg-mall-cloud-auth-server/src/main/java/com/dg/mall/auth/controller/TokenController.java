/*
 *  Copyright 2015-2019 dg-mall.com Group.
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
package com.dg.mall.auth.controller;

import com.dg.mall.auth.util.Oauth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * token控制层
 *
 * @author Joe-Benji
 * @date 2019-04-08
 * @since 1.0.0
 */
@RestController
@RequestMapping("/oauth")
public class TokenController {

    /**
     * 覆盖了 spring-security-oauth2 内部的 endpoint oauth2/check_token
     * spring-security-oauth2 内部原有的该控制器 CheckTokenEndpoint，返回值，不符合自身业务要求，故覆盖之。
     */
//    @PostMapping("/check_token")
//    public ResultMsg<OAuth2AccessToken> getToken(@RequestParam(value = "token") String token) {
//        OAuth2AccessToken oAuth2AccessToken = Oauth2Utils.checkTokenInOauth2Server(token);
//        if (oAuth2AccessToken == null) {
//            return ResultMsg.error(ResultCode.NO_PREMISSION);
//        }
//        return ResultMsg.ok(oAuth2AccessToken);
//    }

    /**
     * 获取当前token对应的用户主体的凭证信息(认证对象)
     */
    @GetMapping("/getAuth")
    public OAuth2Authentication getAuth(@RequestParam(value = "token") String token) {
        OAuth2Authentication oAuth2Authentication = Oauth2Utils.getAuthenticationInOauth2Server(token);
        return oAuth2Authentication;
    }
}
