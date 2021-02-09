package com.wy.interview.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * 77. 组合
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 * @author matthew_wu
 * @since 2021/3/2 2:58 下午
 */
public class CombineNumbers {

    private static List<List<Integer>> res = new ArrayList<>();
    private static List<Integer> path = new ArrayList<>();

    public static void main(String[] args) {
        backtrack(4, 3, 1);
        System.out.println(res);
    }

    private static void backtrack(int n, int k, int index) {
        if (path.size() == k) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = index; i <= n; i++) {
            path.add(i);
            backtrack(n, k, i + 1);
            path.remove((Integer) i);
        }
    }

}

