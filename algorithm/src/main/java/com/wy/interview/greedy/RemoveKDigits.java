package com.wy.interview.greedy;

import java.util.Stack;

/**
 * 贪心算法
 * 单调栈
 * @author matthew_wu
 * @since 2021/2/3 8:22 下午
 */
public class RemoveKDigits {

    public static void main(String[] args) {
        System.out.println(stack("10200",1));
    }

    public static String greedy(String num, int k) {
        if (num.length() == k) {
            return "0";
        }
        int minPos = 0;
        char[] chars = num.toCharArray();
        StringBuilder sb = new StringBuilder();
        // 有num.length - k - 1 个数
        for (int i = k; i < num.length(); i++) {
            for (int j = minPos; j <= i ; j++) {
                int number = Integer.valueOf(chars[j]);
                int min = Integer.valueOf(chars[minPos]);
                if (number < min) {
                    minPos = j;
                }
            }
            if (sb.length() != 0 || chars[minPos] != '0') {
                sb.append(chars[minPos]);
            }
            minPos = minPos + 1;
        }
        return sb.length() == 0 ? "0": sb.toString();
    }

    public static String stack(String num, int k) {
        Stack<Character> stack = new Stack<>();
        for (Character c: num.toCharArray()) {
            while (!stack.isEmpty() && c < stack.peek() && k > 0) {
                stack.pop();
                k--;
            }
            if (stack.isEmpty() && c == '0') {
                continue;
            }
            stack.push(c);
        }
        while (k > 0 && !stack.isEmpty()) {
            stack.pop();
            k--;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.length() == 0 ? "0": sb.reverse().toString();
    }
}
