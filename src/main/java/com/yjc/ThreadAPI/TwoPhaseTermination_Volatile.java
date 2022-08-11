package com.yjc.ThreadAPI;

import lombok.extern.slf4j.Slf4j;

/**
 * @author IntelliYJC
 * @create 2022/7/25 15:15
 * 两阶段终止模式
 */

@Slf4j(topic = "c.TwoPhaseTermination")
public class TwoPhaseTermination_Volatile {
    private Thread monitor;

    private volatile boolean stop = false;

    // 启动监控线程
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (stop) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                }
            }
        });
        monitor.start();
    }

    public void stop() {
        stop = true;
        monitor.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();
        Thread.sleep(3500);
        tpt.stop();
    }
}
