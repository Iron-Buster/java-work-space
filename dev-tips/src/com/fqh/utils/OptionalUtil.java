package com.fqh.utils;

import java.util.function.Supplier;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/20 23:26
 **/

/**
 *
 * Functional Interface以及我们需要处理的异常只有空指针异常，可以写工具类
 */
public class OptionalUtil {
    /**
     * 忽略NullPointerException的获取
     * @param supplier
     * @param <T>
     * @return 如果有空指针，返回null
     */
    public static <T> T orNull(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * 忽略NullPointerException的获取
     *
     * @param supplier
     * @param or
     * @param <T>
     * @return 如果有空指针，返回or
     */
    public static <T> T or(Supplier<T> supplier, T or) {
        try {
            T t = supplier.get();
            if (t != null) {
                return t;
            }
            return or;
        } catch (NullPointerException e) {
            return or;
        }
    }
}
