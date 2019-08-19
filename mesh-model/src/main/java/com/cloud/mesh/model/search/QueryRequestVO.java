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
package com.cloud.mesh.model.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * A Query that matches documents matching QueryRequestVO
 *
 * @author willlu.zheng
 * @date 2019-08-19
 */
@Data
public class QueryRequestVO implements Serializable {

    /**
     * the index
     */
    @ApiModelProperty(value = "index", required = true)
    private String index;

    /**
     * the list of include field
     */
    @ApiModelProperty(value = "fields")
    private String fields;

    /**
     * the matchStr of the field
     */
    @ApiModelProperty(value = "matchStr")
    private String matchStr;

    /**
     * the current page
     */
    @ApiModelProperty(value = "currentPage")
    private Integer currentPage;

    /**
     * the page size
     */
    @ApiModelProperty(value = "pageSize")
    private Integer pageSize;

    /**
     * the start time,long
     */
    @ApiModelProperty(value = "fields")
    private Long startTime;

    /**
     * the end time,long
     */
    @ApiModelProperty(value = "endTime")
    private Long endTime;

    /**
     * The name of the field with sort desc
     */
    @ApiModelProperty(value = "sortField")
    private String sortField;

}
