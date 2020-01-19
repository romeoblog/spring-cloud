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
package com.cloud.mesh.service.mybatis.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.mesh.model.mybatis.TestVO;

import java.util.List;

/**
 * MYBATIS栗子 接口层
 *
 * @author willlu.zheng
 * @date 2019-06-20
 */
public interface IMybatisService {


    /**
     * Mybatis plus 获取单条数据
     *
     * @param id 主健
     * @return T
     */
    TestVO getTest(Integer id);

    /**
     * Mybatis plus 获取多条数据
     *
     * @return List
     */
    List<TestVO> listTest();

    /**
     * Mybatis plus 获取多条数据(分页)
     *
     * @param pageNum  当前页码
     * @param pageSize 当前分页大小
     * @return Page
     */
    Page<TestVO> listRecord(Integer pageNum, Integer pageSize);

    /**
     * XML栗子- 获取多条数据（分页）
     *
     * @param pageNum  当前页码
     * @param pageSize 当前分页大小
     * @return Page
     */
    Page<TestVO> listRecord2(Integer pageNum, Integer pageSize);

    /**
     * Mybatis plus 更新信息
     *
     * @param testVO
     * @return b
     */
    Boolean updateTest(TestVO testVO);

    /**
     * Mybatis plus 删除信息
     *
     * @param id
     * @return b
     */
    Boolean deleteTest(Integer id);

    /**
     * Mybatis plus 新增信息
     *
     * @param testVO
     * @return b
     */
    Boolean insertTest(TestVO testVO);

}