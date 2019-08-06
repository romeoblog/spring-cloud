package com.cloud.example.lock;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition Demo Test2
 * <p>
 * Condition#await() with Object#wait()；
 * <p>
 * Condition#signal() with Object#notify()；
 *
 * @author Benji
 * @date 2019-08-06
 */
public class ConTest2 {
    private int queueSize = 10;

    // Priority Queue
    private PriorityQueue<Integer> queue = new PriorityQueue<>(queueSize);
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ConTest2 test = new ConTest2();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();
        producer.start();
        consumer.start();
        Thread.sleep(0);
        producer.interrupt();
        consumer.interrupt();
    }

    class Consumer extends Thread {
        @Override
        public void run() {
            consume();
        }

        volatile boolean flag = true;

        private void consume() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.size() == 0) {
                        try {
                            System.out.println("Queue is Empty, Waiting write data.");
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            flag = false;
                        }
                    }
                    queue.poll();                //每次移走队首元素
                    notFull.signal();
                    System.out.println("Remove an element from the queue，and the remaining elements: " + queue.size());
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    class Producer extends Thread {
        @Override
        public void run() {
            produce();
        }

        volatile boolean flag = true;

        private void produce() {
            while (flag) {
                lock.lock();
                try {
                    while (queue.size() == queueSize) {
                        try {
                            System.out.println("From the queue full, wait for free space");
                            notFull.await();
                        } catch (InterruptedException e) {

                            flag = false;
                        }
                    }
                    queue.offer(1);        //每次插入一个元素
                    notEmpty.signal();
                    System.out.println("Add an element to the queue，the remaining elements：" + (queueSize - queue.size()));
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}