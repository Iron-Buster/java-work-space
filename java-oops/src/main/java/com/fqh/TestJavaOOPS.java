package com.fqh;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/21 11:15
 **/
public class TestJavaOOPS {

    /**
     * Java对象指针
     *
     */

    public static void main(String[] args) {
        // Q: 对象的hashcode会计算几次? 去哪里找哈希值?
        // A: 计算1次，计算后的hashcode会被设置到对象头的markword里面
        A a = new A();
        B b = new B();
        System.out.println("------After Initialization------\n" + ClassLayout.parseInstance(a).toPrintable() + "\n" + ClassLayout.parseInstance(b).toPrintable());

        System.out.println("a.hashcode: " + a.hashCode());
        System.out.println("b.hashcode: " + b.hashCode());
        System.out.println("------After call hashcode------\n" + ClassLayout.parseInstance(a).toPrintable() + "\n" + ClassLayout.parseInstance(b).toPrintable());
    }

    //A没有覆盖默认的 hashcode 方法
    public static class A {
        long d;
    }
    //B覆盖了 hashcode 方法
    public static class B {
        long d;

        @Override
        public int hashCode() {
            return (int) 5555;
        }
    }
}
