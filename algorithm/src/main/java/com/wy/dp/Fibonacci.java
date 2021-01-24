package com.wy.dp;

import java.util.Arrays;

/**
 * @author matthew_wu
 * @since 2020/6/27 22:46
 */
public class Fibonacci {

    public static void main(String[] args) {
        System.out.println(iteration(12));

    }

    private static int iteration(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return iteration(n-1) + iteration(n-2);
    }

    private static int recursion(int n) {
        int[] a = new int[n];
        a[0] = 1;
        a[1] = 1;
        for (int i = 2; i < n; i++) {
            a[i] = a[i-1] + a[i-2];
        }
        return a[n-1];
    }

}
