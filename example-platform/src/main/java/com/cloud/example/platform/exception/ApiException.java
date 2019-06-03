package com.cloud.example.platform.exception;

/**
 * The type Api Exception
 *
 * @author Benji
 * @date 2019-06-03
 */
public class ApiException extends RuntimeException{
    private static final long serialVersionUID = 8247610319171014183L;

    public ApiException(Throwable e) {
        super(e.getMessage(), e);
    }

    public ApiException(String message) {
        super(message);
    }
}
