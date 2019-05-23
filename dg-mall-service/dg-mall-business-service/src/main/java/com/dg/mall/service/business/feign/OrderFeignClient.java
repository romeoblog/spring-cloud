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
package com.dg.mall.service.business.feign;

import com.dg.mall.common.model.ResultMsg;
import com.dg.mall.model.order.OrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 订单服务 Demo
 *
 * @author Benji
 * @date 2019-04-28
 */
@FeignClient(name = "dg-mall-order-service", path = "/order")
public interface OrderFeignClient {

    /**
     * 创建订单 Demo
     *
     * @param orderVO
     * @return
     */
    @PostMapping("/create")
    ResultMsg<Boolean> create(@RequestBody OrderVO orderVO);

}
