package com.wy.interview.dp;

/**
 * LeetCode 121.买卖股票的最佳时机
 * @author matthew_wu
 * @since 2021/3/5 5:14 下午
 */
public class MaxProfit {

    public static void main(String[] args) {
        System.out.println(solution(new int[]{7,1,5,3,6,4}));
    }

    private static int solution(int[] prices) {
        int[][] dp = new int[prices.length][2];

        // dp[i][0] 下标为 i 这天结束的时候，不持股，手上拥有的现金数
        // dp[i][1] 下标为 i 这天结束的时候，持股，手上拥有的现金数

        // 初始化：不持股显然为 0，持股就需要减去第 1 天（下标为 0）的股价
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < prices.length; i++) {
            //昨天持股，今天卖出股票（现金数增加），
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            //昨天不持股，今天买入股票
            dp[i][1] = Math.max(dp[i - 1][1],  -prices[i]);
        }
        return dp[prices.length - 1][0];
    }
}
/*
1. 定义i k s
 i  第i天
 k  交易数
 s 状态 0代表不持有股票，1代表持有股票
 for 0 <= i < n:
    for 1 <= k <= K:
        for s in {0, 1}:
            dp[i][k][s] = max(buy, sell, rest)
2. 状态转移方程
 dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + price[i])
 dp[i][k][1] = Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - price[i])
3. 初始状态
 dp[i][k][0] = 0;
 dp[i][k][1] = -infinity;
 dp[i][0][0] = 0;
 dp[i][0][1] = -infinity; 不允许操作是不可能有股票的
==============
if k = 1;
dp[i][1][0] = Math.max(dp[i-1][1][0], dp[i-1][1][1] + price[i]);
dp[i][1][1] = Math.max(dp[i-1][1][1], dp[i-1][0][0] - price[i]);
            = Math.max(dp[i-1][1][1], -prices[i])
解释：k = 0 的 base case，所以 dp[i-1][0][0] = 0。
现在发现 k 都是 1，不会改变，即 k 对状态转移已经没有影响了。
可以进行进一步化简去掉所有 k：
dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
dp[i][1] = max(dp[i-1][1], -prices[i])

第二题，k = +infinity
如果 k 为正无穷，那么就可以认为 k 和 k - 1 是一样的。可以这样改写框架：
dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
            = max(dp[i-1][k][1], dp[i-1][k][0] - prices[i])
我们发现数组中的 k 已经不会改变了，也就是说不需要记录 k 这个状态了：
dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
dp[i][1] = max(dp[i-1][1], dp[i-1][0] - prices[i])



*/