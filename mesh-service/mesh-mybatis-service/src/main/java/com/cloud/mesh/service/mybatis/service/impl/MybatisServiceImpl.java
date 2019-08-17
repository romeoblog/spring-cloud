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
package com.cloud.mesh.service.mybatis.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.mesh.entity.MybatisDemo;
import com.cloud.mesh.mapper.MybatisDemoMapper;
import com.cloud.mesh.model.mybatis.TestVO;
import com.cloud.mesh.core.exception.InternalApiException;
import com.cloud.mesh.service.MybatisDemoService;
import com.cloud.mesh.service.mybatis.mapper.MybatisMapperExt;
import com.cloud.mesh.service.mybatis.service.IMybatisService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * MYBATIS栗子 实现层
 *
 * @author Benji
 * @date 2019-06-20
 */
@Service
public class MybatisServiceImpl implements IMybatisService {

    @Autowired
    private MybatisMapperExt mybatisMapperExt;

    @Autowired
    private MybatisDemoMapper mybatisDemoMapper;

    @Autowired
    private MybatisDemoService mybatisDemoService;

    @Override
    public TestVO getTest(Integer id) {
        MybatisDemo mybatisDemo = new MybatisDemo().selectById(id);
        if (mybatisDemo == null) {
            return null;
        }

        TestVO testVO = new TestVO();
        BeanUtils.copyProperties(mybatisDemo, testVO);

        return testVO;
    }

    @Override
    public List<TestVO> listTest() {
        List<MybatisDemo> list = new MybatisDemo().selectList("type = {0}", 1);
        if (list == null || list.size() == 0) {
            return null;
        }
        List<TestVO> listTest = list.stream().map(t -> {
            TestVO test = new TestVO();
            BeanUtils.copyProperties(t, test);
            return test;
        }).collect(Collectors.toList());

        return listTest;
    }

    @Override
    public Page<TestVO> listRecord(Integer pageNum, Integer pageSize) {
        Page<MybatisDemo> page = new Page<>(pageNum, pageSize);

        Wrapper wrapper = new EntityWrapper<MybatisDemo>();
        wrapper.where("type = {0}", 1);
        Page<MybatisDemo> list = new MybatisDemo().selectPage(page, wrapper);

        Page<TestVO> listTest = new MybatisDemo().selectPage(page, wrapper);

        BeanUtils.copyProperties(list, listTest);

        return listTest;
    }

    @Override
    public Page<TestVO> listRecord2(Integer pageNum, Integer pageSize) {
        Page<TestVO> page = new Page<>(pageNum, pageSize);
        List<TestVO> listTest = mybatisMapperExt.listPageTest(page);
        return page.setRecords(listTest);
    }

    @Override
    public Boolean updateTest(TestVO testVO) {

        Integer id = testVO.getId();
        MybatisDemo mybatisDemo = new MybatisDemo().selectById(id);
        if (mybatisDemo == null) {
            throw  new InternalApiException("找不到数据信息: id[" + id + "]");
        }

        BeanUtils.copyProperties(testVO, mybatisDemo);

        mybatisDemoMapper.updateById(mybatisDemo);
        return true;
    }

    @Override
    public Boolean deleteTest(Integer id) {
        MybatisDemo mybatisDemo = new MybatisDemo().selectById(id);
        if (mybatisDemo == null) {
            throw  new InternalApiException("找不到数据信息: id[" + id + "]");
        }
        mybatisDemoMapper.deleteById(mybatisDemo);
        return true;
    }

    @Override
    public Boolean insertTest(TestVO testVO) {
        testVO.setId(null);
        MybatisDemo mybatisDemo = new MybatisDemo();

        BeanUtils.copyProperties(testVO, mybatisDemo);

        mybatisDemoService.insert(mybatisDemo);

        return true;
    }
}
