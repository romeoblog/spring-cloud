package com.cloud.mesh.search.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * The type document dto
 *
 * @author Benji
 * @date 2019-08-19
 */
@Data
public class DocumentDTO implements Serializable {

    /**
     * The type document id
     */
    private String id;

    /**
     * The type document json object
     */
    private JSONObject jsonObject;

}
