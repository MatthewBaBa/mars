package com.wy.interview.math;

import java.util.Arrays;

/**
 * @author matthew_wu
 * @since 2021/2/26 11:17 上午
 */
public class MergeTwoOrderedArray {

    public static void main(String[] args) {
        int[] a = new int[]{1,2,3,0,0,0};
        System.out.println(Arrays.toString(a));
        merge(a, 3, new int[]{2,5,6}, 3);
        System.out.println(Arrays.toString(a));
    }

    private static void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1, k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] >= nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }
}
