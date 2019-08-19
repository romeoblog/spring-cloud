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
package com.cloud.mesh.java7;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JDK7 新特性
 *
 * @author Benji
 * @date 2019-07-02
 */
public class Java7Test {

    /**
     * 二进制变量的表示
     */
    @Test
    public void binaryTest() {
        byte num1 = 0b00001001;  //1个字节8位
        short num2 = 0b0010000101000101; //2个字节16位
        int num3 = 0b10100001010001011010000101000101;//4个字节32位
        long num4 = 0b0010000101000101101000010100010110100001010001011010000101000101L;//8个字节64位

        System.out.println(num1); // 9
        System.out.println(num2); // 8517
        System.out.println(num3); // -1589272251
        System.out.println(num4); // 2397499697075167557
    }

    /**
     * 支持数字类型的下划线表示
     */
    @Test
    public void numberUnderlineTest() {
        int one_million = 1_000_000; // int 数学下划线
        int binary = 0b1001_1001; // int 二进制
        long long_num = 1_00_00_000; // long型
        float float_num = 2.10_001F; // float型
        double double_num = 2.10_12_001; // double型

        System.out.println(one_million); // 1000000
        System.out.println(binary); // 153
        System.out.println(long_num); // 10000000
        System.out.println(float_num); // 2.10001
        System.out.println(double_num); // 2.1012001
    }

    /**
     * Switch语句支持String类型
     */
    @Test
    public void switchStrTest() {
        String str = "Switch.Two";
        switch (str) {
            case "Switch.One":
                System.out.println("Switch.One");
                break;
            case "Switch.Two":
                System.out.println("Switch.Two");
                break;
            case "Switch.Three":
                System.out.println("Switch.Three");
                break;
            default:
                System.out.println("Switch.Other");
        }


    }

    /**
     * 泛型简化，菱形泛型
     */
    @Test
    public void collectionsTest() {

        // 新特性之前：
//        List list = new ArrayList();
//        List<String> list2 = new ArrayList<String>();
//        List<Map<String, List<String>>> list3 =  new ArrayList<Map<String, List<String>>>();

        // 新特性：
        List list = new ArrayList();
        List<String> list2 = new ArrayList<>();
        List<Map<String, List<String>>> list3 = new ArrayList<>();

    }

    /**
     * Catch支持多个异常
     */
    @Test
    public void throwsTest() throws Exception {
        try {
            IOExceptionTest();
            SQLExceptionTest();
        } catch (IOException | SQLException ex) {
            throw ex;
        }
    }

    public void IOExceptionTest() throws IOException {
        throw new IOException("This is an IOException.");
    }

    public void SQLExceptionTest() throws SQLException {
        throw new SQLException("This is an SQLException.");
    }

    /**
     * Try-with-resource语句, 1.7版标准的异常处理代码
     */
    @Test
    public void autoClosableTest() {
        try (MyAutoClosable myAutoClosable = new MyAutoClosable()) {
            myAutoClosable.doIt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 执行
        // MyAutoClosable doing it!
        // MyAutoClosable closed!
    }

    public void readFile1() throws FileNotFoundException {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("d:/input.txt");
            br = new BufferedReader(fr);
            String s = "";
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readFile() throws FileNotFoundException {
        try (
                FileReader fr = new FileReader("d:/input.txt");
                BufferedReader br = new BufferedReader(fr)
        ) {
            String s = "";
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
