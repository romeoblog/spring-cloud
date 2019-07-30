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