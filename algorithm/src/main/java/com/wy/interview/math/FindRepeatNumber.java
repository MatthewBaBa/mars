package com.wy.interview.math;

/**
 * 剑指 Offer 03. 数组中重复的数字
 * @author matthew_wu
 * @since 2021/2/19 5:05 下午
 */
public class FindRepeatNumber {

    public static void main(String[] args) {
        System.out.println((solution(new int[]{2, 3, 1, 0, 4, 5, 3})));
    }

    private static int solution(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            nums[i] += 1;
        }
        int res = -1;
        for(int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]);
            if (nums[index-1] < 0) {
                res = index - 1;
                break;
            }
           nums[index-1] *= -1;
        }
        return res;
    }
}
