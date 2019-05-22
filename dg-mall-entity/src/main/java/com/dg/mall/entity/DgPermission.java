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
package com.dg.mall.entity;

import java.io.Serializable;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.dg.mall.platform.base.SuperEntity;

/**
 * <p>
 *
 * </p>
 *
 * @author Generator
 * @since 2019-05-06
 */
@TableName("dg_permission")
public class DgPermission extends SuperEntity<DgPermission> {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    private Integer id;
    /**
     * 方法类型
     */
    private String method;
    /**
     * 网关前缀
     */
    @TableField("gateway_prefix")
    private String gatewayPrefix;
    /**
     * 服务前缀
     */
    @TableField("service_prefix")
    private String servicePrefix;
    /**
     * 请求路径
     */
    private String uri;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 更新日期
     */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getGatewayPrefix() {
        return gatewayPrefix;
    }

    public void setGatewayPrefix(String gatewayPrefix) {
        this.gatewayPrefix = gatewayPrefix;
    }

    public String getServicePrefix() {
        return servicePrefix;
    }

    public void setServicePrefix(String servicePrefix) {
        this.servicePrefix = servicePrefix;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DgPermission{" +
                ", id=" + id +
                ", method=" + method +
                ", gatewayPrefix=" + gatewayPrefix +
                ", servicePrefix=" + servicePrefix +
                ", uri=" + uri +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                "}";
    }
}
