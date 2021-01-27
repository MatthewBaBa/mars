package com.wy.interview.dp;

import java.util.Arrays;

/**
 * @author matthew_wu
 * @since 2020-06-30 18:08
 */
public class DropEggs {

    public static void main(String[] args) {
        System.out.println(solution100(100));
    }

    private static int solution(int k, int n) {
        int[][] dp = new int[k+1][n+1];

        //base case
        for (int i = 0; i <= n; i++) {
            dp[0][i] = 0;
            dp[1][i] = i;
        }
        for (int j = 0; j <= k; j++) {
            dp[j][0] = 0;
            dp[j][1] = 1;
        }

        for (int egg = 2; egg <= k; egg++) {
            for (int floor = 2; floor<= n; floor++) {
                int result = Integer.MAX_VALUE;
                //从第drop层扔鸡蛋
                for (int drop = 1; drop <= floor; drop++) {
                    //碎了，剩下的问题即如何在drop-1层，用egg-1个鸡蛋寻找最优解
                    int broken = dp[egg-1][drop-1];
                    //没碎，在floors-drop层，用egg个鸡蛋找最优解
                    int unbroken = dp[egg][floor - drop];
                    //两种情况取最大值，因为我根本不知道鸡蛋会不会碎
                    int conditon = Math.max(broken, unbroken) + 1;
                    //不断和上一次结果做比较，得到最少的扔次数
                    result = Math.min(result, conditon);
                }
                dp[egg][floor]=result;
            }
        }
        return dp[k][n];
    }

    /**
     * 最常规的解法是动态规划。可以用动态规划法求解的问题必须满足这样的条件，
     * 分割成的子问题是最优解的时候，原问题也是最优解。显然这个问题满足这一点：
     * 假设扔的总次数为f(x)，在第一次扔碎了的情况下，接下去只能一层一层试验，
     * 最多从第1层到第x-1层需要试验x-1次，加上扔第一个鸡蛋那一次，
     * 总的次数就是(x-1)+1=x；在第一次没碎的情况下，就相当于一个新的相似子问题：
     * 在N-x层中寻找新的扔鸡蛋次数f(N-x)，因此总次数就是f(N-x)+1。那么：
     * f(x)=max(x, f(N-x)+1)
     * ----------------------
     * f[n] = min(1 + max(i - 1, f[n - i])) i = 1……n
     **/
    private static int solution100(int n) {
        int[] dp = new int[n+1];
        //base case
        dp[1] = 1;
        for (int floor = 2; floor<= n; floor++) {
            dp[floor] = floor;
            for (int drop = 1; drop <= floor; drop++) {
                //碎
                int broken = drop-1;
                //不碎
                int unbroken = dp[floor-drop];
                int condition = Math.max(broken, unbroken) + 1;
                dp[floor] = Math.min(dp[floor], condition);
            }
        }
        System.out.println(Arrays.toString(dp));
        return dp[n];
    }
}

