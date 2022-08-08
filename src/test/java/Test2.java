import com.yjc.DesignPattern.GuardedObject;
import com.yjc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author IntelliYJC
 * @create 2022/7/12 16:35
 */

@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            log.debug("begin");
            Object response = guardedObject.get(2000);
            log.debug("结果是: {}", response);
        }, "t1").start();

        new Thread(()->{
            log.debug("begin");
            Sleeper.sleep(3000);
            guardedObject.complete(new Object());

        }, "t2").start();

        log.debug("finally");
    }
}
