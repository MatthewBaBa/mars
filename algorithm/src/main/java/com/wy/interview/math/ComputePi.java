package com.wy.interview.math;

/**
 * Monte Carlo method
 * 这种方法是一种利用计算机随机数的功能基于“随机数”的算法，通过计算落在单位圆内的点与落在正方形内的
 * 点的比值求PI。
 * M/N = pi*R*R / 4R*R   => pi = 4 * M/N
 * @author matthew_wu
 * @since 2021/1/22 11:15 上午
 */
public class ComputePi {

    public static void main(String[] args) {
        System.out.println(solution(100000000));
    }

    private static double solution(int times) {
        int hitCnt = 0;
        double x, y, pi;
        for (int i = 0; i < times; i++) {
            x = Math.random();
            y = Math.random();
            if (x*x + y*y < 1) {
                hitCnt++;
            }
        }
        pi = 4.0 * hitCnt / times;
        return pi;
    }

}
