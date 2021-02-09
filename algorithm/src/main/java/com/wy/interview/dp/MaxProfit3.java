package com.wy.interview.dp;

/**
 * @author matthew_wu
 * @since 2021/3/5 6:41 下午
 */
public class MaxProfit3 {

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1,2,4,2,5,7,2,4,9,0}, 4));
    }

    private static int solution(int[] prices, int k) {
        int[][][] dp = new int[prices.length][k + 1][2];
        // 因为对于第0天来说，它所能够获取的最大利润只有两种，与交易次数毫无关系，所以我们有以下初始值：
        for (int i = 1; i <= k; i++) {
            dp[0][i][0] = 0;
            dp[0][i][1] = -prices[0];
        }
        for (int i = 1; i < prices.length; i++) {
            for(int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
            }
        }
        int max = 0;
        for (int i = 0; i <= k; i++) {
            max = Math.max(max, dp[prices.length - 1][i][0]);
        }
        return max;
    }
}
