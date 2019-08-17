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

import feign.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignLoggerFactory;

/**
 * The type info feign logger factory
 *
 * @author Benji
 * @date 2019-06-03
 */
public class InfoFeignLoggerFactory implements FeignLoggerFactory {

    @Override
    public Logger create(Class<?> type) {
        return new InfoFeignLogger(LoggerFactory.getLogger(type));
    }
}