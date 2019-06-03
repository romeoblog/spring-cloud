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
package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.feign.UserFeignClient;
import com.example.demo.repository.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2019-04-04
 */
@Service
public class OrderService {

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private OrderDAO orderDAO;

    @Transactional
    public void create(String userId,String commodityCode, Integer count){

        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));

        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(count);
        order.setMoney(orderMoney);

        orderDAO.save(order);

        userFeignClient.debit(userId, orderMoney);
    }

}
