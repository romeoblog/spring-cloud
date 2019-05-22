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
package com.dg.mall.order.service;

import com.dg.mall.common.model.ResultMsg;
import com.dg.mall.order.entity.DemoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * Created with default template
 * Author: Joe Benji
 * Date: 2019-04-03
 * Time: 15:42
 * Description:
 */
@FeignClient(value = "dg-mall-user-server"/*, fallback = OrderServiceHystrix.class*/)
public interface UserService {

    // @RequestLine("GET /home/getInformation1")
    @RequestMapping(value = "/user/getInformation1", method = RequestMethod.GET)
    ResultMsg<DemoVO> getInformation1();

}
