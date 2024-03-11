package com.fqh;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/3/11 15:01
 **/
public class TestDirectMemory {

    static int _1Gb = 1024 * 1024 * 1024;

    public static void main(String[] args) throws IOException {
        /**
         * 直接内存分配的底层原理：Unsafe对象
         */
        Unsafe unsafe = getUnsafe();
        long base = unsafe.allocateMemory(_1Gb);
        unsafe.setMemory(base, _1Gb, (byte) 0);
        System.in.read();

        // 释放内存
        unsafe.freeMemory(base);
        System.in.read();
    }

    /**
     * 通过反射获取Unsafe对象
     */
    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
            return unsafe;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
