package com.fqh;

import java.util.stream.IntStream;

public class LambdaTest {

    // invokedynamic

    public static void main(String[] args) {
        int num = 3;
//        num = 5;
        int sum = IntStream.of(1, 2, 3)
                .map(i -> i * 2)                // 行内
                .map(i -> i * num)              // 行内
                .map(LambdaTest::add)           // 方法引用
                .sum();
    }

    public static int add(int num) { return num + 3; }
}
