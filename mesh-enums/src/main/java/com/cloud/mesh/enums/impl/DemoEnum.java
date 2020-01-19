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
package com.cloud.mesh.enums.impl;

import com.cloud.mesh.common.enums.BaseExtensibleEnum;

/**
 * Created with default template
 * Author: Joe willlu.zheng
 * Date: 2019-04-03
 * Time: 15:41
 * Description:
 */
public class DemoEnum {

    /**
     * 性别
     */
    public static class SexEnumBase extends BaseExtensibleEnum<Integer> {
        public final static SexEnumBase MAN = new SexEnumBase(1, "男");
        public final static SexEnumBase WOMAN = new SexEnumBase(2, "女");
        public final static SexEnumBase UNKNOWN = new SexEnumBase(0, "未知");

        public SexEnumBase(int code) {
            this(code, null);
        }

        public SexEnumBase(int code, String name) {
            super(code, name);
        }
    }

    /**
     * 银行编号
     */
    public static class BankEnumBase extends BaseExtensibleEnum<String> {
        public final static BankEnumBase BOC = new BankEnumBase("0001", "中国银行");
        public final static BankEnumBase ABC = new BankEnumBase("0002", "农业银行");
        public final static BankEnumBase CCB = new BankEnumBase("0003", "建设银行");
        public final static BankEnumBase ICBC = new BankEnumBase("0004", "工商银行");

        public BankEnumBase(String code) {
            this(code, null);
        }

        public BankEnumBase(String code, String name) {
            super(code, name);
        }
    }

}
