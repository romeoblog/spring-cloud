package com.cloud.example.api;

import com.cloud.example.common.model.ResultMsg;
import com.cloud.example.model.auth.ResultMessageVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 认证服务相关api
 *
 * @author Benji
 * @date 2019-06-24
 */
@FeignClient(name = "example-auth-service")
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
