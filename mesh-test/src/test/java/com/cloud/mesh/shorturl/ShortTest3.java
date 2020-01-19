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
package com.cloud.mesh.shorturl;

import org.junit.Test;

import java.util.Stack;

/**
 * @author willlu.zheng
 * @date 2019/11/17
 */
public class ShortTest3 {

    private static final char[] array = {
            'P', 'm', 'A', 'b', 'I', 'c', 'J', 'z', 'H', 'd',
            'a', '6', 'p', 'W', 'O', 'e', 'Q', 'f', '7', 'K',
            'h', 'C', 'i', '5', 'j', 'R', 'g', 'D', 'k', 'Z',
            'M', '8', 'n', '4', 'F', 'L', 'E', 'o', '9', 'X',
            'r', 'T', 's', 'B', '3', 't', 'V', 'G', 'u', 'Y',
            'v', '2', 'w', 'x', '1', 'y', 'U', '0', 'N', 'l',
            'S', 'q'
    };

    public static String decode(long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder(6);
        do {
            stack.add(array[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        } while (rest != 0);
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    @Test
    public void test() {
        for (int i=0;i<100; i++){
            System.out.print(decode(i)+", ");
        }
    }

}
