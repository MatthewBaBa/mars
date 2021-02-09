package com.wy.interview.dp;

import java.util.Arrays;

/**
 * LeetCode 518. 零钱兑换 II
 * 完全背包
 * @author matthew_wu
 * @since 2021/2/27 3:11 下午
 */
public class CoinChange2 {

    public static void main(String[] args) {
        System.out.println(solution2(5, new int[]{1, 2, 5}));
    }

    //组合数
    private static int solution(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for(int i = 0; i < coins.length; i++) {
            for(int j = coins[i]; j < dp.length; j++) {
                dp[j] += dp[j - coins[i]];
                System.out.println(Arrays.toString(dp));
            }
        }
        return dp[amount];
    }

    //排列数
    private static int solution2(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for(int j = 0; j < dp.length; j++) {
            for(int i = 0; i < coins.length; i++) {
                if(j - coins[i] >= 0) {
                    dp[j] += dp[j - coins[i]];
                    System.out.println(j + "   " + coins[i]);
                    System.out.println(Arrays.toString(dp));
                }
            }
        }
        return dp[amount];
    }

}
