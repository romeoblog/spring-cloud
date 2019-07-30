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
package com.cloud.example.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Vector;

/**
 * 多线程测试
 *
 * @author Benji
 * @date 2019-07-26
 */
public class ThreadTest {

    private final Logger log = LoggerFactory.getLogger(ThreadTest.class);

    @Test
    public void vectorTest() {
        final Vector<String> vector = new Vector<>();

        log.info("vector test ...");

        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add("项：" + i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        log.info(vector.get(i));
                    }
                }
            });

            removeThread.start();
            printThread.start();

            if (Thread.activeCount() >= 20) {
                return;
            }
        }
    }

    @Test
    public void vectorSynchronizedTest() {
        final Vector<String> vector = new Vector<>();

        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add("项：" + i);
            }

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (vector) {
                        for (int i = 0; i < vector.size(); i++) {
                            vector.remove(i);
                        }
                    }
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (vector) {
                        for (int i = 0; i < vector.size(); i++) {
                            log.info(vector.get(i));
                        }
                    }
                }
            });

            removeThread.start();
            printThread.start();

            if (Thread.activeCount() >= 10) {
                return;
            }
        }
    }

    private volatile int start = 0;

    @Test
    public void volatileKeyword() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    start++;
                }
            }
        };

        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        log.info("start = " + start);
    }

    @Test
    public void volatileKeyword1() {
        Integer s1 = -128;
        Integer s2 = -128;
        Long s5 = -128L;
        Integer s3 = new Integer(-128);
        Integer s4 = new Integer(-128);

        System.out.println(Objects.equals(s1,s5.intValue()));
        System.out.println(s3 == s4);
    }

}
