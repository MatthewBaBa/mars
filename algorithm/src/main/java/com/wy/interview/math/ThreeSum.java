package com.wy.interview.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 排序 + 双指针
 * 排序 + 双指针
 * 本题的难点在于如何去除重复解。
 * 算法流程：
 * 特判，对于数组长度n，如果数组为null或者数组长度小于3，返回[]。
 * 对数组进行排序。
 * 遍历排序后数组：
 * 若nums[i]>0：因为已经排序好，所以后面不可能有三个数加和等于0，直接返回结果。
 * 对于重复元素：跳过，避免出现重复解
 * 令左指针L=i+1，右指针R=n−1，当L<R 时，执行循环：
 * 当nums[i]+nums[L]+nums[R]==0，执行循环，判断左界和右界是否和下一位置重复，去除重复解。并同时将 L,R 移到下一位置，寻找新的解
 * 若和大于0，说明nums[R] 太大，R 左移
 * 若和小于0，说明nums[L] 太小，L 右移
 *
 * @author matthew_wu
 * @since 2021/2/24 4:30 下午
 */
public class ThreeSum {

    public static void main(String[] args) {
        System.out.println(solution(new int[]{0,0,0,0}));
    }

    private static List<List<Integer>> solution(int[] nums) {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                return res;
            }
            //i>0表示第0个元素不要判断
            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }
            int L=i+1, R=nums.length-1;
            while (L < R) {
                if (nums[i] + nums[L] + nums[R] == 0) {
                    res.add(Arrays.asList(nums[i], nums[L], nums[R]));
                    while (L < R && nums[L] == nums[L + 1]) {
                        L++;
                    }
                    while (L < R && nums[R] == nums[R - 1]) {
                        R--;
                    }
                    L++;
                    R--;
                } else if (nums[i] + nums[L] + nums[R] > 0) {
                    R--;
                } else {
                    L++;
                }
            }
        }
        return res;
    }
}
