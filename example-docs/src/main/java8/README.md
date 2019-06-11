# 概述
以下列出两点重要特性：

* Lambda 表达式（匿名函数）
* Stream 多线程并行数据处理

# 新特性

+ 接口的默认方法只需要使用 `default` 关键字即可，这个特征又叫做 **扩展方法**
+ Lambda 表达式
+ Functional 接口 **函数式接口** 是指仅仅只包含一个抽象方法的接口，每一个该类型的 Lambda 表达式都会被匹配到这个抽象方法。你只需要给你的接口添加 `@FunctionalInterface` 注解
+ 使用 `::` 双冒号关键字来传递方法(静态方法和非静态方法)
+ Predicate 接口和 Lambda 表达式
+ Function 接口
+ Function 有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法。
    + compose 方法表示在某个方法之前执行。
    + andThen 方法表示在某个方法之后执行。
    + 注意：compose 和 andThen 方法调用之后都会把对象自己本身返回，这可以 **方便链式编程**。
+ Supplier 接口，返回一个任意范型的值，和 Function 接口不同的是该接口 **没有任何参数**。
+ Consumer 接口，接收一个任意范型的值，和 Function 接口不同的是该接口 **没有任何值** 。
+ Optional 类
    + Optional 不是接口而是一个类，这是个用来防止 `NullPointerException` 异常的辅助类型。
    + Optional 被定义为一个简单的容器，其值可能是 null 或者不是 null。
    + 在 Java8 之前一般某个函数应该返回非空对象但是偶尔却可能返回了 null，而在 Java8 中，不推荐你返回 null 而是返回 Optional。
    + 这是一个可以为 null 的容器对象。
    + 如果值存在则 `isPresent()` 方法会返回 true，调用 `get()` 方法会返回该对象。
    
# 小栗子
```java
package com.cloud.example.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lambda 基本用法
 * <p>Title: BaseLambda</p>
 * <p>Description: </p>
 *
 * @author Benji
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
```


