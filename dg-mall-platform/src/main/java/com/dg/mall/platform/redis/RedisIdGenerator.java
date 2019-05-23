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
package com.dg.mall.platform.redis;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisIdGenerator
 *
 * @author Joe-Benji
 * @date 2019-04-08
 * @since 1.0.0
 */
public class RedisIdGenerator {

    public final static String ID_GENERATOR_SUBFIX = "_ID_GENERATOR";

    private final static long DEFAULT_INCRMENT_STEP = 1L;

    private final static long DEFAULT_INITIAL_VALUE = 0L;

    private final RedisTemplate<String, Long> redisTemplate;

    public RedisIdGenerator(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long nextId(String idKey) {
        return nextId(idKey, DEFAULT_INITIAL_VALUE, DEFAULT_INCRMENT_STEP);
    }

    public Long nextId(String idKey, long initValue) {
        return nextId(idKey, initValue, DEFAULT_INCRMENT_STEP);
    }

    public Long nextId(String idKey, long initValue, long step) {
        if (step <= 0) {
            step = DEFAULT_INCRMENT_STEP;
        }
        return initValue + redisTemplate.opsForValue().increment(idKey + ID_GENERATOR_SUBFIX, step);
    }

    public String nextId(String idKey, int keyLength) {
        return nextId(idKey, null, keyLength);
    }

    public String nextId(String idKey, String idPrefix, int keyLength) {
        Long idValue = nextId(idKey, DEFAULT_INITIAL_VALUE, DEFAULT_INCRMENT_STEP);
        String nextId = String.valueOf(idValue);

        if (keyLength > 0) {
            nextId = String.format("%0" + keyLength + "d", idValue);
        }

        if (idPrefix != null && idPrefix.trim() != "") {
            nextId = idPrefix + nextId;
        }

        return nextId;
    }

}
