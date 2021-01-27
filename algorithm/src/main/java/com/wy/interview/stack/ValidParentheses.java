package com.wy.interview.stack;

import java.util.Stack;

/**
 * LeetCode20 有效的括号
 * @author matthew_wu
 * @since 2021/1/28 2:26 下午
 */
public class ValidParentheses {

    public static void main(String[] args) {
        System.out.println(solution("("));
    }

    private static boolean solution(String s) {
        if (s.length() % 2 == 1) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (Character c : s.toCharArray()) {
            if (c == ')' || c == '}' || c == ']') {
                if (!stack.isEmpty() && c.equals(stack.peek())) {
                    stack.pop();
                } else {
                    return false;
                }
            } else {
                switch (c) {
                    case '(':
                        stack.push(')');
                        break;
                    case '{':
                        stack.push('}');
                        break;
                    case '[':
                        stack.push(']');
                        break;
                }
            }
        }
        return stack.isEmpty();
    }

}
