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
package com.cloud.mesh.core.feign.logger;


import feign.Request;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The type Info Feign Logger
 *
 * @author Benji
 * @date 2019-06-03
 */
public class InfoFeignLogger extends feign.Logger {

    /**
     * The type logger Object
     */
    private final Logger logger;

    public InfoFeignLogger() {
        this(feign.Logger.class);
    }

    public InfoFeignLogger(Class<?> clazz) {
        this(LoggerFactory.getLogger(clazz));
    }

    public InfoFeignLogger(String name) {
        this(LoggerFactory.getLogger(name));
    }

    InfoFeignLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Logger request loading
     *
     * @param configKey the configKey
     * @param logLevel  the logLevel
     * @param request   the request
     */
    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        if (logger.isInfoEnabled()) {
            super.logRequest(configKey, logLevel, request);
        }
    }

    /**
     * Logger and rebuffer response
     *
     * @param configKey   the configKey
     * @param logLevel    the logLevel
     * @param response    the response
     * @param elapsedTime the elapsedTime
     * @return
     * @throws IOException
     */
    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel,
                                              Response response, long elapsedTime) throws IOException {
        if (logger.isInfoEnabled()) {
            return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
        }
        return response;
    }

    /**
     * log
     *
     * @param configKey the configKey
     * @param format    the format
     * @param args      the args
     */
    @Override
    protected void log(String configKey, String format, Object... args) {
        // Not using SLF4J's support for parameterized messages (even though it would be more efficient) because it would
        // require the incoming message formats to be SLF4J-specific.
        if (logger.isInfoEnabled()) {
            logger.info(String.format(methodTag(configKey) + format, args));
        }
    }
}