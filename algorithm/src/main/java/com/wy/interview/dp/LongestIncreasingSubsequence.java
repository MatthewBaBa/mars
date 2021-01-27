package com.wy.interview.dp;

import java.util.Arrays;

/**
 * @author matthew_wu
 * @since 2020/6/28 21:09
 */
public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        System.out.println(solution2(new int[]{10,9,2,5,3,7,101,18}));
    }

    // O(N2)
    private static int solution(int[] a) {
        if (a.length == 0) {
            return 0;
        }
        int[] dp = new int[a.length];
        int res = 0;
        for (int i = 0; i < a.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (a[i] > a[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public static int solution2(int[] a) {
        int[] dp = new int[a.length];
        int piles = 0;
        for (int i = 0; i < a.length; i++) {
            int poker = a[i];

            int left = 0, right = piles;
            //搜索左侧边界的二分查找
            while (left < right) {
                int mid = (left + right) / 2;
                if (dp[mid] > poker) {
                    right = mid;
                } else if (dp[mid] < poker) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            if (left == piles) {
                piles++;
            }
            dp[left] = poker;
        }
        System.out.println(Arrays.toString(dp));
        return piles;
    }
}
