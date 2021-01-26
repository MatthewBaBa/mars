package com.wy.interview.backtrack;

import com.sun.xml.internal.txw2.output.DumpSerializer;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 计算各个位数不同的数字个数
 * @author matthew_wu
 * @since 2021/1/26 11:55 上午
 */
public class CountNumbersWithUniqueDigits {

    public static void main(String[] args) {
        solution(2);
    }

    private static void solution(int n) {
        System.out.println(backtrace(Math.min(n, 10), 1, new boolean[10]));
    }

    /**
     *
     * @param index 当前层是在遍历第几位
     * @param n 一共有n位
     * @param used 记忆化递归
     * @return
     */
    private static int backtrace(int n, int index, boolean[] used) {
        if (index > n) {
            return 0;
        }
        int count = 0;
        // 遍历0-9
        for (int i = 0; i < 10; i++) {
            // 剪枝：多位数时，第一个数字不能为0, 如：0x 是不被允许的。
            if (used[0] && index == 2) {
                continue;
            }
            // 剪枝：不能使用用过的数字
            if (used[i]) {
                continue;
            }
            used[i]=true;
            count += backtrace(n, index + 1, used) + 1;
            used[i]=false;
        }
        return count;
    }

}
