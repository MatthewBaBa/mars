package com.wy.search;

/**
 * 注意容易出错的 3 个地方。
 * 1. 循环退出条件是 low <= high，而不是 low < high；
 * 2. mid 的取值，可以是 mid = (low + high) / 2，但是如果 low 和 high 比较大的话，low + high 可能会溢出，所以这里写为 mid = low + ((high - low) >> 1)；
 * 3.low 和 high 的更新分别为 low = mid + 1、high = mid - 1。
 *
 * @author matthew_wu
 * @since 2021/2/25 3:17 下午
 */
public class BinarySearch {

    public static int search(int[] nums, int low, int high, int val) {
        while (low <= high) {
            int mid = low + ((high -low) >> 1);
            if (nums[mid] == val) {
                return mid;
            } else if (nums[mid] < val) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找(非递归)
     *
     * @param nums 有序数组
     * @param val 要查找的值
     * @return 要查找的值在数组中的索引位置
     */
    private static int search(int[] nums, int val) {
        return search(nums, 0, nums.length - 1, val);
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 5, 7, 8, 9};

        // 非递归查找
        int r1 = search(nums, 7);
        System.out.println(r1);
    }
}
