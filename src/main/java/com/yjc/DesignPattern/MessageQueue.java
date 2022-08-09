package com.yjc.DesignPattern;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author IntelliYJC
 * @create 2022/8/9 20:51
 */
@Slf4j(topic = "c.MessageQueue")
public class MessageQueue {

    // 消息队列集合
    private final Deque<Message> queue = new ArrayDeque<>();
    // 队列容量
    private final int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take() {
        synchronized (queue) {
            // 检查队列是否为空
            while (queue.isEmpty()) {
                try {
                    log.debug(Thread.currentThread().getName() + ":队列为空，消费者线程等待");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 从消息队列头返回Message
            Message message = queue.removeFirst();
            log.debug(Thread.currentThread().getName() + "：已消费消息--" + message);
            queue.notifyAll();
            return message;
        }
    }

    // 存入消息
    public void put(Message message) {
        synchronized (queue) {
            // 检查队列是否满
            while (queue.size() == capacity) {
                try {
                    log.debug(Thread.currentThread().getName() + ":队列已满，生产者线程等待");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(message);
            log.debug(Thread.currentThread().getName() + "：已生产消息--" + message);
            queue.notifyAll();
        }
    }
}