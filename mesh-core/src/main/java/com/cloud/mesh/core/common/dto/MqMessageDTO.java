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
package com.cloud.mesh.core.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Mq消息DTO
 *
 * @author Benji
 * @date 2019-07-05
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class MqMessageDTO implements java.io.Serializable {

    /**
     * message tag
     */
    @NonNull
    private String tag;

    /**
     * message body
     */
    @NonNull
    private String body;

    /**
     * message key
     */
    private String key;

    /**
     * 设置延时等级: 只支持以下类型等级
     * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    private Integer delayTimeLevel;

}
