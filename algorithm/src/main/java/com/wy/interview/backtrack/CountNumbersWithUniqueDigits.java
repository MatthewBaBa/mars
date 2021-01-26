package com.wy.interview.backtrack;

/**
 * 计算各个位数不同的数字个数
 * @author matthew_wu
 * @since 2021/1/26 11:55 上午
 */
public class CountNumbersWithUniqueDigits {

    public static void main(String[] args) {
        int backtrace = backtrace(Math.min(2, 10), 1, new boolean[10]);
        System.out.println(backtrace);
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


    /**
     * 排列组合：n位有效数字 = 每一位都从 0~9 中选择，且不能以 0 开头
     * 1位数字：0~9                      10
     * 2位数字：C10-2，且第一位不能是0      9 * 9
     * 3位数字：C10-3，且第一位不能是0      9 * 9 * 8
     * 4位数字：C10-4，且第一位不能是0      9 * 9 * 8 * 7
     * ... ...
     * 最后，总数 = 所有 小于 n 的位数个数相加
     */
    public static int dp(int n) {
        if (n == 0) {
            return 1;
        }
        int first = 10, second = 9 * 9;
        int size = Math.min(n, 10);
        for (int i = 2; i <= size; i++) {
            first += second;
            second *= 10 - i;
        }
        return first;
    }

}
