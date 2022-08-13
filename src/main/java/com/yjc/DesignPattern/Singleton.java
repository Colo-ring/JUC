package com.yjc.DesignPattern;

import java.io.Serializable;

/**
 * @author IntelliYJC
 * @create 2022/8/11 15:16
 * 最安全的双重校验 —— 懒汉单例
 */
public final class Singleton {
    private Singleton() {}
    private static volatile Singleton INSTANCE = null;
    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }


    // 比较推荐的内部类实现方式：
    /*private Singleton() {}
    private static class LazyHolder {
        static final Singleton INSTANCE = new Singleton();
    }
    public static Singleton getInstance() {
        return LazyHolder.INSTANCE;
    }
    */
}
