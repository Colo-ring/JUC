package com.yjc.Solutions;

import java.io.Reader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author IntelliYJC
 * @create 2022/8/10 15:17
 * 面试题：交替打印 abc —————— ReentrantLock 方法
 */
public class AlternateOutput_ReenL extends ReentrantLock {
    private int loopNum;

    public AlternateOutput_ReenL(int loopNum) {
        this.loopNum = loopNum;
    }

    /**
     * @param str 打印内容
     * @param current 进入哪一间休息室
     * @param next 下一间休息室
     */
    public void print(String str, Condition current, Condition next) {
        for (int i = 0; i < loopNum; i++) {
            lock();
            try {
                current.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }

    public static void main(String[] args) {
        AlternateOutput_ReenL awaitSignal = new AlternateOutput_ReenL( 5);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();

        new Thread(() -> {
            awaitSignal.print("a", a, b);
        }).start();
        new Thread(() -> {
            awaitSignal.print("b", b, c);
        }).start();
        new Thread(() -> {
            awaitSignal.print("c", c, a);
        }).start();

        awaitSignal.lock();
        try {
            a.signal();
        } finally {
            awaitSignal.unlock();
        }
    }
}
