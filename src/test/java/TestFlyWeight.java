import com.yjc.DesignPattern.flayWeight.DataBasePool;
import com.yjc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

/**
 * @author IntelliYJC
 * @create 2022/8/13 22:09
 */

@Slf4j(topic = "c.TestFlyWeight")
public class TestFlyWeight {
    public static void main(String[] args) {
        DataBasePool pool = new DataBasePool(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Connection conn = pool.borrow();
                log.debug("使用连接: {}", conn);
                Sleeper.sleep(1000);
                pool.free(conn);
            }).start();
        }
    }
}
