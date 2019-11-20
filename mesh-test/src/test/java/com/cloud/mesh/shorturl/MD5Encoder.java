package com.cloud.mesh.shorturl;

import java.security.MessageDigest;

/**
 * 字符串转换成对应16进制字符串
 *
 * @author willlu.zheng
 * @date 2019-11-18
 */
public class MD5Encoder {

    /**
     * 16进制元素
     */
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param b 要转换的字节数组
     * @return 返回16进制字符串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 字节转换成16进制字符串元素
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 将源字符串生成md5摘要用16进制转换的字符串
     *
     * @param origin
     * @return
     */
    public static String md5Encode(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString.trim();
            resultString = byteArrayToHexString(md.digest(resultString
                    .getBytes("UTF-8")));
        } catch (Exception ex) {
        }
        return resultString;
    }
}