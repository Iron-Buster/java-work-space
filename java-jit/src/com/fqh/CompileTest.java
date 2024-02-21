package com.fqh;

import java.util.concurrent.TimeUnit;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/21 11:41
 **/
public class CompileTest {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            test1();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * VM参数：-XX:+PrintCompilation 打印编译日志
     */
    public static void test1() {
        long time1 = System.nanoTime();
        long count1 = 0;
        for (int i = 0; i < 10000; i++) {
            count1++;
        }
        //为了和编译日志区分，这里输出到error输出
        System.err.println(System.nanoTime() - time1 + "-----" + count1);
    }
}
