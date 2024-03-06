package com.fqh.thread_local;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/3/6 14:59
 **/
public class TestInheritableThreadLocal {

    // InheritableThreadLocal 异步场景父子线程共享副本数据
    public static void main(String[] args) {
        ThreadLocal<String> ThreadLocal = new ThreadLocal<>();
        ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        ThreadLocal.set("父类数据:threadLocal");
        inheritableThreadLocal.set("父类数据:inheritableThreadLocal");

        new Thread(() -> {
            System.out.println("子线程获取父类ThreadLocal数据：" + ThreadLocal.get());
            System.out.println("子线程获取父类inheritableThreadLocal数据：" + inheritableThreadLocal.get());
        }).start();
    }
}
