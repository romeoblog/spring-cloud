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
package com.dg.mall.common.enums;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Base Enum Class
 *
 * @author Benji
 * @date 2019-04-03
 */
@Slf4j
public abstract class BaseExtensibleEnum implements Serializable {

    private static Map<Class<? extends BaseExtensibleEnum>, Map<Object, BaseExtensibleEnum>> enums = new HashMap<>();

    private int code;

    private String name;

    @Override
    public String toString() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    protected BaseExtensibleEnum(int code) {
        this(code, null);
    }

    protected BaseExtensibleEnum(int code, String name) {
        this.code = code;
        this.name = name;
        register(this);
    }

    protected void register(BaseExtensibleEnum child) {
        Map<Object, BaseExtensibleEnum> eMap = enums.get(child.getClass());
        if (eMap == null) {
            eMap = new HashMap<>();
            enums.put(child.getClass(), eMap);
        } else {
            if (eMap.containsKey(code)) {
                log.warn("{} duplicated Code[{}]", child.getClass().getSimpleName(), code);
            }
        }
        eMap.put(code, child);
        if (!"".equals(name)) {
            eMap.put(name, child);
        }
    }

    private static Map<Object, BaseExtensibleEnum> findClassInfosMap(Class type) {
        Map<Object, BaseExtensibleEnum> map = enums.get(type);
        if (map == null) {
            try {
                Class.forName(type.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            map = enums.get(type);
        }

        return map;
    }

    public static <T extends BaseExtensibleEnum> T valueOf(String name, Class<T> type) {
        return (T) findClassInfosMap(type).get(name);
    }

    public static <T extends BaseExtensibleEnum> T findByCode(int code, Class<T> type) {
        return (T) findClassInfosMap(type).get(code);
    }

    public static Collection<BaseExtensibleEnum> values(Class<?> type) {
        return findClassInfosMap(type).values();
    }

}