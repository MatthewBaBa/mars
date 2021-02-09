package com.wy.interview.math;

/**
 * 滑动窗口(双指针)
 * @author matthew_wu
 * @since 2021/2/26 10:52 上午
 */
public class MinSubArrayLen {

    public static void main(String[] args) {
        System.out.println(solution(7, new int[]{2,3,1,2,4,3}));
    }

    private static int solution(int target, int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int start = 0, end = 0;
        int sum = 0, min = Integer.MAX_VALUE;
        while (end < n) {
            sum += nums[end];
            while (sum >= target) {
                min = Math.min(min, end - start + 1);
                sum -= nums[start];
                start++;
            }
            end++;
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

}
