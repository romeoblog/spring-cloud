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
package com.cloud.example.service.order.service.impl;

import com.cloud.example.api.UserFeignClient;
import com.cloud.example.common.enums.ResultCode;
import com.cloud.example.common.model.ResultMsg;
import com.cloud.example.entity.OrderTbl;
import com.cloud.example.model.order.OrderVO;
import com.cloud.example.model.user.AccountVO;
import com.cloud.example.platform.exception.InternalApiException;
import com.cloud.example.service.OrderTblService;
import com.cloud.example.service.order.mapper.OrderMapperExt;
import com.cloud.example.service.order.service.IOrderService;
import com.google.common.collect.Lists;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单实现类 Demo
 *
 * @author Benji
 * @date 2019-04-28
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderTblService orderTblService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private OrderMapperExt orderMapperExt;


    /**
     * 创建订单 demo
     *
     * @param orderVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(OrderVO orderVO) {

        String xid = RootContext.getXID();

        log.info("事务开启：xid={}", xid);

        OrderTbl order = new OrderTbl();
        order.setUserId(orderVO.getUserId());
        order.setCommodityCode(orderVO.getCommodityCode());
        order.setCount(orderVO.getCount());
        order.setMoney(5);

        List<OrderTbl> orders = Lists.newArrayList();
        orders.add(order);
        orders.add(order);
        orders.add(order);

        orderTblService.insert(order);

        //orderTblService.insertBatch(orders);

        //orderMapperExt.insertOrder(order);

        Integer orderId = order.getId();

        log.info("===orderId={}===", orderId);

        AccountVO accountVO = new AccountVO();
        accountVO.setUserId(orderVO.getUserId());
        accountVO.setMoney(order.getMoney().longValue() * orderVO.getCount());

        ResultMsg<Boolean> userFeignResult = userFeignClient.debit(accountVO);

        if (userFeignResult.getCode() != ResultCode.OK.getCode()) {
            throw new InternalApiException("The feign user request is error exception. The Code:" + userFeignResult.getCode());
        }

    }

}
