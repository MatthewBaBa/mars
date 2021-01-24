package com.wy.dp;

/**
 * LeetCode211
 * 在一个由 0 和 1 组成的二维矩阵内，找到只包含 1 的最大正方形，并返回其面积。
 * 解题思路
 * 当我们判断以某个点为正方形右下角时最大的正方形时，那它的上方，左方和左上方三个点也一定是某个正方形的右下角，
 * 否则该点为右下角的正方形最大就是它自己了。这是定性的判断，那具体的最大正方形边长呢？
 * 我们知道，该点为右下角的正方形的最大边长，最多比它的上方，左方和左上方为右下角的正方形的边长多1，
 * 最好的情况是是它的上方，左方和左上方为右下角的正方形的大小都一样的，这样加上该点就可以构成一个更大的正方形。
 * 但如果它的上方，左方和左上方为右下角的正方形的大小不一样，合起来就会缺了某个角落，
 * 这时候只能取那三个正方形中最小的正方形的边长加1了。假设dpi表示以i,j为右下角的正方形的最大边长，则有
 * dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1
 * 当然，如果这个点在原矩阵中本身就是0的话，那dpi肯定就是0了。
 * @author matthew_wu
 * @since 2021/1/22 1:53 下午
 */
public class MaxSquareIsland {

    public static void main(String[] args) {
        char[][] grid = {
                {'1','0','1','0','0'},
                {'1','0','1','1','1'},
                {'1','1','1','1','1'},
                {'1','0','0','1','0'}};
        System.out.println(solution(grid));
    }

    private static int solution(char[][] grid) {
        int max = 0, rows = grid.length, cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            dp[i][0] = grid[i][0] - '0';
            max = Math.max(max, dp[i][0]);
        }
        for (int i = 0; i < cols; i++) {
            dp[0][i] = grid[0][i] - '0';
            max = Math.max(max, dp[0][i]);
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                dp[i][j] = grid[i][j] == '1'? Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])) + 1: 0;
                max = Math.max(max, dp[i][j]);
            }
        }
        return max * max;
    }

}
