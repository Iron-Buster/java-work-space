package com.fqh.thread_local;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/3/6 14:34
 **/
public class TestThreadLocal {

    // simply use
    private List<String> messages = new ArrayList<>();
    private static final ThreadLocal<TestThreadLocal> holder = ThreadLocal.withInitial(TestThreadLocal::new);

    public static void add(String message) {
        holder.get().messages.add(message);
    }

    public static List<String> clear() {
        List<String> messages = holder.get().messages;
        holder.remove();

        System.out.println("size: " + holder.get().messages.size());
        return messages;
    }

    public static void main(String[] args) {
        TestThreadLocal.add("hello world");
        System.out.println(holder.get().messages);
        TestThreadLocal.clear();
    }
}
