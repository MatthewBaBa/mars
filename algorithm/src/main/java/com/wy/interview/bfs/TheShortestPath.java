package com.wy.interview.bfs;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * n*n的0-1矩阵，从0*0 走到 (n-1)*(n-1) 求最短路， 0可以走，1不能走
 * @author matthew_wu
 * @since 2021/5/11 20:12
 */
public class TheShortestPath {

    private static int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) {
        char[][] grid = new char[][]{
                {'0', '1', '0', '0', '0'},
                {'0', '1', '0', '1', '0'},
                {'0' ,'0' ,'0' ,'0' ,'0'},
                {'0' ,'1' ,'1' ,'1' ,'0'},
                {'0' ,'0' ,'0' ,'1' ,'0'}
        };
        System.out.println(solution(grid));
    }

    private static int solution(char[][] grid) {
        if(grid == null || grid.length == 0) {
            return 0;
        }
        return bfs(grid);
    }

    private static int bfs(char[][] grid) {
        int n = grid.length;
        Deque<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];
        int steps = 1;
        queue.offer(new Point(0, 0));
        visited[0][0] = true;

        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                Point cur = queue.poll();
                int x = cur.getX();
                int y = cur.getY();
                if(x == n - 1 && y == n - 1) {
                    return steps;
                }
                for(int j = 0; j < directions.length; j++) {
                    int newX = x + directions[j][0];
                    int newY = y + directions[j][1];
                    if(inArea(grid, newX, newY) && grid[newX][newY] == '0' && !visited[newX][newY]) {
                        queue.offer(new Point(newX, newY));
                        visited[newX][newY] = true;
                    }
                }
            }
            steps++;
        }
        print(visited);
        return -1;
    }

    private static void print(boolean[][] v) {
        for (boolean[] v1: v) {
            System.out.println(Arrays.toString(v1));
        }
    }

    private static boolean inArea(char[][] grid, int i, int j) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid.length;
    }

    static class Point {

        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

}
