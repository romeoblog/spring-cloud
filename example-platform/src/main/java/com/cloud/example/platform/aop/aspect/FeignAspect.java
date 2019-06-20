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
package com.cloud.example.platform.aop.aspect;


import com.cloud.example.common.utils.JacksonUtils;
import com.cloud.example.platform.aop.JoinPointContext;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The type Aop Aspect with FeignAspect
 *
 * @author Benji
 * @date 2019-04-16
 */
@Aspect
@Component
@Slf4j
public class FeignAspect {

    private final static String LOG_ARG = "{}";
    private static final String TEXT_DELIMITER = "=";
    private static final String TARGET = "Target" + TEXT_DELIMITER;
    private static final String METHOD = "Method" + TEXT_DELIMITER;
    private static final String SERVER_PORT = "ServerPort" + TEXT_DELIMITER;
    private static final String REQUEST_METHOD = "RequestMethod" + TEXT_DELIMITER;
    private static final String SEATA_XID = "Xid" + TEXT_DELIMITER;
    private static final String RUNTIME = "RunTime" + TEXT_DELIMITER;
    private static final String ARGS = "Args" + TEXT_DELIMITER;
    private static final String RETURN = "Return" + TEXT_DELIMITER;
    private static final String EXCEPTION = "Exception" + TEXT_DELIMITER;
    private static final String ERROR_MSG = "ErrorMsg" + TEXT_DELIMITER;
    private static final String SEPARATOR = ", ";
    private static final String TIME_UNIT_NAME = "ms";

    private String serverPort, requestMethod;

    @Pointcut("execution(* com.cloud.example.api.*.*(..))")
    public void feign() {
    }

    @Around("feign()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        JoinPointContext ctx = new JoinPointContext(joinPoint);
        long start = System.nanoTime();
        try {
            final Object returnValue = joinPoint.proceed();
            loggerRequest(ctx, start, returnValue);
            return returnValue;
        } catch (Exception ex) {
            loggerRequestException(ctx, start, ex);
            throw ex;
        }
    }

    public void loggerRequest(JoinPointContext ctx, long start, final Object returnValue) {
        if (!log.isInfoEnabled()) {
            return;
        }

        init();
        List<Object> args = new ArrayList<>();
        long stop = System.nanoTime();
        long diffNanos = stop - start;
        long diff = TimeUnit.MILLISECONDS.convert(diffNanos, TimeUnit.NANOSECONDS);
        StringBuilder sb = new StringBuilder();
        sb.append(TARGET).append(ctx.getTargetClass().getName()).append(SEPARATOR);
        sb.append(METHOD).append(ctx.getMethod().getName()).append(SEPARATOR);
        sb.append(SERVER_PORT).append(this.serverPort).append(SEPARATOR);
        sb.append(REQUEST_METHOD).append(this.requestMethod).append(SEPARATOR);
        sb.append(SEATA_XID).append(RootContext.getXID()).append(SEPARATOR);
        sb.append(RUNTIME).append(diff).append(TIME_UNIT_NAME).append(SEPARATOR);
        if (ctx.getArgs().length > 0) {
            sb.append(ARGS).append(LOG_ARG).append(SEPARATOR);
            args.add(ctx.getArgs());
        }
        if (returnValue != null) {
            sb.append(RETURN).append(LOG_ARG);
            args.add(JacksonUtils.toJson(returnValue));
        }
        log.info(sb.toString(), args.toArray());
    }

    public void loggerRequestException(JoinPointContext ctx, long start, Throwable ex) {
        init();
        if (ex instanceof UndeclaredThrowableException) {
            ex = ex.getCause();
        }
        List<Object> args = new ArrayList<>();
        long stop = System.nanoTime();
        long diffNanos = stop - start;
        long diff = TimeUnit.MILLISECONDS.convert(diffNanos, TimeUnit.NANOSECONDS);
        StringBuilder sb = new StringBuilder();
        sb.append(TARGET).append(ctx.getTargetClass().getName()).append(SEPARATOR);
        sb.append(METHOD).append(ctx.getMethod().getName()).append(SEPARATOR);
        sb.append(SERVER_PORT).append(this.serverPort).append(SEPARATOR);
        sb.append(REQUEST_METHOD).append(this.requestMethod).append(SEPARATOR);
        sb.append(SEATA_XID).append(RootContext.getXID()).append(SEPARATOR);
        sb.append(RUNTIME).append(diff).append(TIME_UNIT_NAME).append(SEPARATOR);
        if (ctx.getArgs().length > 0) {
            sb.append(ARGS).append(LOG_ARG).append(SEPARATOR);
            args.add(ctx.getArgs());
        }
        sb.append(EXCEPTION).append(ex.getClass().getName()).append(SEPARATOR);
        sb.append(ERROR_MSG).append(ExceptionUtils.getMessage(ex)).append(SEPARATOR);
        log.error(sb.toString(), args.toArray(), ex);
    }

    private void init() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        Integer serverPort = request.getServerPort();
        String method = request.getMethod();

        this.serverPort = serverPort.toString();
        this.requestMethod = method;
    }

}