import com.yjc.aqs.MyLock;
import com.yjc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author IntelliYJC
 * @create 2022/8/19 15:41
 */

@Slf4j(topic = "c.TestAQSMyLock")
public class TestAQSMyLock {
    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(() -> {
            lock.lock();
            try {
                log.debug("locking...");
                Sleeper.sleep(1000);
            } finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.debug("locking...");
            } finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        }, "t2").start();
    }
}
