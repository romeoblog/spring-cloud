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
package com.dg.mall.service.user.service.impl;

import com.dg.mall.entity.AccountTbl;
import com.dg.mall.model.user.AccountVO;
import com.dg.mall.service.user.service.IAccountService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账号实现类 Demo
 *
 * @author Benji
 * @date 2019-04-28
 */
@Service
@Slf4j
public class AccountServiceImpl implements IAccountService {

    /**
     * 金额扣除
     *
     * @param accountVO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void debit(AccountVO accountVO) {

        String xid = RootContext.getXID();

        log.info("事务开启：xid={}", xid);

        AccountTbl account = new AccountTbl().selectOne("user_id={0}", accountVO.getUserId());
        account.setMoney(account.getMoney() - accountVO.getMoney().intValue());

        account.updateById();

        if (accountVO.getMoney() == 35) {
            throw new RuntimeException("account branch exception");
        }
    }
}
