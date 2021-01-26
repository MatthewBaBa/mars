package com.wy.interview.dp;

/**
 * 给你 k 种面值的硬币，面值分别为 c1, c2 ... ck，每种硬币的数量无限，
 * 再给一个总金额 amount，问你最少需要几枚硬币凑出这个金额，
 * 如果不可能凑出，算法返回 -1
 * @author matthew_wu
 * @since 2020/6/27 23:16
 */
public class CoinChange {

    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{1,2,5}, 14));
    }

    private static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        // base case
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = amount + 1;
            for (int coin: coins) {
                // 子问题无解，跳过
                if (i - coin < 0) {
                    continue;
                }
                dp[i] = Math.min(dp[i], dp[i-coin] + 1);
            }
        }
        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }
}
