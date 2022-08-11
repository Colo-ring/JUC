import com.yjc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author IntelliYJC
 * @create 2022/8/10 14:46
 */

@Slf4j(topic = "c.TestCondition")
public class TestCondition {
    private static final ReentrantLock ROOM = new ReentrantLock();
    private static boolean hasCigarette = false;
    private static boolean hasTakeout = false;
    // 等待烟的休息室
    private static final Condition waitCigaretteSet = ROOM.newCondition();
    // 等待外卖的休息室
    private static final Condition waitTakeoutSet = ROOM.newCondition();

    public static void main(String[] args) {
        new Thread(() -> {
            ROOM.lock();

            try {
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) { //while防止虚假唤醒
                    log.debug("没烟，先歇会！");
                    try {
                        waitCigaretteSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("可以开始干活了");
            } finally {
                ROOM.unlock();
            }

        }, "小南").start();

        new Thread(() -> {
            ROOM.lock();
            try {
                log.debug("外卖送到没？[{}]", hasTakeout);
                if (!hasTakeout) {
                    log.debug("没外卖，先歇会！");
                    try {
                        waitTakeoutSet.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("可以开始干活了");
            } finally {
                ROOM.unlock();
            }
        }, "小女").start();

        Sleeper.sleep(1000);
        new Thread(() -> {
            ROOM.lock();
            try {
                hasTakeout = true;
                log.debug("外卖到了噢！");
                waitTakeoutSet.signal();
            } finally {
                ROOM.unlock();
            }
        }, "送外卖的").start();

        Sleeper.sleep(1000);
        new Thread(() -> {
            ROOM.lock();
            try {
                hasCigarette = true;
                log.debug("外卖到了噢！");
                waitCigaretteSet.signal();
            } finally {
                ROOM.unlock();
            }
        }, "送烟的").start();
    }
}
