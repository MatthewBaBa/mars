package com.wy.interview.dp;

import java.util.Arrays;

/**
 * LeetCode 416. 分割等和子集
 * @author matthew_wu
 * @since 2021/3/2 4:33 下午
 */
public class SplitEqualSumSubset {

    public static void main(String[] args) {
        solution(new int[]{1, 5, 11, 5});
    }

    /**
     * dp[i]表示 背包总容量是i，最大可以凑成i的子集总和为dp[i]。
     * 所以递推公式：dp[j] = max(dp[j], dp[j - nums[i]] + nums[i]);
     * dp[i]表示 :背包总容量是i，最大可以凑成i的子集总和为dp[i]。所以可以知道总和dp[i]一定是小于等于i的。我是这样理解的
     * 背包价值最大值一定是小于等于背包容量的，因为所有物品的单位价值都是1，所以当且仅当占用背包所有容量才能得到最大的价值——背包容量值。
     **/
    private static boolean solution(int[] nums) {
        int sum = 0;
        for(int i: nums) {
            sum += i;
        }
        if (sum % 2 == 1) {
            return false;
        }
        int target = sum/2;
        int[] dp = new int[15];
        // 开始0-1背包
        for(int i = 0; i < nums.length; i++) {
            // 每一个元素一定是不可重复放入，所以从大到小遍历
            for(int j = target; j >= nums[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
                System.out.println(i + "   " + j + "   " + Arrays.toString(dp));
            }
        }
        // 集合中的元素正好可以凑成总和target
        if (dp[target] == target) {
            return true;
        }
        return false;
    }
}
