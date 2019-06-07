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

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cloud.example.platform.base.SuperEntity;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Generator
 * @since 2019-06-07
 */
@TableName("tb_user")
public class TbUser extends SuperEntity<TbUser> {

    private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
    /**
     * 用户名
     */
	private String username;
    /**
     * 密码，加密存储
     */
	private String password;
    /**
     * 注册手机号
     */
	private String phone;
    /**
     * 注册邮箱
     */
	private String email;
	private Date created;
	private Date updated;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":\"")
				.append(id);
		sb.append(",\"username\":\"")
				.append(username);
		sb.append(",\"password\":\"")
				.append(password);
		sb.append(",\"phone\":\"")
				.append(phone);
		sb.append(",\"email\":\"")
				.append(email);
		sb.append(",\"created\":\"")
				.append(created);
		sb.append(",\"updated\":\"")
				.append(updated);
		sb.append('}');
		return sb.toString();
	}
}








