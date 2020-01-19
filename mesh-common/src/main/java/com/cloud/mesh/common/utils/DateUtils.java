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

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The type date utils.
 *
 * @author willlu.zheng
 * @date 2019-05-24
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    /**
     * <p>
     * 把月份转换为日期格式(无异常转换)
     * </p>
     * <p>
     * <strong>日期时、分、秒、毫秒均为0</strong>
     * </p>
     * <p>
     * getDateByMonth("201302") = 2013-02-01 00:00:00
     * </p>
     *
     * @param month 月份格式为yyyyMM
     */
    public static Date getDateByMonth(String month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(StringUtils.substring(month, 0, 4)), Integer.parseInt(StringUtils.substring(month, 4, 6)) - 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * <p>
     * 把天转换为日期格式(无异常转换)
     * </p>
     * <p>
     * <strong>日期时、分、秒、毫秒均为0</strong>
     * </p>
     * <p>
     * getDateByDay("20130201") = 2013-02-01 00:00:00
     * </p>
     *
     * @param day 默认格式为yyyyMMdd
     */
    public static Date getDateByDay(String day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(StringUtils.substring(day, 0, 4)), Integer.parseInt(StringUtils.substring(day, 4, 6)) - 1, Integer.parseInt(StringUtils.substring(day, 6, 8)), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * <p>
     * 计算指定日期date相对指定类型type的偏移日期
     * </p>
     * <p>
     * <strong>日期时、分、秒、毫秒均为0</strong>
     * </p>
     *
     * @param date   日期
     * @param type   增量类型
     * @param offset 偏移量
     */
    public static Date offsetDate(Date date, int type, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置偏移量
        calendar.add(type, offset);
        return calendar.getTime();
    }

    /**
     * 由{@code java.sql.Timestamp }获取{@code java.util.Date }类型的时间格式
     *
     * @param timestamp
     * @return
     */
    public static Date getDateByTimestamp(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }


    /**
     * 取当前时间戳
     *
     * @return
     */
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 由{@code java.util.Date }获取{@code java.sql.Timestamp }类型的时间格式
     *
     * @param date
     */
    public static Timestamp getTimestampByDate(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 设置日期的时、分、秒和毫秒信息，返回{@code java.sql.Timestamp }类型时间
     *
     * @param date
     * @param hourOfDay
     * @param minute
     * @param second
     * @param millisecond
     */
    public static Timestamp getTimestampByDate(Date date, int hourOfDay, int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * <p>
     * 取指定月份的开始时间
     * </p>
     * <p>
     * <strong>日期时、分、秒、毫秒均为0</strong>
     * </p>
     * <p>
     * getBeginTimeOfMonth("201302") = 2013-02-01 00:00:00.0
     * </p>
     *
     * @param month 格式为yyyyMM
     */
    public static Timestamp getBeginTimeOfMonth(String month) {
        Date date = DateUtils.getDateByMonth(month);
        return DateUtils.getTimestampByDate(date, 0, 0, 0, 0);
    }

    /**
     * <p>
     * 取指定月份的结束时间
     * </p>
     * <p>
     * <strong>日期时、分、秒、毫秒均为0</strong>
     * </p>
     * <p>
     * getEndTimeOfMonth("201302") = 2013-02-28 23:59:59.999
     * </p>
     *
     * @param month 格式为yyyyMM
     */
    public static Timestamp getEndTimeOfMonth(String month) {
        Date curMonthFirstDate = DateUtils.getDateByMonth(month);
        Date nextMonthFirstDate = DateUtils.offsetDate(curMonthFirstDate, Calendar.MONTH, 1);
        Date endTime = DateUtils.offsetDate(nextMonthFirstDate, Calendar.DATE, -1);
        return DateUtils.getTimestampByDate(endTime, 23, 59, 59, 999);
    }


    /**
     * <p>
     * 取指定日期的开始时间
     * </p>
     * <p>
     * <strong>日期时、分、秒、毫秒均为0</strong>
     * </p>
     * <p>
     * getBeginTimeOfDay("20130203") = 2013-02-03 00:00:00.0
     * </p>
     *
     * @param day 格式为yyyyMMdd
     */
    public static Timestamp getBeginTimeOfDay(String day) {
        Date date = DateUtils.getDateByDay(day);
        return DateUtils.getTimestampByDate(date, 0, 0, 0, 0);
    }

    /**
     * <p>
     * 取指定日期的结束时间
     * </p>
     * <p>
     * <strong>日期时、分、秒、毫秒均为0</strong>
     * </p>
     * <p>
     * getEndTimeOfDay("20130203") = 2013-02-03 23:59:59.999
     * </p>
     *
     * @param day 格式为yyyyMMdd
     */
    public static Timestamp getEndTimeOfDay(String day) {
        Date date = DateUtils.getDateByDay(day);
        return DateUtils.getTimestampByDate(date, 23, 59, 59, 999);
    }


    /**
     * 取当前日期
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 取当前日期（字符串）
     *
     * @param date
     * @return
     */
    public static String getCurrentDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 取相对于指定日期date的前一天
     *
     * @param date
     */
    public static Date getLastDate(Date date) {
        return DateUtils.addDays(date, -1);
    }

    /**
     * 取当前日期的前一天
     */
    public static Date getLastDate() {
        return DateUtils.getLastDate(DateUtils.getCurrentDate());
    }

    /**
     * 取相对于指定日期date的后一天
     *
     * @param date
     */
    public static Date getNextDate(Date date) {
        return DateUtils.addDays(date, 1);
    }

    /**
     * 取当前日期的后一天
     */
    public static Date getNextDate() {
        return DateUtils.getNextDate(DateUtils.getCurrentDate());
    }

    /**
     * 获取指定月 的当前月底23:59:59时间 如：2018-04-12 12:10:10  --> 2019-05-30 23:59:59
     *
     * @return
     */
    public static Date getMonthNextDate(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getMonthNextDate(13));
        }
    }
}
