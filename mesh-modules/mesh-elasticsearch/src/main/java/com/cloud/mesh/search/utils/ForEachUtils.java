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
package com.cloud.mesh.search.utils;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * The type ForEach Utils
 *
 * @author Benji
 * @date 2019-08-15
 */
public class ForEachUtils {

    /**
     * ForEach element and provide start index
     *
     * @param startIndex the start index
     * @param elements   the elements
     * @param action     the action
     * @param <T>        t
     */
    public static <T> void forEach(int startIndex, Iterable<? extends T> elements, BiConsumer<Integer, ? super T> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        if (startIndex < 0) {
            startIndex = 0;
        }
        int index = 0;
        for (T element : elements) {
            index++;
            if (index <= startIndex) {
                continue;
            }

            action.accept(index - 1, element);
        }
    }

    /**
     * ForEach element
     *
     * @param elements the elements
     * @param action   the action
     * @param <T>      t
     */
    public static <T> void forEach(Iterable<? extends T> elements, BiConsumer<Integer, ? super T> action) {
        forEach(0, elements, action);
    }
}
