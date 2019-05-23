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
package com.dg.mall.user.service;

import com.dg.mall.platform.common.model.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with default template
 * Author: Joe Benji
 * Date: 2019-04-03
 * Time: 15:42
 * Description:
 */
@FeignClient(value = "dg-mall-inventory-server")
public interface InventoryService {

    @RequestMapping(value = "/inventory/countInventory/{inventoryId}", method = RequestMethod.GET)
    ResultMsg<Integer> countInventory(@PathVariable(value = "inventoryId") Integer inventoryId);

}
