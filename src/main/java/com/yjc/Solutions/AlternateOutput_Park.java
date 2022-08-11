package com.yjc.Solutions;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author IntelliYJC
 * @create 2022/8/10 15:17
 * 面试题：交替打印 abc —————— Park-UnPark 方法
 */
public class AlternateOutput_Park extends ReentrantLock {
    private int loopNum;

    public AlternateOutput_Park(int loopNum) {
        this.loopNum = loopNum;
    }

    /**
     * @param str 打印内容
     * @param next 下一个线程
     */
    public void print(String str, Thread next) {
        for (int i = 0; i < loopNum; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }
    }

    static Thread t1, t2, t3;
    public static void main(String[] args) {
        AlternateOutput_Park pu = new AlternateOutput_Park(5);
        t1 = new Thread(() -> {
            pu.print("a", t2);
        });
        t2 = new Thread(() -> {
            pu.print("b", t3);
        });
        t3 = new Thread(() -> {
            pu.print("c", t1);
        });
        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }
}
