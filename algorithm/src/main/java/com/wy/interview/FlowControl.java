package com.wy.interview;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 接口限流实现
 * 有一个 API 网关，出于对 API 接口的保护，需要建立一个流控功能，根据 API 名称，每分钟最多只能请求指定的次数（如 1000 次），超过限制则这一分钟内返回错误，但下一分钟又可以正常请求。
 * @author matthew_wu
 * @since 2020/12/21 3:02 下午
 */
public class FlowControl {

    /**
     * 第一种使用 简单的区块大锁实现 固定自然时间窗口内的限流
     * 也可以使用Map分区 或者其他并发加锁方式
     **/
    public static class Invoker1 implements Invoke {

        /**
         * 限流次数
         **/
        private final int threshold;

        /**
         * 限流周期
         **/
        private final int periodSeconds;

        /**
         * 当前周期计数器
         **/
        private int counter = 0;

        /**
         * 周期标示
         **/
        private long periodMark = -1;

        /**
         * 大锁 确保上面两个普通变量的同步
         * 如果不用大锁的话  上面需要有其他同步方式
         */
        private Lock lock = new ReentrantLock();

        public Invoker1(int threshold, int periodSeconds) {
            this.threshold = threshold;
            this.periodSeconds = periodSeconds;
        }

        @Override
        public boolean invoke() {
            lock.lock();
            try {
                long period = System.currentTimeMillis() / periodSeconds * 1000;
                if (period != periodMark) {
                    period = periodMark;
                    counter = 0;
                }
                return ++counter <= threshold;
            } finally {
             lock.unlock();
            }
        }
    }

    /**
     * 第二种实现 类似令牌桶算法 加分项 任意时间窗口内的限流
     * 如果能额外考虑到缓冲桶加分
     **/
    public static class Invoker2 implements Invoke {
        /**
         * 限流次数
         */
        private final int threshold;
        /**
         * 限流周期
         */
        private final int periodSeconds;

        /**
         * 每一个令牌所需的纳秒数
         */
        private final int nanoPerTicket;

        /**
         * 上一次放过的时间
         */
        private long lastAccessNano;

        public Invoker2(int threshold, int periodSeconds) {
            this.threshold = threshold;
            this.periodSeconds = periodSeconds;
            this.nanoPerTicket = this.periodSeconds * 1000 * 1000 * 1000 / (this.threshold);
        }

        @Override
        public boolean invoke() {
            long now = System.nanoTime();
            /**
             * 也可以用其他并发控制方式
             */
            synchronized (this) {
                if (now >= lastAccessNano + nanoPerTicket) {
                    lastAccessNano = now;
                    return true;
                }
                return false;
            }
        }

    }
    
    /**
     * 接口定义
     * 对invoke方法进行调用，超过限制则return false
     * @param
     * @return 
     **/
    public interface Invoke {
        boolean invoke();
    }

}
