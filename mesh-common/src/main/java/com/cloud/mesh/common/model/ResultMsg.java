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
package com.cloud.mesh.common.model;


import com.cloud.mesh.common.enums.BaseExtensibleEnum;
import com.cloud.mesh.common.enums.ResultCode;

import java.io.Serializable;

/**
 * 视图结果集
 *
 * @author Benji
 * @date 2019-04-02
 */
public class ResultMsg<T> implements Serializable {

    private int code;
    private String message;
    private T data = null;

    public static <T> ResultMsg<T> create() {
        return new ResultMsg<T>();
    }

    public static <T> ResultMsg<T> ok() {
        return ResultMsg.create().status(ResultCode.OK);
    }

    public static <T> ResultMsg<T> ok(T data) {
        return ResultMsg.ok().data(data);
    }

    public static <T> ResultMsg<T> error(int code, String message) {
        return ResultMsg.create().code(code).message(message);
    }

    public static <T> ResultMsg<T> error(String message) {
        return ResultMsg.create().status(ResultCode.ERROR).message(message);
    }

    public static <T> ResultMsg<T> error(BaseExtensibleEnum status) {
        return ResultMsg.create().status(status);
    }

    private ResultMsg() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultMsg status(BaseExtensibleEnum<Integer> status) {
        this.setCode(status.getCode());
        this.setMessage(status.getName());
        return this;
    }

    public ResultMsg code(int code) {
        this.code = code;
        return this;
    }

    public ResultMsg message(String message) {
        this.message = message;
        return this;
    }

    public ResultMsg data(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":")
                .append(code);
        sb.append(",\"message\":\"")
                .append(message).append('\"');
        sb.append(",\"data\":")
                .append(data);
        sb.append('}');
        return sb.toString();
    }
}
