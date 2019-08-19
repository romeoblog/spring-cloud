package com.cloud.mesh.model.search;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * The type document vo
 *
 * @author Benji
 * @date 2019-08-19
 */
@Data
public class DocumentVO implements Serializable {

    /**
     * The type document id
     */
    private String id;

    /**
     * The type document json object
     */
    private JSONObject jsonObject;

}
