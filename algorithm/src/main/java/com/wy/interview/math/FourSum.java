package com.wy.interview.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author matthew_wu
 * @since 2021/2/25 10:54 上午
 */
public class FourSum {

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 0, -1, 0, -2, 2}, 0));
    }

    private static List<List<Integer>> solution(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return res;
        }
        Arrays.sort(nums);
        int length = nums.length;
        for (int i = 0; i < length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }
            if (nums[i] + nums[length - 3] + nums[length - 2] + nums[length - 1] < target) {
                continue;
            }
            for (int j = i+1; j < length - 2; j++) {
                //去除重复解
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                //剪枝
                if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }
                //剪枝
                if (nums[i] + nums[j] + nums[length - 2] + nums[length - 1] < target) {
                    continue;
                }
                int start = j + 1, end = length - 1;
                while (start < end) {
                    int sum = nums[i] + nums[j] + nums[start] + nums[end];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[start], nums[end]));
                        while (start < end && nums[start] ==  nums[start+1]) {
                            start++;
                        }
                        start++;
                        while (start < end && nums[end] == nums[end-1]) {
                            end--;
                        }
                        end--;
                    } else if (sum > target) {
                        end--;
                    } else {
                        start++;
                    }
                }
            }
        }
        return res;
    }
}
