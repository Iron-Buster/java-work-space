package com.fqh.thread_local;

import java.lang.reflect.Field;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/3/6 14:46
 **/
public class TestThreadLocalKeyRef {

    // Test ThreadLocal wake ref

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> test("abc",false));
        t.start();
        t.join();
        System.out.println("--after gc--");
        Thread t2 = new Thread(() -> test("def", true));
        t2.start();
        t2.join();
    }

    private static void test(String s, boolean isGC)  {
        try {
//            new ThreadLocal<>().set(s); // new的一个匿名对象，没有任何引用执行threadLocal，在GC后，里面的key会被回收
            ThreadLocal<Object> threadLocal = new ThreadLocal<>();
            threadLocal.set(s);
            if (isGC) {
                System.gc();
            }
            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object ThreadLocalMap = field.get(t);
            Class<?> tlmClass = ThreadLocalMap.getClass();
            Field tableField = tlmClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] arr = (Object[]) tableField.get(ThreadLocalMap);
            for (Object o : arr) {
                if (o != null) {
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.println(String.format("弱引用key:%s,值:%s", referenceField.get(o), valueField.get(o)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
