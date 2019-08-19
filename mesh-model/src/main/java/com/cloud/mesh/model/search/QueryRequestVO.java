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
