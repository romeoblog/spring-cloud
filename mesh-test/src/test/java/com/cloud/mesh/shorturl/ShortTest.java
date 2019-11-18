package com.cloud.mesh.shorturl;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author willlu.zheng
 * @date 2019/11/17
 */
public class ShortTest {

    static final char[] DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private AtomicLong sequence = new AtomicLong(1000000000000001L);

    private String shorten(String longUrl) {
        long myseq = sequence.incrementAndGet();
        String shortUrl = to62RadixString(myseq);
        return shortUrl;
    }

    private String to62RadixString(long seq) {
        StringBuilder sBuilder = new StringBuilder();
        while (true) {
            int remainder = (int) (seq % 62);
            sBuilder.append(DIGITS[remainder]);
            seq = seq / 62;
            if (seq == 0) {
                break;
            }
        }
        return sBuilder.toString();
    }

    @Test
    public void test() {
        for (int i=0;i<100; i++){
            System.out.print(shorten("")+", ");
        }
    }

}
