package com.fqh;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/21 11:20
 **/
public class TestMaxTenuringThreshold {

    // 对象头中的分代年龄，用于分代 GC
    // 记录分代年龄一共 4 bit，所以最大为 2^4 - 1 = 15。
    // 所以配置最大分代年龄-XX:MaxTenuringThreshold=n这个n不能大于15，当然也不能小于 0.等于 0 的话，就直接入老年代。
    // 等于 16 的话，就是从不进入老年代，这样不符合JVM规范，所以不能大于15（感谢 CSDN @JonsonJiao 指正）。默认是 15。
    static volatile Object consumer;
    public static void main(String[] args) {
        //这是我们要观察的对象
        Object instance = new Object();
        long lastAddr = VM.current().addressOf(instance);
        for (int i = 0; i < 10000; i++) {
            //查看地址是否发生了变化，代表是否发生了 Survivor 复制，或者是移动到老年代
            long currentAddr = VM.current().addressOf(instance);
            if (currentAddr != lastAddr) {
                //地址发生变化的时候，打印对象结构
                ClassLayout layout = ClassLayout.parseInstance(instance);
                System.out.println(layout.toPrintable());
                lastAddr = currentAddr;
            }
            for (int j = 0; j < 10000; j++) {
                //一直创建新对象
                //因为是volatile的属性更新，不会被编译器优化
                consumer = new Object();
            }
        }
    }
}
