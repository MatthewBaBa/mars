package com.wy.interview.math;

import java.util.Arrays;

/**
 * @author matthew_wu
 * @since 2021/2/23 2:35 下午
 */
public class FindErrorNums {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{1,2,2,4})));
    }

    private static int[] solution(int[] nums) {
        int dup = 0;
        int missing = 0;
        for(int i=0; i < nums.length; i++) {
            int idx = Math.abs(nums[i]);
            if(nums[idx-1] < 0) {
                dup = idx;
                continue;
            }
            nums[idx-1] *= -1;
        }
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] > 0) {
                missing = i+1;
                break;
            }
        }
        return new int[]{dup, missing};
    }
}
