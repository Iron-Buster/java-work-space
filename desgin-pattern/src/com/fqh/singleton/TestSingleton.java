package com.fqh.singleton;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/21 18:34
 **/
public class TestSingleton {


    /** 单例饿汉式  */
    static class SingletonI {
        private static SingletonI instance = new SingletonI();
        private SingletonI() {}
        public static SingletonI getInstance() {
            return instance;
        }
    }

    /** 单例懒汉式  */
    static class SingletonII {
        private static SingletonII instance = null;
        private SingletonII() {}
        public static SingletonII getInstance() {
            if (instance == null) {
                instance = new SingletonII();
            }
            return instance;
        }
    }
}
