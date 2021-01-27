package com.wy.interview.greedy;

/**
 * @author matthew_wu
 * @since 2021/2/4 8:16 下午
 */
public class Jump {

    public static void main(String[] args) {

    }

    private static int solution(int[] nums) {
        int steps = 0;
        int furthest = 0;
        int end = 0;
        for(int i = 0; i < nums.length - 1; i++) {
            furthest = Math.max(furthest, i + nums[i]);
            if (i == end) {
                end = furthest;
                steps++;
            }
        }
        return steps;
    }
}
