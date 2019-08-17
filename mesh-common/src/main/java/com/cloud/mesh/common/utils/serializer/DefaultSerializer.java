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
package com.cloud.mesh.common.utils.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.reflect.Type;

/**
 * Default Serializer
 *
 * @author Benji
 * @date 2019-04-30
 */
public class DefaultSerializer<T> implements JsonSerializer<T> {

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        String tName = src.getClass().getName();
        if ("java.lang.Class".equalsIgnoreCase(tName)) {
            return new JsonPrimitive(((Class<?>) src).getName());
        } else if (src instanceof java.util.Date) {
            return new JsonPrimitive(DateFormatUtils.format((java.util.Date) src, "yyyy-MM-dd HH:mm:ss"));
        } else {
            return new JsonPrimitive(String.valueOf(src));
        }
    }

}
