package com.yjc.Solutions;

/**
 * @author IntelliYJC
 * @create 2022/8/10 15:17
 * 面试题：交替打印 abc —————— synchronized 方法
 */
public class AlternateOutput_sync {

    private int flag;

    private int loopNum;

    public AlternateOutput_sync(int flag, int loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNum; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        AlternateOutput_sync alternateOutput = new AlternateOutput_sync(1, 5);
        new Thread(() -> {
            alternateOutput.print("a", 1, 2);
        }).start();
        new Thread(() -> {
            alternateOutput.print("b", 2, 3);
        }).start();new Thread(() -> {
            alternateOutput.print("c", 3, 1);
        }).start();

    }
}
