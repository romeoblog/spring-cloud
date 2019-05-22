/*
 *  Copyright 2015-2019 dg-mall.com Group.
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
package com.dg.mall.platform.rest.advice;


import com.dg.mall.common.enums.ResultCode;
import com.dg.mall.common.model.ResultMsg;
import com.dg.mall.platform.exception.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * The Global Exception Handler
 *
 * @author Benji
 * @date 2019-05-14
 */
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(PermissionException.class)
    public ResultMsg permissionException(HttpServletRequest request, PermissionException ex) {
        logger.error(ExceptionUtils.getMessage(ex));
        return ResultMsg.error(ResultCode.NO_PREMISSION);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RepeatSumbitException.class)
    public ResultMsg repeatSumbitException(HttpServletRequest request, RepeatSumbitException ex) {
        logger.error(ExceptionUtils.getMessage(ex));
        return ResultMsg.error("重复提交!");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(CommonException.class)
    public ResultMsg commonException(HttpServletRequest request, CommonException ex) {
        logger.error(ExceptionUtils.getMessage(ex), ex);
        return ResultMsg.error(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnLoginException.class)
    public ResultMsg unLoginException(HttpServletRequest request, UnLoginException ex) {
        logger.error(ExceptionUtils.getMessage(ex));
        return ResultMsg.error(ResultCode.NOT_LOGIN);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DuplicateMachineException.class)
    public ResultMsg duplicateMachineException(HttpServletRequest request, DuplicateMachineException ex) {
        logger.error(ExceptionUtils.getMessage(ex));
        return ResultMsg.error(ResultCode.DUPLICATE_MACHINE);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(FrozenUserException.class)
    public ResultMsg frozenUserException(HttpServletRequest request, FrozenUserException ex) {
        logger.error(ExceptionUtils.getMessage(ex));
        return ResultMsg.error(ResultCode.FROZEN_USER);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ErrorSignException.class)
    public ResultMsg errorSignException(HttpServletRequest request, ErrorSignException ex) {
        logger.error(ExceptionUtils.getMessage(ex));
        return ResultMsg.error("签名错误");
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public ResultMsg exception(HttpServletRequest request, Exception ex) {
        logger.error(ExceptionUtils.getMessage(ex), ex);
        return ResultMsg.error(ResultCode.EXCEPTION);
    }

}
