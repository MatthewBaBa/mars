package com.wy.interview.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author matthew_wu
 * @since 2021/1/25 5:06 下午
 */
public class ComputeWaterArea {

    public static void main(String[] args) {
        int[][] land = {{0,2,1,0},{0,1,0,1},{1,1,0,1},{0,1,0,1}};
        System.out.println(Arrays.toString(solution(land)));
    }

    private static int[] solution(int[][] land) {
        int rows = land.length;
        int cols = land[0].length;
        int size;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                size = doSearch(land, i, j);
                if (size != 0) {
                    list.add(size);
                }
            }
        }
        int[] result = list.stream().mapToInt(Integer::valueOf).toArray();
        Arrays.sort(result);
        return result;
    }

    private static int doSearch(int[][] land, int i, int j) {
        int size = 0;
        if (i < 0 || i >= land.length || j < 0 || j >= land[0].length) {
            return size;
        }
        if (land[i][j] != 0) {
            return size;
        }
        size++;
        // 如果为0，就转换为-1，避免重复搜索
        land[i][j] = -1;
        size += doSearch(land, i+1, j);
        size += doSearch(land, i, j+1);
        size += doSearch(land, i-1, j);
        size += doSearch(land, i, j-1);
        size += doSearch(land, i+1, j+1);
        size += doSearch(land, i-1, j-1);
        size += doSearch(land, i+1, j-1);
        size += doSearch(land, i-1, j+1);
        return size;
    }
}
