package com.wy.structure.list;

/**
 * @author matthew_wu
 * @since 2020/6/26 12:17
 */
public class LinkedList<E> implements List<E> {

    @Override
    public void add(E e) {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public void set(E e, int index) {

    }

    @Override
    public void remove(int index) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    private static class Node<E> {
        E item;

    }
}
