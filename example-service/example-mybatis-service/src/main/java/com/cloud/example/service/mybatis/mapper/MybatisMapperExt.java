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
