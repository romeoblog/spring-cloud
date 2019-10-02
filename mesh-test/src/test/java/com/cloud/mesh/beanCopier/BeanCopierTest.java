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
package com.cloud.mesh.beanCopier;

import com.cloud.mesh.entity.*;
import com.google.common.collect.Lists;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 使用CGlib实现Bean拷贝(BeanCopier)
 *
 * @author willlu.zheng
 * @date 2019-10-01
 */
public class BeanCopierTest {

    // 1. 属性名称、类型都相同:
    @Test
    public void normalCopyTest1() {
        // create(Class source, Class target, boolean useConverter)
        final BeanCopier beanCopier = BeanCopier.create(User.class, UserDto.class, false);
        User user = new User();
        user.setAge(10);
        user.setName("zhangsan");
        UserDto userDto = new UserDto();
        beanCopier.copy(user, userDto, null);
        Assert.assertEquals(10, userDto.getAge());
        Assert.assertEquals("zhangsan", userDto.getName());

        // 结论：属性名称相同类型相同的属性拷贝OK。
    }

    // 2. 属性名称相同、类型不同：
    @Test
    public void normalCopyTest2() {
        // create(Class source, Class target, boolean useConverter)
        final BeanCopier beanCopier = BeanCopier.create(User.class, UserWithDiffType.class, false);
        User user = new User();
        user.setAge(10);
        user.setName("zhangsan");
        UserWithDiffType userDto = new UserWithDiffType();
        beanCopier.copy(user, userDto, null);
        Assert.assertEquals(null, userDto.getAge());
        Assert.assertEquals("zhangsan", userDto.getName());

        //  结论：属性名称相同而类型不同的属性不会被拷贝。
        //  注意：即使源类型是原始类型(int, short和char等)，目标类型是其包装类型(Integer, Short和Character等)，或反之：都 不会被拷贝。
    }

    // 总结：
    // beanCopieranCopier只拷贝名称和类型都相同的属性。


    // 1. 不使用Converter
    @Test
    public void noConverterTest3() {
        Account po = new Account();
        po.setId(1);
        po.setCreateTime(new Date());
        po.setBalance(BigDecimal.valueOf(4000L));
        BeanCopier copier = BeanCopier.create(Account.class, AccountDto.class, false);
        AccountDto dto = new AccountDto();
        copier.copy(po, dto, null);
        // 类型不同，未拷贝
        Assert.assertNull(dto.getBalance());
        // 类型不同，未拷贝
        Assert.assertNull(dto.getCreateTime());
    }


    // 2. 使用Converter
    // 基于目标对象的属性出发，如果源对象有相同名称的属性，则调一次convert方法：
    // 注：一旦使用Converter，BeanCopier只使用Converter定义的规则去拷贝属性，所以在convert方法中要考虑所有的属性。
    @Test
    public void noConverterTest4() {
        Account po = new Account();
        po.setId(1);
        po.setCreateTime(new Date());
        po.setBalance(BigDecimal.valueOf(4000L));
        BeanCopier copier = BeanCopier.create(Account.class, AccountDto.class, true);
        AccountDto dto = new AccountDto();
        AccountConverter converter = new AccountConverter();
        copier.copy(po, dto, converter);
        // 类型不同，未拷贝
        Assert.assertEquals("4000", dto.getBalance());
        // 类型不同，未拷贝
        Assert.assertEquals("2018-12-13", dto.getCreateTime());
    }

    class AccountConverter implements Converter {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        @SuppressWarnings("rawtypes")
        @Override
        public Object convert(Object source, Class target, Object context) {
            if (source instanceof Integer) {
                return (Integer) source;
            } else if (source instanceof Date) {
                Date date = (Date) source;
                return sdf.format(date);
            } else if (source instanceof BigDecimal) {
                BigDecimal bd = (BigDecimal) source;
                return bd.toPlainString();
            }
            return null;
        }
    }

    // ==========性能测试=============

    @Test
    public void costTest1() {
        List<Account> list1 = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            Account po = new Account();
            po.setId(1);
            po.setCreateTime(new Date());
            po.setBalance(BigDecimal.valueOf(4000L));
            list1.add(po);
        }
        long start = System.currentTimeMillis();
        List<UserDto> list2 = Lists.newArrayList();
        for (Account user : list1) {
            UserDto dto = new UserDto();
            BeanUtils.copyProperties(user, dto);
            list2.add(dto);
        }
        long t = System.currentTimeMillis() - start;
        System.out.printf("took time: %d(ms)%n", t);

        // 206(ms) 198(ms) 200(ms) 216(ms) 210(ms)
    }

    @Test
    public void costTest2() {
        List<Account> list1 = Lists.newArrayList();
        for (int i = 0; i < 1000; i++) {
            Account po = new Account();
            po.setId(1);
            po.setCreateTime(new Date());
            po.setBalance(BigDecimal.valueOf(4000L));
            list1.add(po);
        }
        BeanCopier copier = BeanCopier.create(Account.class, UserDto.class, false);
        long start = System.currentTimeMillis();
        List<UserDto> list2 = Lists.newArrayList();
        for (Account user : list1) {
            UserDto dto = new UserDto();
//            BeanUtils.copyProperties((user, dto);
            copier.copy(user, dto, null);
            list2.add(dto);
        }
        long t = System.currentTimeMillis() - start;
        System.out.printf("took time: %d(ms)%n", t);

        // 1(ms)  0(ms) 0(ms) 1(ms) 0(ms) 0(ms)
    }


}
