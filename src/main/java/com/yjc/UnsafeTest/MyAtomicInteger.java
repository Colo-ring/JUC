package com.yjc.UnsafeTest;

import com.yjc.util.UnsafeAccessor;
import sun.misc.Unsafe;

/**
 * 自定义原子整数
 */
public class MyAtomicInteger {
    static final Unsafe UNSAFE;

    private volatile int value;
    private static final long valueOffset;

    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        // 计算 value 域的偏移量
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    public void decrement(int amount) {
        while (true) {
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }

    }
}
