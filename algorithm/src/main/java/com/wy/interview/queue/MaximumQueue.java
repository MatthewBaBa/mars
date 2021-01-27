package com.wy.interview.queue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都是 O(1)。
 * 若队列为空，pop_front 和 max_value  需要返回 -1
 * @author matthew_wu
 * @since 2021/1/29 5:36 下午
 */
public class MaximumQueue {

    public static void main(String[] args) {
        MaxQueue queue = new MaxQueue();
        queue.push_back(1);
        queue.push_back(2);
        System.out.println(queue.max_value());
        queue.pop_front();
        System.out.println(queue.max_value());

    }

}

class MaxQueue {
    private Queue<Integer> data;
    private Deque<Integer> max;

    public MaxQueue() {
        data = new LinkedList<>();
        max = new LinkedList<>();
    }

    public void push_back(int value) {
        data.offer(value);
        while (!max.isEmpty() && max.peekLast() < value) {
            max.pollLast();
        }
        max.offerLast(value);
    }

    public int pop_front() {
        if (data.isEmpty()) {
            return -1;
        }
        int res = data.poll();
        if (max.peekFirst() == res) {
            max.pollFirst();
        }
        return res;
    }

    public int max_value() {
        return max.isEmpty() ? -1 : max.peekFirst();
    }

}