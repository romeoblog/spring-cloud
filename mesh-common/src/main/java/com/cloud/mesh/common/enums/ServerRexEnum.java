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
package com.cloud.mesh.common.enums;

/**
 * 服务相关枚举
 *
 * @author willlu.zheng
 * @date 2019-04-09
 * @since 1.0.0
 */
public class ServerRexEnum {

    /**
     * 各服务端url前缀
     */
    public static class UriRexEnumBase extends BaseExtensibleEnum<Integer> {
        private static final long serialVersionUID = -3123570392894951511L;

        //用户服务
        public static final UriRexEnumBase USERREX = new UriRexEnumBase(1, "/user");
        //订单服务
        public static final UriRexEnumBase ORDERREX = new UriRexEnumBase(2, "/order");
        //库存服务
        public static final UriRexEnumBase INVENTTORYREX = new UriRexEnumBase(3, "/inventory");

        private UriRexEnumBase(int code) {
            this(code, null);
        }

        private UriRexEnumBase(int code, String name) {
            super(code, name);
        }
    }

}
