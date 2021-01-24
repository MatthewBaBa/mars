package com.wy.interview;

/**
 * 线程的使用
 * 给定一个字符串，比如“12A34B56C78D910E1112F1314G1516H1718I1920J2122K”
 * 要求：两个线程交替打印， 一个打印数字，一个打印字符。
 * @author matthew_wu
 * @since 2020/12/7 5:47 下午
 */
public class ParallelWriting {

    public static void main(String[] args) {
        int length = 21;
        Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                for (int i = 0; i < length; i+=2) {
                    System.out.print(i+1);
                    System.out.print(i+2);
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // ASCII在 65-90 之间是大写, 97-122 是小写
        new Thread(() -> {
                synchronized (lock) {
                    for (int i = 65; i < 65 + length; i++) {
                        System.out.print( (char) i);
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            }
        }).start();
    }

}
