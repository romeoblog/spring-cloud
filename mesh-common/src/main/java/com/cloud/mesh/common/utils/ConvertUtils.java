/**
 * Copyright (c) 2005-2010 springside.org.cn
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * <p>
 * $Id: ConvertUtils.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package com.cloud.mesh.common.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * 转化工具类
 *
 * @author willlu.zheng
 * @date 2019-09-17
 */
public class ConvertUtils {

    private final static String SEPARATOR = "_";

    static {
        registerConverter();
    }

    /**
     * 转换字符串到相应类型.
     *
     * @param value  待转换的字符串.
     * @param toType 转换目标类型.
     */
    public static <T> T convert(Object value, Class<T> toType) {
        try {
            return (T) BeanUtilsBean.getInstance().getConvertUtils().convert(value, toType);
        } catch (Exception e) {
            throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
        }
    }

    public static <T> List<T> convert(List<Map<String, Object>> sourceListMap, Class<T> destType) {
        List<T> resultData = new ArrayList<>();
        sourceListMap.forEach(sourceMap -> resultData.add(convert(sourceMap, destType)));
        return resultData;
    }

    /**
     * 转换Map为特定对象
     *
     * @param sourceMap
     * @param destType
     * @param <T>
     * @return
     */
    public static <T> T convert(Map<String, Object> sourceMap, Class<T> destType) {
        CaseInsensitiveMap targetMap = new CaseInsensitiveMap();
        sourceMap.forEach((key, value) -> targetMap.put(StringUtils.replaceAll(key, SEPARATOR, ""), value));
        try {
            T instance = destType.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(destType);
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            Arrays.stream(descriptors).forEach(descriptor -> {
                String propertyName = descriptor.getName();
                Class<?> type = descriptor.getPropertyType();
                if (targetMap.containsKey(propertyName)) {
                    Object value = targetMap.get(propertyName);
                    Object[] args = new Object[]{convert(value, type)};
                    try {
                        descriptor.getWriteMethod().invoke(instance, args);
                    } catch (Exception ex) {
                        throw new RuntimeException("Map convert to Bean[" + destType.getName() + "] Error, ErrorMsg[" + ex.getMessage() + "]", ex);
                    }
                }
            });
            return instance;
        } catch (Exception ex) {
            throw new RuntimeException("Map convert to Bean[" + destType.getName() + "] Error, ErrorMsg[" + ex.getMessage() + "]", ex);
        }
    }

    /**
     * 定义日期Converter的格式: yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
     * 注意：
     * 前台在转换过程中不需要转换异常报错
     */
    private static void registerConverter() {
        DateConverter dc = new DateConverter(null);
        dc.setUseLocaleFormat(true);
        dc.setPatterns(new String[]{"yyyyMMdd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyyMM"});
        BeanUtilsBean.getInstance().getConvertUtils().register(dc, Date.class);
        SqlDateConverter sdc = new SqlDateConverter(null);
        sdc.setUseLocaleFormat(true);
        sdc.setPatterns(new String[]{"yyyyMMdd", "yyyy-MM-dd", "yyyyMM"});
        BeanUtilsBean.getInstance().getConvertUtils().register(sdc, java.sql.Date.class);
    }
}
