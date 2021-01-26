package com.wy.interview.dp;

import java.util.Arrays;

/**
 *
 * @author matthew_wu
 * @since 2020-06-30 16:35
 */
public class EditPath {

    public static void main(String[] args) {
        System.out.println(solution("horse", "ros"));
    }

    private static int minDistance(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        int[][] dp = new int[m+1][n+1];

        //base case
        for (int i = 1; i <= m ; i++) {
            dp[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }
        //自底向上
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1];
                } else {
                    dp[i][j] = min(
                            //插入
                            dp[i][j-1] + 1,
                            //删除
                            dp[i-1][j] + 1,
                            //替换
                            dp[i-1][j-1] + 1
                    );
                }
            }
        }
        return dp[m][n];
    }

    private static int min(int i, int j, int k) {
        return Math.min(i, Math.min(j, k));
    }

    /**
     * 拿到具体路径信息
     **/
    public static int solution(String str1, String str2) {
        int m = str1.length(), n = str2.length();
        Node[][] dp = new Node[m+1][n+1];
        //base case
        for (int i = 1; i <= m; i++) {
            dp[i][0] = new Node(i, 2);
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = new Node(j, 2);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i-1) == str2.charAt(j-1)) {
                    dp[i][j] = new Node(dp[i-1][j-1].val, 0);
                } else {
                    Node insert = new Node(dp[i][j-1].val + 1, 1);
                    Node delete = new Node(dp[i-1][j].val + 1, 2);
                    Node replace = new Node(dp[i-1][j-1].val + 1, 3);
                    dp[i][j] = min(insert, delete, replace);
                }
            }
        }
        for (Node[] a: dp) {
            System.out.println(Arrays.toString(a));
        }
        return dp[m][n].val;
    }

    private static Node min(Node a, Node b, Node c) {
        Node min = a;
        if (b.compareTo(min) < 0) {
            min = b;
        }
        if (c.compareTo(min) < 0) {
            min = c;
        }
        return min;
    }

    static class Node implements Comparable {
        int val;
        int choice;
        // 0 代表啥都不做
        // 1 代表插入
        // 2 代表删除
        // 3 代表替换

        public Node(int val, int choice) {
            this.val = val;
            this.choice = choice;
        }

        @Override
        public int compareTo(Object o) {
            return val - ((Node) o).val;
        }

        @Override
        public String toString() {
            String operation = "";
            switch (choice) {
                case 0:
                    operation = "不操作";
                    break;
                case 1:
                    operation = "插入";
                    break;
                case 2:
                    operation = "删除";
                    break;
                case 3:
                    operation = "替换";
                    break;
                default:
            }
            return val + " " + operation;
        }
    }
}
