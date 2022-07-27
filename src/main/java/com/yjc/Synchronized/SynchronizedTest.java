package com.yjc.Synchronized;

import lombok.extern.slf4j.Slf4j;

/**
 * @author IntelliYJC
 * @create 2022/7/27 15:59
 * 面向对象的 synchronized 同步代码块演示
 */

@Slf4j(topic = "c.SynchronizedTest")
public class SynchronizedTest {
    public static void main(String[] args) throws InterruptedException {
        Room room = new Room();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                room.increment();
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                room.decrement();
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", room.getCounter());
    }
}


class Room {
    private int counter;

    public void increment() {
        synchronized (this) {
            counter++;
        }
    }

    public void decrement() {
        synchronized (this) {
            counter--;
        }
    }

    public int getCounter() {
        synchronized (this) {
            return counter;
        }
    }
}