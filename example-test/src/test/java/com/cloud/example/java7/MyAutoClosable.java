package com.cloud.example.java7;

/**
 * 自定义AutoCloseable
 *
 * @author Benji
 * @date 2019-07-02
 */
public class MyAutoClosable implements AutoCloseable {

    public void doIt() {
        System.out.println("MyAutoClosable doing it!");
    }

    @Override
    public void close() throws Exception {
        System.out.println("MyAutoClosable closed!");
    }

}