package com.wy.interview.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * 单调栈 解法
 * 当前元素关注的是它右边的元素
 * 我选择从右遍历，先为最右的元素找目标元素，需要考察的右边元素由少到多
 * 用空间换取时间
 *
 * 遍历一遍数组是少不了的，关键是每个元素寻找目标元素做了很多重复的遍历
 * 我们用一个数据结构去存储右边的项，想将无需比较的元素从该数据结构中剔除，且不会再进来了，避免重复考察
 * 什么剔除，什么留下
 *
 * T[i]T[i] 的目标是找到第一个大项，比它小的项该被剔除，因为比 T[i]T[i] 还小的元素，肯定不会是 T[i-1]T[i−1] 想找的大项
 * 大项留下，T[i]T[i] 也进入到这个“数据结构”中，供 T[i-1]T[i−1] 寻找
 * 保持单调，为了更快比较
 *
 * 如果“数据结构”中的项无序，则新来的项无法快速地比较大小，找到大项
 * 如果从小到大排好，则很容易剔除小项，遇到大项
 * 为什么是栈
 *
 * 小项 “出” 了，当前项要 “进”，依然要维持排列的单调性
 * 所以当前项要从小项 “出” 的地方 “进”
 * 只在一端进出——所以是栈
 *
 * @author matthew_wu
 * @since 2021/2/2 5:34 下午
 */
public class DailyTemperatures {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{89,62,70,58,47,47,46,76,100,70})));
    }

    private static int[] solution(int[] T) {
        int[] res = new int[T.length];
        Stack<Integer> stack = new Stack<>();
        for (int i = T.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && T[i] >= T[stack.peek()]) {
                stack.pop();
            }
            if (!stack.isEmpty()) {
                res[i] = stack.peek() - i;
            }
            stack.push(i);
        }
        return res;
    }

}
