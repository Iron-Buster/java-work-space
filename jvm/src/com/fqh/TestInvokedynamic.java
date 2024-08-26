package com.fqh;

import java.lang.invoke.MethodHandle;
import java.util.concurrent.ThreadLocalRandom;

public class TestInvokedynamic {

    public void testMethod1() {
        System.out.println("testMethod1");
    }

    public void testMethod2() {
        System.out.println("testMethod2");
    }

//    public static MethodHandle dynamicMethodCallBack() {
//        if (ThreadLocalRandom.current().nextBoolean()) {
//            return new MethodHandle("testMethod1");
//        } else {
//            return new MethodHandle("testMethod2");
//        }
//    }
}


