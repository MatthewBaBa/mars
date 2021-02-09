package com.wy.interview.dfs;

/**
 * LeetCode 695 岛屿的最大面积
 * @author matthew_wu
 * @since 2021/2/25 10:40 上午
 */
public class MaxAreaOfIsland {

    public static void main(String[] args) {

    }

    private static int solution(int[][] grid) {
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                max = Math.max(max, doSearch(grid, i, j));
            }
        }
        return max;
    }

    private static int doSearch(int[][] grid, int i, int j) {
        int size = 0;
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return size;
        }
        if (grid[i][j] == 0) {
            return size;
        }
        size++;
        grid[i][j] = -1;
        size += doSearch(grid, i+1, j);
        size += doSearch(grid, i, j+1);
        size += doSearch(grid, i-1, j);
        size += doSearch(grid, i, j-1);
        return size;
    }


}
