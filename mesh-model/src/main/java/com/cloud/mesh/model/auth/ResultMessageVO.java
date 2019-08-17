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
package com.cloud.mesh.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The type authenticated result message
 *
 * @author Benji
 * @date 2019-06-24
 */
@Data
public class ResultMessageVO implements Serializable {

    /**
     * The type authenticated result username
     */
    @JsonProperty("user_name")
    private String userName;

    /**
     * The type authenticated result scope
     */
    private List<String> scope;

    /**
     * The type authenticated result active
     */
    private Boolean active;

    /**
     * The type authenticated result exp
     */
    private Long exp;

    /**
     * The type authenticated result authorities
     */
    private List<String> authorities;

    /**
     * The type authenticated result jti
     */
    private String jti;

    /**
     * The type authenticated result client_id
     */
    @JsonProperty("client_id")
    private String clientId;

    /**
     * The type authentication fail result error
     */
    private String error;

    /**
     * The type error description
     */
    @JsonProperty("error_description")
    private String errorDescription;

}
