package com.wy.interview.queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author matthew_wu
 * @since 2021/2/1 6:03 下午
 */
public class ImplementStackByQueues {

}

class Stack {
    private Deque<Integer> deque;

    /** Initialize your data structure here. */
    public Stack() {
        deque = new ArrayDeque<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        deque.offerLast(x);
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        return deque.pollLast();
    }

    /** Get the top element. */
    public int top() {
        return deque.peekLast();
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return deque.isEmpty();
    }
}