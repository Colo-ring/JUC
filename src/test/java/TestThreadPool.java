import com.yjc.ThreadPool.BlockingQueue;
import com.yjc.ThreadPool.ThreadPool;
import com.yjc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author IntelliYJC
 * @create 2022/8/16 16:56
 */
@Slf4j(topic = "c.TestThreadPool")
public class TestThreadPool {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(1,
                1000, TimeUnit.MILLISECONDS, 1,
                // BlockingQueue::put // 1.死等
                // (queue, task) -> {queue.offer(task, 500, TimeUnit.MILLISECONDS);} // 2.带超时等待
                // (queue, task) -> {log.debug("什么都不写，放弃任务执行");} // 3.让调用者放弃任务执行
                // (queue, task) -> {throw new RuntimeException("任务执行失败" + task);} // 4.让调用者抛出异常
                (queue, task) -> {task.run();} // 4.让调用者自己执行任务
        );

        for (int i = 0; i < 3; i++) {
            int j = i;
            threadPool.execute(() -> {
                Sleeper.sleep(1000);
                log.debug("{}", j);
            });
        }
    }
}
