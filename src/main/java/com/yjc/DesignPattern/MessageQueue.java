/*
package com.yjc.DesignPattern;

import java.util.ArrayDeque;
import java.util.Deque;

*/
/**
 * @author IntelliYJC
 * @create 2022/8/9 20:51
 *//*

public class MessageQueue {

    // 消息队列集合
    private Deque<Message> queue = new ArrayDeque<>();
    // 队列容量
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    public Message take() {
        // 检查队列是否为空
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
        }
        return null;
    }

    // 存入消息
    public void put(Message message) {

    }
}

final class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
*/
