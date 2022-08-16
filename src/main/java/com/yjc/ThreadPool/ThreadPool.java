package com.yjc.ThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author IntelliYJC
 * @create 2022/8/16 14:51
 * 自定义 ThreadPool
 */

@Slf4j(topic = "c.ThreadPool")
public class ThreadPool {
    // 任务队列
    private final BlockingQueue<Runnable> taskQueue;

    // 线程集合
    private final Set<Worker> workers = new HashSet<>();

    // 拒绝策略接口
    private RejectPolicy<Runnable> rejectPolicy;

    private final int coreSize;

    // 获取任务的超时时间
    private final long timeout;

    private final TimeUnit timeUnit;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit,
                      int queueCapacity, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    // 执行任务
    public void execute(Runnable task) {
        synchronized (workers) {
            // 当任务数没有超过coreSize 时， 直接交给worker执行
            // 如果任务数超过coreSize 时，加入任务队列暂存
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                log.debug("新增 worker {},{}", worker, task);
                workers.add(worker);
                worker.start();
            } else {
                // 拒绝策略
                taskQueue.tryPut(rejectPolicy, task);
                // taskQueue.put(task); // 死等
            }
        }
    }

    // 线程包装
    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 执行任务
            // 1. 当 task 不为空，执行任务
            // 2. task执行完毕，接着从任务队列获取任务并执行
            // take() 死等， poll() 超时退出
            while (task != null || (task = taskQueue.poll(timeout, timeUnit)) != null) {
                try {
                    log.debug("正在执行...{}", task);
                    task.run();
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers) {
                log.debug("worker {} 被移除", this);
                workers.remove(this);
            }
        }
    }
}
