package com.wy.interview;

/**
 * 实现一个单例类
 * 重点考察单例的理解及线程安全的理解，可考察其中一到两种写法：
 * - 非线程安全的懒汉模式
 * - 线程安全的懒汉模式
 * - 更高效的线程安全的懒汉模式：双校验
 * - 恶汉模式，线程安全及不安全写法
 * - 其他写法
 * @author matthew_wu
 * @since 2020/12/22 2:28 下午
 */
public class Singleton {
}


/**
 * 非线程安全的懒汉模式
 **/
class SingletonA {
    private static SingletonA instance;

    public static SingletonA getInstance() {
        if (instance == null) {
            instance = new SingletonA();
        }
        return instance;
    }
}

/**
 * 线程安全的懒汉模式
 **/
class SingletonA1 {
    private static SingletonA1 instance;

    public static synchronized SingletonA1 getInstance() {
        if (instance == null) {
            instance = new SingletonA1();
        }
        return instance;
    }
}

/**
 * 更高效的线程安全的懒汉模式：双校验
 **/
class SingletonA2 {
    private static SingletonA2 instance;

    public static SingletonA2 getInstance() {
        if (instance == null) {
            synchronized (SingletonA2.class) {
                if (instance == null) {
                    instance = new SingletonA2();
                }
            }
        }
        return instance;
    }
}

/**
 * 恶汉模式，线程不安全
 **/
class SingletonB {
    private static SingletonB instance = new SingletonB();

    private SingletonB() {}

    public static SingletonB getInstance() {
        return instance;
    }
}

/**
 * 内嵌静态类的恶汉模式，线程安全
 **/
class SingletonC {

    private static class SingletonHolder {
        private static SingletonC instance = new SingletonC();
    }

    public static SingletonC getInstance() {
        return SingletonHolder.instance;
    }

}

/**
 * 枚举写法，推荐的很赞的写法
 **/
enum SingletonD {
    INSTANCE;

    public void doSomething() {
        System.out.println("Hello singleton.");
    }
}