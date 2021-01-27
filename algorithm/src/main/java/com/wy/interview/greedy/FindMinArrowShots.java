package com.wy.interview.greedy;

import java.util.Arrays;

/**
 * @author matthew_wu
 * @since 2021/2/3 7:49 下午
 */
public class FindMinArrowShots {

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{-2147483646,-2147483645},{2147483646,2147483647}}));
    }

    private static int solution(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        //用 a[1] > b[1] 而不是 a[1] - b[1]， 是为了防止越界
        Arrays.sort(points, (a, b) -> a[1] > b[1] ? 1 : -1);
        int count = 1;
        int end = points[0][1];
        for (int[] point: points) {
            int start = point[0];
            if (start > end) {
                count++;
                end = point[1];
            }
        }
        return count;
    }
}
