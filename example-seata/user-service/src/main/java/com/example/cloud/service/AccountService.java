package com.example.cloud.service;

import com.example.cloud.entity.Account;
import com.example.cloud.repository.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2019-04-05
 */
@Service
public class AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Transactional
    public void debit(String userId, BigDecimal num){
        Account account = accountDAO.findByUserId(userId);
        account.setMoney(account.getMoney().subtract(num));
        accountDAO.save(account);

        if (num.equals(new BigDecimal("25"))){
            throw new RuntimeException("account branch exception");
        }
    }
}
