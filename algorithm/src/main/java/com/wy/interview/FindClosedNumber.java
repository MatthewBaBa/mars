package com.wy.interview;

/**
 * @author matthew_wu
 * @since 2021/1/22 2:23 下午
 */
public class FindClosedNumber {

    public static void main(String[] args) {
        double[] arr = new double[]{1.2, 2.1, 2.8, 3.1, 3.14, 3.2, 5.1};
        System.out.println(solution(arr, 3.14));
    }

    private static double solution(double[] array, double target) {
        int len = array.length;
        if (target < array[0]) {
            return array[0];
        }
        if (target > array[len - 1]) {
            return array[len - 1];
        }
        int lowPos = 0, highPos = len - 1, mid = 0;
        while (lowPos < highPos) {
            mid = (lowPos + highPos) / 2;
            if (array[mid] == target) {
                return array[mid];
            }
            if (array[mid] > target) {
                if (mid > 0 && target >= array[mid - 1]) {
                    return getClosest(array[mid - 1], array[mid], target);
                }
                highPos = mid - 1;
            }
            if (mid < len - 1 && target <= array[mid + 1]) {
                return getClosest(array[mid], array[mid + 1], target);
            }
            lowPos = mid + 1;
        }
        return -1;
    }

    private static double getClosest(double val1, double val2,
                                     double target) {
        if (target - val1 >= val2 - target) {
            return val2;
        }
        return val1;
    }



}
