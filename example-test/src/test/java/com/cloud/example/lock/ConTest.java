package com.cloud.example.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition Demo Test
 * <p>
 * Condition#await() with Object#wait()；
 * <p>
 * Condition#signal() with Object#notify()；
 *
 * @author Benji
 * @date 2019-08-06
 */
public class ConTest {

    final Lock lock = new ReentrantLock();
    final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        ConTest test = new ConTest();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();

        consumer.start();
        producer.start();
    }

    class Consumer extends Thread {

        @Override
        public void run() {
            consume();
        }

        private void consume() {

            try {
                lock.lock();
                System.out.println("Waiting for a lock signal: " + this.currentThread().getName());
                condition.await();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Get a lock signal: " + this.currentThread().getName());
                lock.unlock();
            }

        }
    }

    class Producer extends Thread {

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            try {
                lock.lock();
                System.out.println("Get a lock: " + this.currentThread().getName());
                condition.signalAll();

                System.out.println("Send a lock signal: " + this.currentThread().getName());
            } finally {
                lock.unlock();
            }
        }
    }

} 