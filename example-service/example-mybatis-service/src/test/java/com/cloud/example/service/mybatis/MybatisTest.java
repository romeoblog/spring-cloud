package com.cloud.example.service.mybatis;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.example.model.mybatis.TestVO;
import com.cloud.example.service.mybatis.service.IMybatisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The type JUnit Test
 *
 * @author Benji
 * @date 2019-07-29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MybatisTest {

    @Autowired
    private IMybatisService mybatisService;

    @Test
    public void testSecKill() {

        Page<TestVO> testVOPage = mybatisService.listRecord(1, 10);

        System.out.println(testVOPage.getRecords());
    }
}