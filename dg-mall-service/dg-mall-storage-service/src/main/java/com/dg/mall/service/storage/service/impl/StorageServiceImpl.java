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
package com.dg.mall.service.storage.service.impl;

import com.dg.mall.entity.StorageTbl;
import com.dg.mall.model.storage.StorageVO;
import com.dg.mall.service.storage.service.IStorageService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存实现类 Demo
 *
 * @author Benji
 * @date 2019-04-28
 */
@Service
@Slf4j
public class StorageServiceImpl implements IStorageService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deduct(StorageVO storageVO) {

        String xid = RootContext.getXID();

        log.info("事务开启：xid={}", xid);

        StorageTbl storage = new StorageTbl().selectOne("commodity_code = {0}", storageVO.getCommodityCode());
        storage.setCount(storage.getCount() - storageVO.getCount());

        if (storageVO.getCount() == 6) {
            throw new RuntimeException("storage branch exception");
        }

        storage.updateById();

        log.info("扣除库存成功");

    }

}
