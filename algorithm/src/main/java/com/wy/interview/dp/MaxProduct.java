package com.wy.interview.dp;

/**
 * LeetCode152 乘积最大子数组
 * @author matthew_wu
 * @since 2021/1/27 11:41 上午
 */
public class MaxProduct {

    public static void main(String[] args) {
        System.out.println(solution(new int[]{-4,-3,-2}));
    }

    private static int solution(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int max = nums[0], min = nums[0], res = nums[0];
        for(int i = 1; i < nums.length; i++) {
            int maxTmp = max, minTmp = min;
            max = Math.max(nums[i] * maxTmp, Math.max(nums[i], nums[i] * minTmp));
            min = Math.min(nums[i] * minTmp, Math.min(nums[i], nums[i] * maxTmp));
            res = Math.max(res, max);
        }
        return res;
    }

}
