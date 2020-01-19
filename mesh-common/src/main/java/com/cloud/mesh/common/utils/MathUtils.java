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
package com.cloud.mesh.common.utils;

import java.math.BigDecimal;

/**
 * The type math utils.
 *
 * @author willlu.zheng
 * @date 2019-05-24
 */
public class MathUtils {

    private static final int DEF_DIV_SCALE = 10;

    private MathUtils() {
    }

    /**
     * @param v1 被加数
     * @param v2 加数
     * @return
     * @Desc 精确加法运算
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * @param v1 被减数
     * @param v2 减数
     * @return
     * @Desc 精确减法运算
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * @param v1 被乘数
     * @param v2 乘数
     * @return
     * @Desc 精确乘法运算
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * @param v1 被除数
     * @param v2 除数
     * @return
     * @Desc 相对准确除法运算, 当除不尽时精确到小数点后DEF_DIV_SCALE位
     */
    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * @param v1    被除数
     * @param v2    除数
     * @param scale
     * @return
     * @Desc 相对准确除法运算, 当除不尽时精确到小数点后scale位
     */
    public static double divide(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @param v     需要四舍五入处理的数字
     * @param scale 精确(小数位数)
     * @return
     * @Desc 精确小数位四舍五入处理
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return new BigDecimal(Double.toString(v)).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
