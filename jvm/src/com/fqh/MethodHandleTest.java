package com.fqh;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * <li>invokedynamic 实现动态调用的原理</li>
 * <li>方法句柄 -> MethodHandle</li>
 * <li>拿到方法句柄后进行调用方法（JDK中方法不能作为参数传递，但是方法句柄可以）</li>
 */
public class MethodHandleTest {

    public void testMethod(String name) {
        System.out.println("testMethod: " + name);
    }


    public static void main(String[] args) throws Throwable {
        // 基于当前词法位置获取【解析/搜索器】，服从成员可见性限制
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 创建方法描述符，即名称无关的【方法签名】，方法出入参特征
        MethodType methodType = MethodType.methodType(void.class, String.class);
        // 在指定类中搜索指定名称、指定出入参特征的方法，得到方法句柄
        MethodHandle methodHandle = lookup.findVirtual(MethodHandleTest.class, "testMethod", methodType);
        // 调用方法，类型 java.lang.reflect.Method.invoke(Object[])
        methodHandle.invoke(new MethodHandleTest(), "test");
    }
}
