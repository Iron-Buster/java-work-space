package com.fqh.volatile_feature;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/21 18:22
 **/
public class TestVolatileFeatureI {

    /** volatile修饰的变量，可以保证该变量在多线程环境中的可见性 */
    static volatile boolean ok = true;

    public static void main(String[] args) {

        new Thread(() -> {
            while (ok) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // T1 update ok -> False
                ok = false;
            }
            System.out.println("T1 Finished....");
        }, "T1").start();

        new Thread(() -> {
            // T2 read from memory ok = False
            // T2 stop
            while (ok) {
                // cycle
            }
            System.out.println("ok was updated....");
            System.out.println("T2 Finished....");
        }, "B").start();
    }

}
