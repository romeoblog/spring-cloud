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
package com.dg.mall.enums.impl;

import com.dg.mall.common.enums.BaseExtensibleEnum;

/**
 * Created with default template
 * Author: Joe Benji
 * Date: 2019-04-03
 * Time: 15:41
 * Description:
 */
public class DemoEnum {

    /**
     * 性别
     */
    public static class SexEnumBase extends BaseExtensibleEnum {
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

}
