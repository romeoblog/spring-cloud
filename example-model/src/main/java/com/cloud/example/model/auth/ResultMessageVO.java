package com.cloud.example.model.auth;

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
    private String user_name;

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
    private String client_id;

    /**
     * The type authentication fail result error
     */
    private String error;

    /**
     * The type error description
     */
    private String error_description;

}
