package com.wy.interview.list;

/**
 * LeetCode 707. 设计链表
 * @author matthew_wu
 * @since 2021/3/11 2:14 下午
 */
public class MyLinkedList {

    class ListNode {
        int val;
        ListNode prev;
        ListNode next;
        public ListNode() {}
        public ListNode(int val) {
            this.val = val;
        }
    }
    private ListNode dummyHead;
    private ListNode dummyTail;
    private int size;

    /** Initialize your data structure here. */
    public MyLinkedList() {
        dummyHead = new ListNode();
        dummyTail = new ListNode();
        dummyHead.next = dummyTail;
        dummyTail.prev = dummyHead;
        size = 0;
    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if(isNotValid(index)) {
            return -1;
        }
        ListNode node = getNode(index);
        return node.val;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if (index > size) {
            return;
        }
        if (index < 0) {
            index = 0;
        }
        ListNode node = getNode(index);
        ListNode newNode = new ListNode(val);
        newNode.prev = node.prev;
        newNode.next = node;
        node.prev.next = newNode;
        node.prev = newNode;
        size++;
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(isNotValid(index)) {
            return;
        }
        ListNode node = getNode(index);
        removeNode(node);
    }

    private void removeNode(ListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    private ListNode getNode(int index) {
        ListNode cur;
        if(index > size / 2) {
            cur = dummyTail;
            while(index <= size - 1) {
                cur = cur.prev;
                index++;
            }
        } else {
            cur = dummyHead;
            while(index >= 0) {
                cur = cur.next;
                index--;
            }
        }
        return cur;
    }

    private boolean isNotValid(int index) {
        return index < 0 || index >= size;
    }

}
