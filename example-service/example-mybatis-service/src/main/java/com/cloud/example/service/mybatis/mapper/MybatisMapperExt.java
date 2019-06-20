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
package com.cloud.example.service.mybatis.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.example.model.mybatis.TestVO;

import java.util.List;

/**
 * MYBATIS栗子 Mapper层
 *
 * @author Benji
 * @date 2019-06-20
 */
public interface MybatisMapperExt {

    /**
     * XML栗子- 获取多条数据（分页）
     *
     * @param page
     * @return
     */
    List<TestVO> listPageTest(Page<TestVO> page);

}
