import com.yjc.DesignPattern.GuardedBoxes;
import com.yjc.DesignPattern.GuardedObject;
import com.yjc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author IntelliYJC
 * @create 2022/8/9 18:38
 */

@Slf4j(topic = "c.Test3")
public class Test3 {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        Sleeper.sleep(1000);
        for (Integer id : GuardedBoxes.getIds()) {
            new Postman(id, id + "号快递到了").start();
        }
    }
}

@Slf4j(topic = "c.People")
class People extends Thread{
    @Override
    public void run() {
        // 收信
        GuardedObject guardedObject = GuardedBoxes.createGuardedObject();
        log.debug("开始收信i d:{}", guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信id:{}，内容:{}", guardedObject.getId(),mail);
    }
}
@Slf4j(topic = "c.Postman")
class Postman extends Thread{
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObject guardedObject = GuardedBoxes.getGuardedObject(id);
        log.debug("开始送信i d:{}，内容:{}", guardedObject.getId(),mail);
        guardedObject.complete(mail);
    }
}