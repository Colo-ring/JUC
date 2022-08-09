package com.yjc.DesignPattern;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * @author IntelliYJC
 * @create 2022/8/9 18:11
 */
public class GuardedBoxes {
    private static Map<Integer, GuardedObject> boxes = new Hashtable<>();

    private static int id = 1;

    private static synchronized int generateId() {
        return id++;
    }

    public static GuardedObject getGuardedObject(int id) {
        return boxes.remove(id);
    }

    public static GuardedObject createGuardedObject() {
        GuardedObject go = new GuardedObject(generateId());
        boxes.put(go.getId(), go);
        return go;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

}
