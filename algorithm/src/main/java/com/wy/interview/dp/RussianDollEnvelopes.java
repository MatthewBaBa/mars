package com.wy.interview.dp;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * LeetCode354 俄罗斯套娃信封问题
 * @author matthew_wu
 * @since 2021/1/27 4:02 下午
 */
public class RussianDollEnvelopes {

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{5,4},{6,4},{6,7},{2,3}}));
    }

    private static int solution(int[][] envelopes) {
        Arrays.sort(envelopes, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int[] dp = new int[envelopes.length];
        Arrays.fill(dp, 1);
        int max = 0;
        for (int i = 0; i < envelopes.length; i++) {
            for (int j = 0; j < i; j++) {
                if (envelopes[i][1] > envelopes[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j]+1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}
