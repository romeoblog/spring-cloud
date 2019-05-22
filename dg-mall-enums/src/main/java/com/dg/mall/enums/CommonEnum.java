/*
 *  Copyright 2015-2019 dg-mall.com Group.
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
package com.dg.mall.enums;

import com.dg.mall.common.enums.BaseExtensibleEnum;

/**
 * Created with default template
 * Author: Joe Benji
 * Date: 2019-03-30
 * Time: 11:17
 * Description: 枚举公共类
 */
public class CommonEnum {

    /**
     * 是或否
     */
    public static class YesOrNoEnumBase extends BaseExtensibleEnum {

        public final static YesOrNoEnumBase YES = new YesOrNoEnumBase(1, "是");
        public final static YesOrNoEnumBase NO = new YesOrNoEnumBase(0, "否");

        public YesOrNoEnumBase(int code) {
            this(code, null);
        }

        public YesOrNoEnumBase(int code, String name) {
            super(code, name);
        }
    }

}
