package com.wy.interview.stack;

import java.util.Stack;

/**
 *
 * @author matthew_wu
 * @since 2021/2/1 6:14 下午
 */
public class EvaluateReversePolishNotation {

    public static void main(String[] args) {
        System.out.println(solution(new String[]{"2", "1", "+", "3", "*"}));
        System.out.println(solution(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}));
    }

    public static int solution(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String s: tokens) {
            switch (s) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    int a = stack.pop();
                    stack.push(stack.pop() - a);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    int b = stack.pop();
                    stack.push(stack.pop() / b);
                    break;
                default:
                    stack.push(Integer.parseInt(s));
            }
        }
        return stack.pop();
    }
    
}
