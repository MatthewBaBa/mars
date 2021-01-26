package com.wy.interview.dfs;

/**
 * LeetCode200
 * 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。你可以假设网格的四个边均被水包围。
 * 图论内容，使用DFS解决。遍历二维数组，遇到陆地的时候则岛屿数量+1，同时使用DFS消除与此岛屿相连的所有陆地，然后继续遍历二维数组，如果又碰到陆地，说明是一个新的岛，消除与之相连的陆地，继续遍历，同理直到遍历完毕
 * @author matthew_wu
 * @since 2021/1/22 11:50 上午
 */
public class NumberOfIslands {

    public static void main(String[] args) {
        int[][] grid = {{1,1,1,1,0,0},{1,1,1,1,0,1},{1,1,1,1,0,0},{1,1,1,1,0,0}};
        System.out.println(solution(grid));
        int[][] grid1 = {{1,1,1,1,0},{1,1,0,1,0},{1,1,0,0,0},{0,0,0,0,0}};
        System.out.println(solution(grid1));
        int[][] grid2 = {{1,1,0,0,0},{1,1,0,0,0},{0,0,1,0,0},{0,0,0,1,1}};
        System.out.println(solution(grid2));
    }

    private static int solution(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            System.out.println("input error!!!");
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        int cnt = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    cnt++;
                    doSearch(grid, i, j, rows, cols);
                }
            }
        }
        return cnt;
    }

    private static void doSearch(int[][] grid, int i, int j, int rows, int cols) {
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            return;
        }
        if (grid[i][j] != 1) {
            return;
        }
        // 消灭相邻的1
        grid[i][j] = 0;
        doSearch(grid, i-1, j, rows, cols);
        doSearch(grid, i+1, j, rows, cols);
        doSearch(grid, i, j-1, rows, cols);
        doSearch(grid, i, j+1, rows, cols);
    }


}
