package com.wy.interview.stack;

import java.util.Stack;

/**
 * @author matthew_wu
 * @since 2021/2/1 6:02 下午
 */
public class ImplementQueueByStacks {

}

class Queue {
    private Stack<Integer> pushStack;
    private Stack<Integer> popStack;

    /** Initialize your data structure here. */
    public Queue() {
        pushStack = new Stack<>();
        popStack = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        pushStack.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if (!popStack.isEmpty()) {
            return popStack.pop();
        }
        while(!pushStack.isEmpty()) {
            popStack.push(pushStack.pop());
        }
        return  popStack.pop();
    }

    /** Get the front element. */
    public int peek() {
        if (!popStack.isEmpty()) {
            return popStack.peek();
        }
        while(!pushStack.isEmpty()) {
            popStack.push(pushStack.pop());
        }
        return popStack.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return popStack.isEmpty() && pushStack.isEmpty();
    }

}