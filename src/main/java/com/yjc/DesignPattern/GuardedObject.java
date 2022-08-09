package com.yjc.DesignPattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @author IntelliYJC
 * @create 2022/8/8 16:30
 */
@Slf4j(topic = "c.GuardedObject")
public class GuardedObject {
    private int id;

    private Object response;

    public GuardedObject() {
    }

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // 获取结果
    public Object get(long timeout) {
        synchronized (this) {
            // 开始时间
            long begin = System.currentTimeMillis();
            // 经历时间
            long passedTime = 0;
            while (response == null) {
                // 循环等待时间，考虑了虚假唤醒
                long waitTime = timeout - passedTime;
                // 经历时间超过最大等待时间，退出循环
                if (waitTime <= 0) break;

                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }

    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}
