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
package com.cloud.mesh.api;

import com.cloud.mesh.common.model.ResultMsg;
import com.cloud.mesh.model.auth.ResultMessageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 认证服务相关api
 *
 * @author Benji
 * @date 2019-06-24
 */
@FeignClient(name = "mesh-auth-service")
public interface AuthFeignClient {

    /**
     * 检查头肯是否有效请求
     *
     * @param token
     * @return
     */
    @GetMapping("/oauth/check_token")
    ResultMessageVO checkToken(@RequestParam("token") String token);

    /**
     * 自定义token校验
     *
     * @param token
     * @return
     */
    @GetMapping("/permission/checkToken")
    ResultMsg<ResultMessageVO> checkToken2(@RequestParam("token") String token);

}
