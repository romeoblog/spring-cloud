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
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.cloud.example.base.SuperEntity;

/**
 * <p>
 * 
 * </p>
 *
 * @author Generator
 * @since 2019-06-20
 */
@TableName("mybatis_demo")
public class MybatisDemo extends SuperEntity<MybatisDemo> {

    private static final long serialVersionUID = 1L;

	private String name;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	private Integer type;
	private Integer age;
	private String address;
	private Integer sex;
	private Integer status;
	private Integer deleted;
	private Integer version;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"name\":\"")
				.append(name);
		sb.append(",\"id\":\"")
				.append(id);
		sb.append(",\"type\":\"")
				.append(type);
		sb.append(",\"age\":\"")
				.append(age);
		sb.append(",\"address\":\"")
				.append(address);
		sb.append(",\"sex\":\"")
				.append(sex);
		sb.append(",\"status\":\"")
				.append(status);
		sb.append(",\"deleted\":\"")
				.append(deleted);
		sb.append(",\"version\":\"")
				.append(version);
		sb.append('}');
		return sb.toString();
	}
}








