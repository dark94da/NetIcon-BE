package com.neticon.neticon.tools;

public class DBUtils {
    public static <T> T completeInsert(T obj) {
        if (obj == null) {
            return null;
        }
        Class clazz = obj.getClass();
        try {
            long ts = System.currentTimeMillis() / 1000;
            clazz.getMethod("setCreatedAt", Long.class).invoke(obj, ts);
            clazz.getMethod("setUpdatedAt", Long.class).invoke(obj, ts);
        } catch (Exception e) {
            return null;
        }
        return obj;
    }

    public static <T> T completeUpdate(T obj) {
        if (obj == null) {
            return null;
        }
        Class clazz = obj.getClass();
        try {
            long ts = System.currentTimeMillis() / 1000;
            clazz.getMethod("setUpdatedAt", Long.class).invoke(obj, ts);
        } catch (Exception e) {
            return null;
        }
        return obj;
    }
}
