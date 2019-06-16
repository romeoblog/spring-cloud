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
package com.cloud.example.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.cloud.example.base.SuperEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Generator
 * @since 2019-05-06
 */
@TableName("dg_account")
public class DgAccount extends SuperEntity<DgAccount> {

    private static final long serialVersionUID = 1L;

    /**
     * 会员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 会员名
     */
    @TableField("account_name")
    private String accountName;
    /**
     * 密码
     */
    private String password;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 性别 1男0女
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 出生日
     */
    private Date birthday;
    /**
     * 注册日期
     */
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DgAccount{" +
                ", id=" + id +
                ", accountName=" + accountName +
                ", password=" + password +
                ", email=" + email +
                ", sex=" + sex +
                ", mobile=" + mobile +
                ", birthday=" + birthday +
                ", createTime=" + createTime +
                "}";
    }
}
