package com.wy.interview.stack;

import java.util.Stack;

/**
 * @author matthew_wu
 * @since 2021/1/28 3:11 下午
 */
public class MinimumStack {

    public static void main(String[] args) {
        MinStack<Integer> stack =  new MinStack<>();
        stack.push(-1);
        stack.push(1);
        stack.push(3);
        stack.push(-8);
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
    }
}

class MinStack<T extends Comparable> {

    private Stack<T> stack;
    private Stack<T> min;

    public MinStack() {
        stack = new Stack<>();
        min = new Stack<>();
    }

    public T pop() {
        min.pop();
        return stack.pop();
    }

    public T top() {
        return stack.peek();
    }

    public void push(T value) {
        stack.push(value);
        if (min.isEmpty() || min.peek().compareTo(value) >= 0) {
            min.push(value);
        } else {
            min.push(min.peek());
        }
    }

    public T getMin() {
        return min.peek();
    }

}
