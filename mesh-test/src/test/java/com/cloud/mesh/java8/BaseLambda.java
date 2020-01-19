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
package com.cloud.mesh.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lambda 基本用法
 * <p>Title: BaseLambda</p>
 * <p>Description: </p>
 *
 * @author willlu.zheng
 * @date 2019-06-11
 */
public class BaseLambda {
    public static void main(String[] args) {
        testForeach();
        testStreamDuplicates();
    }

    /**
     * Lambda 遍历
     */
    public static void testForeach() {
        // 定义一个数组
        String[] array = {
                "尼尔机械纪元",
                "关于我转生成为史莱姆这件事",
                "实力至上主义教师",
                "地狱少女"
        };

        // 转换成集合
        List<String> acgs = Arrays.asList(array);

        // 传统的遍历方式
        System.out.println("传统的遍历方式：");
        for (String acg : acgs) {
            System.out.println(acg);
        }
        System.out.println();

        // 使用 Lambda 表达式以及函数操作(functional operation)
        System.out.println("Lambda 表达式以及函数操作：");
        acgs.forEach((acg) -> System.out.println(acg));
        System.out.println();

        // 在 Java 8 中使用双冒号操作符(double colon operator)
        System.out.println("使用双冒号操作符：");
        acgs.forEach(System.out::println);
        System.out.println();
    }

    /**
     * Stream 去重复
     * String 和 Integer 可以使用该方法去重
     */
    public static void testStreamDuplicates() {
        System.out.println("Stream 去重复：");

        // 定义一个数组
        String[] array = {
                "尼尔机械纪元",
                "尼尔机械纪元",
                "关于我转生成为史莱姆这件事",
                "关于我转生成为史莱姆这件事",
                "实力至上主义教师",
                "实力至上主义教师",
                "地狱少女",
                "地狱少女"
        };

        // 转换成集合
        List<String> acgs = Arrays.asList(array);

        // Stream 去重复
        acgs = acgs.stream().distinct().collect(Collectors.toList());

        // 打印
        acgs.forEach(System.out::println);
    }
}