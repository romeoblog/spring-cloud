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
package com.dg.mall.service.business.service.impl;

import com.dg.mall.common.enums.ResultCode;
import com.dg.mall.common.model.ResultMsg;
import com.dg.mall.model.order.OrderVO;
import com.dg.mall.model.storage.StorageVO;
import com.dg.mall.platform.exception.RequestException;
import com.dg.mall.service.business.feign.OrderFeignClient;
import com.dg.mall.service.business.feign.StorageFeignClient;
import com.dg.mall.service.business.service.IBusinessService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务发起方
 *
 * @author Benji
 * @date 2019-04-28
 */
@Service
@Slf4j
public class BusinessServiceImpl implements IBusinessService {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private StorageFeignClient storageFeignClient;

    /**
     * 发起方
     *
     * @param orderVO
     */
    @Override
    @GlobalTransactional
    public void purchase(OrderVO orderVO) {

        String xid = RootContext.getXID();

        log.info("事务开启：xid={}", xid);

        ResultMsg<Boolean> orderFeignResult = orderFeignClient.create(orderVO);

        if (orderFeignResult.getCode() != ResultCode.OK.getCode()) {
            throw new RequestException("The feign order request is error exception. The Code:" + orderFeignResult.getCode());
        }

        StorageVO storageVO = new StorageVO();
        storageVO.setUserId(orderVO.getUserId());
        storageVO.setCommodityCode(orderVO.getCommodityCode());
        storageVO.setCount(orderVO.getCount());

        ResultMsg<Boolean> storageFeignResult = storageFeignClient.deduct(storageVO);

        if (storageFeignResult.getCode() != ResultCode.OK.getCode()) {
            throw new RequestException("The feign storage request is error exception. The Code:" + storageFeignResult.getCode());
        }

    }
}
