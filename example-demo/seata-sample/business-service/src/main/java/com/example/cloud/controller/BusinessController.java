package com.example.cloud.controller;

import com.example.cloud.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BusinessController {

    private final static Logger logger = LoggerFactory.getLogger(BusinessController.class);

    @Autowired
    private BusinessService businessService;

    @RequestMapping("/purchase")
    public Boolean purchase(String userId, String commodityCode, Integer orderCount, HttpServletRequest request) {
        String queryString = request.getQueryString();
        String requestURL = request.getRequestURI();
        String method = request.getMethod();
//        try {
//            businessService.purchase(userId, commodityCode, orderCount);
//            logger.info("事务成功 {} {} {}", method, requestURL, queryString);
//            return true;
//        } catch (Exception e) {
//            logger.info("事务回滚 {} {} {}", method, requestURL, queryString);
//            return false;
//        }
        businessService.purchase(userId, commodityCode, orderCount);

        return true;
    }
}
