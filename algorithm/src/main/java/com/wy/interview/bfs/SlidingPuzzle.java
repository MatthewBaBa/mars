package com.wy.interview.bfs;

import java.util.*;

/**
 * 773. 滑动谜题
 * @author matthew_wu
 * @since 2021/3/9 4:51 下午
 */
public class SlidingPuzzle {

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{4,1,2},{5,0,3}}));
    }

    private static int solution(int[][] board) {
        int m = 2, n = 3;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append((char) (board[i][j] + '0'));
            }
        }
        String start = sb.toString(), end = "123450";
        int[][] neighbors = new int[][] {
                {1, 3},
                {0, 2, 4},
                {1, 5},
                {0, 4},
                {3, 1, 5},
                {4, 2}
        };
        int step = 0;
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                String cur = queue.poll();
                if (cur.equals(end)) {
                    return step;
                }
                int index = cur.indexOf('0');
                for (int neighbor: neighbors[index]) {
                    String s = swap(cur, index, neighbor);
                    // 防止走回头路
                    if (!visited.contains(s)) {
                        queue.add(s);
                        visited.add(s);
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private static String swap(String s, int src, int target) {
        char[] chars = s.toCharArray();
        char temp = chars[src];
        chars[src] = chars[target];
        chars[target] = temp;
        return new String(chars);
    }
}
