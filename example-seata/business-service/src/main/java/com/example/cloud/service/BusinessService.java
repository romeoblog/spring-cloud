package com.example.cloud.service;

import com.example.cloud.feign.OrderFeignClient;
import com.example.cloud.feign.StorageFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    @Autowired
    private StorageFeignClient storageFeignClient;
    @Autowired
    private OrderFeignClient orderFeignClient;

    //@GlobalTransactional
    public void purchase(String userId, String commodityCode, int orderCount) {
        storageFeignClient.deduct(commodityCode, orderCount);

        orderFeignClient.create(userId, commodityCode, orderCount);
    }
}
