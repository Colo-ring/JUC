import com.yjc.DesignPattern.Message;
import com.yjc.DesignPattern.MessageQueue;
import com.yjc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author IntelliYJC
 * @create 2022/8/10 1:56
 */
@Slf4j(topic = "c.Test4")
public class Test4 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                int id = i;
                messageQueue.put(new Message(id, "值：" + id));
            }
        }, "producer").start();

        new Thread(() -> {
            while (true) {
                Sleeper.sleep(1000);
                Message message = messageQueue.take();
            }
        }, "consumer").start();
    }
}
