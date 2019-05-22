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
package com.dg.mall.common.utils.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Data Entity Class Parse
 *
 * @author Benji
 * @date 2019-04-30
 */
public class ClassDeserializer implements JsonDeserializer<Class<?>> {

    @Override
    public Class<?> deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        try {
            return Class.forName(json.getAsJsonPrimitive().getAsString());
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new JsonParseException("数据转换对象出错,出错数据[" + json.getAsJsonPrimitive().getAsString() + "], 出错信息[" + ex.getMessage() + "]");
        }
    }

}
