package com.example.cloud.repository;

import com.example.cloud.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description：
 *
 * @author fangliangsheng
 * @date 2019/3/28
 */
public interface AccountDAO extends JpaRepository<Account,Long> {

    Account findByUserId(String userId);

}
