package com.yjc.ThreadAPI;

import com.yjc.util.Sleeper;

/**
 * @author IntelliYJC
 * @create 2022/7/24 20:59
 */
public class Main {
    private void threadSort(int[] arr) {
        for (final int a : arr) {
            new Thread(() -> {
                Sleeper.sleep(a);
                System.out.println(a);
            }).start();
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.threadSort(new int[] {1,34,54,31,0,-1,10,-100});
    }

}
