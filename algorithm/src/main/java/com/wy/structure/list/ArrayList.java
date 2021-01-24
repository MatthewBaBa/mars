package com.wy.structure.list;

import com.wy.structure.list.List;

/**
 * @author matthew_wu
 * @since 2020/6/26 10:31
 */
public class ArrayList<E> implements List<E> {
    private transient Object[] elementData;
    //不用初始化，默认int的值是0
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 根据提供的容器大小初始化
     * @param capacity 容量
     */
    public ArrayList(int capacity) {
        if (capacity < 0) {
            throw new RuntimeException("Illegal Capacity: " + capacity);
        } else if (capacity == 0) {
            elementData = new Object[DEFAULT_CAPACITY];
        } else {
            elementData = new Object[capacity];
        }
    }

    public ArrayList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    //int newCapacity = oldCapacity + (oldCapacity >> 1),
    //所以ArrayList每次扩容之后容量都会变为原来的 1.5倍
    @Override
    public void add(E e) {
        if (size == elementData.length) {
            Object[] newArray = new Object[elementData.length + elementData.length >> 1];
            System.arraycopy(elementData, 0, newArray, 0, elementData.length);
            elementData = newArray;
        }
        elementData[size++] = e;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > size - 1) {
            throw new RuntimeException("invalid index: " + index);
        }
        return (E) elementData[index];
    }

    @Override
    public void set(E e, int index) {
        if (index < 0 || index > size - 1) {
            throw new RuntimeException("invalid index: " + index);
        }
        elementData[index] = e;
    }

    @Override
    public void remove(int index) {
        int numMoved = elementData.length - 1 - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
    }

    public void remove(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(get(i))) {
                remove(i);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        if(size <= 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(elementData[i] + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(']');
        return sb.toString();
    }

}
