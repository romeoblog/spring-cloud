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

import java.util.Calendar;
import java.util.Date;

/**
 * The type month utils.
 *
 * @author Benji
 * @date 2019-05-24
 */
public class MonthUtils {

    /**
     * 取当前月份(yyyyMM)
     *
     * @return
     */
    public static String getCurrentMonth() {
        return DateFormatUtils.format(new Date(), "yyyyMM");
    }

    /**
     * 取月份month的上一个月份(yyyyMM)
     *
     * @param month
     * @return
     */
    public static String getLastMonth(String month) {
        return offsetMonth(month, -1);
    }

    /**
     * 取当前月份的上一个月份(yyyyMM)
     *
     * @return
     */
    public static String getLastMonth() {
        return offsetMonth(getCurrentMonth(), -1);
    }

    /**
     * 取指定月份第一天(yyyyMMdd)
     *
     * @param month
     * @return
     */
    public static String getBeginDayOfMonth(String month) {
        return month + "01";
    }

    /**
     * 取指定月份的最后一天(yyyyMMdd)
     *
     * @param month
     */
    public static String getEndDayOfMonth(String month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(DateUtils.getEndTimeOfMonth(month).getTime()));

        int cYear = calendar.get(Calendar.YEAR);
        int cMonth = calendar.get(Calendar.MONTH) + 1;
        int cDay = calendar.get(Calendar.DATE);
        return String.valueOf(cYear) + (cMonth < 10 ? ("0" + cMonth) : (String.valueOf(cMonth))) + (cDay < 10 ? ("0" + cDay) : (String.valueOf(cDay)));
    }

    /**
     * 由指定月份计算相对该月份的偏移月份(yyyyMM)
     *
     * @param calmonth
     * @param offset
     */
    public static String offsetMonth(String calmonth, int offset) {
        if (null == calmonth || calmonth.length() < 6) {
            return calmonth;
        }

        int yearCt = Integer.parseInt(calmonth.substring(0, 4));
        int monCt = Integer.parseInt(calmonth.substring(4, 6));
        String sb = calmonth.substring(6);
        int monTotal = yearCt * 12 + monCt + offset;

        int curYear = monTotal / 12;
        int curMon = monTotal % 12;
        if (curMon == 0) {
            return String.valueOf(curYear - 1) + "12" + sb;
        } else {
            return String.valueOf(curYear) + (curMon < 10 ? ("0" + curMon) : String.valueOf(curMon)) + sb;
        }
    }

    /**
     * 计算left-right的月份偏移量(left-right)(yyyyMM)
     *
     * @param left
     * @param right
     */
    public static int getOffset(String left, String right) {
        int leftYearCt = Integer.parseInt(left.substring(0, 4));
        int leftMonCt = Integer.parseInt(left.substring(4, 6), 10);
        int leftTotal = leftYearCt * 12 + leftMonCt;

        int rightYearCt = Integer.parseInt(right.substring(0, 4));
        int rightMonCt = Integer.parseInt(right.substring(4, 6), 10);
        int rightTotal = rightYearCt * 12 + rightMonCt;
        return leftTotal - rightTotal;
    }

}
