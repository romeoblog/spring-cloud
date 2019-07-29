package com.cloud.example.thread;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

}
