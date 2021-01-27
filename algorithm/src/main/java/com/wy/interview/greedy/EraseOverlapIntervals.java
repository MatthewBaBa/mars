package com.wy.interview.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * LeetCode435 无重叠区间
 * @author matthew_wu
 * @since 2021/2/3 7:13 下午
 */
public class EraseOverlapIntervals {

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{1,2}, {2,3}, {3,4}, {1,3}}));
    }

    private static int solution(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        int count = 1;
        int end = intervals[0][1];
        for (int[] interval: intervals) {
            int start = interval[0];
            if (start >= end) {
                count++;
                end = interval[1];
            }
        }
        return intervals.length - count;
    }

}
