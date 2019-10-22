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
 * 业务编码定义
 * 定义分成两层:
 * 1、可以显示给前端用户：code > 0
 * 2、不可显示给前端用户：code = -1(调试过程错误信息、用于内部调试使用)
 *
 * @author Benji
 * @date 2019-04-02
 */
public class ResultCode extends BaseExtensibleEnum<Integer> {

    public final static ResultCode OK = new ResultCode(200, "正常");

    /**
     * 服务内部错误，不可直接显示给前端面
     **/
    public final static ResultCode ERROR = new ResultCode(-1, "业务方提供错误原因");

    /**
     * NOT_FOUND_EXP异常原因一般不会存在，只会在URL写错的时候存在
     **/
    public final static ResultCode NOT_FOUND_EXP = new ResultCode(404, "服务不存在");

    /**
     * 不可预知的异常原因
     */
    public final static ResultCode EXCEPTION = new ResultCode(1020, "网络错误");

    /**
     * auth过程错误信息
     *
     * @param code
     */
    public final static ResultCode NO_PREMISSION = new ResultCode(9000, "无操作权限");
    public final static ResultCode ERROR_SIGN = new ResultCode(9010, "签名错误");
    public final static ResultCode ERROR_ACCOUNT = new ResultCode(9020, "用户名或密码错误");
    public final static ResultCode ERROR_TOKEN = new ResultCode(9030, "TOKEN错误或过期");
    public final static ResultCode NOT_LOGIN = new ResultCode(9040, "用户未登录");
    public final static ResultCode NO_PERSENT = new ResultCode(9050, "用户不存在");
    public final static ResultCode FROZEN_USER = new ResultCode(9060, "用户被冻结");
    public final static ResultCode DUPLICATE_PHONE = new ResultCode(9070, "手机号重复");
    public final static ResultCode DUPLICATE_USERNAME = new ResultCode(9080, "用户名重复");
    public final static ResultCode DUPLICATE_MACHINE = new ResultCode(9090, "在其他设备上登陆");
    public final static ResultCode ERROR_VALID_CODE = new ResultCode(9999, "验证码错误");

    //Runtime
    private ResultCode(int code) {
        super(code);
    }

    private ResultCode(int code, String name) {
        super(code, name);
    }
}
