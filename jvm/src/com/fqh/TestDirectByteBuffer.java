package com.fqh;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/3/11 15:04
 **/
public class TestDirectByteBuffer {

    static int _1Gb = 1024 * 1024 * 1024;
    public static void main(String[] args) throws IOException {
        // ①、分配1G内存
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_1Gb);
        System.out.println("分配完毕...");
        System.in.read();
        System.out.println("开始释放...");
        // ②、被虚引用引用的对象  的  强引用被释放掉
        byteBuffer = null;
        System.gc(); // 显式的垃圾回收，Full GC
        // ③、在这里阻塞住，通过内存分析工具分析当前JVM进程的内存消耗。观察上面分配的1G内存是否被回收
        System.in.read();
    }
}
