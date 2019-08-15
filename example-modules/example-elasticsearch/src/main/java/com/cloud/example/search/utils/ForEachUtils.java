package com.cloud.example.search.utils;

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
     * forEach element and provide start index
     *
     * @param startIndex the start index
     * @param elements   the elements
     * @param action     the action
     * @param <T>
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
}
