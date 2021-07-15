package com.wy.interview.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Question:
In the ocean, there is an island of grids which can abstracted as a two-dimension array island[n][n] where each element is the height of the grid.
In a rainy day, the water fall on the island[i][j] can flow into its 4 adjacent (left, right, up, down) island[x][y] if island[i][j] >= island[x][y].
Assume the height of the sea is 0.
Find all grids on the island that the water fallen on it might flow into the sea on all 4 sides. What’s the time complexity of your solution?

For example:


Input: island = [[1, 1, 1, 1], [2, 4, 5, 3], [1, 6, 1, 4], [2, 1, 2, 3]]
Output: [[2, 1], [2, 3]] (grids with height: 6 and 4)

                  sea
          -----------------------
        |  1  |  1  |  1  |  1  |
         -----------------------
        |  2  |  4  |  5  |  3  |
sea      -----------------------      sea
        |  1  |  6  |  1  |  4  |
         -----------------------
        |  2  |  1  |  2  |  3  |
         -----------------------
                  sea
Requirements:
O(n^2) time complexity
*/

/**
 * @author matthew_wu
 * @since 2021/5/6 3:40 下午
 */
public class WaterFlowFrom4Adjacent {

    private static boolean[][] up, bottom, left, right;
    private static int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private static int len;
    private static List<List<Integer>> res = new ArrayList<>();

    public static void main(String[] args) {
        int[][] grid = new int[][]{{1, 1, 1, 1}, {2, 4, 5, 3}, {1, 6, 1, 4}, {2, 1, 2, 3}};
        solution(grid);
        System.out.println(res);
    }

    private static void solution(int[][] grid) {
        len = grid.length;
        up = new boolean[len][len];
        bottom = new boolean[len][len];
        left = new boolean[len][len];
        right = new boolean[len][len];
        for(int i = 0; i < len; i++) {
            dfs(grid, up, 0, i);
            dfs(grid, bottom, len - 1, i);
            dfs(grid, left, i, 0);
            dfs(grid, right, i, len - 1);
        }
        print(up);
        print(bottom);
        print(left);
        print(right);
        for(int i = 0; i < len; i++) {
            for(int j = 0; j < len; j++) {
                if(up[i][j] && bottom[i][j] && left[i][j] && right[i][j]) {
                    List<Integer> ad = new ArrayList<>();
                    ad.add(i);
                    ad.add(j);
                    res.add(ad);
                }
            }
        }
    }

    private static void print(boolean[][] v) {
        for(boolean[] v1: v) {
            System.out.print(Arrays.toString(v1));
        }
        System.out.println();
    }

    private static void dfs(int[][] grid, boolean[][] visited, int x, int y) {
        if(visited[x][y]) {
            return;
        }
        visited[x][y] = true;
        for(int i = 0; i < directions.length; i++) {
            int newX = x + directions[i][0];
            int newY = y + directions[i][1];
            if(!inArea(grid, newX, newY)) {
                continue;
            }
            if(grid[x][y] > grid[newX][newY]) {
                continue;
            }
            dfs(grid, visited, newX, newY);
        }
    }

    private static boolean inArea(int[][] grid, int x, int y) {
        return x >= 0 && x < len && y >= 0 && y < len;
    }

}

