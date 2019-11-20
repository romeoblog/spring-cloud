package com.cloud.mesh.shorturl;

import org.junit.Test;

import java.util.Stack;

/**
 * @author willlu.zheng
 * @date 2019/11/17
 */
public class ShortTest2 {

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
        System.out.print(decode(1000000));
    }

}
