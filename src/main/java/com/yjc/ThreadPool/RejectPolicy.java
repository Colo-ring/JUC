package com.yjc.ThreadPool;

@FunctionalInterface // 拒绝策略
public interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}
