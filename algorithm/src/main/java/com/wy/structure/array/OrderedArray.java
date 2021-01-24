package com.wy.structure.array;

import java.util.Arrays;

/**
 * @author matthew_wu
 * @since 2020/6/26 12:30
 */
public class OrderedArray<E extends Comparable<E>> {
    private transient Object[] elementData;
    private int size;

    public OrderedArray(int capacity) {
        elementData = new Object[capacity];
    }

    public void insert(E e) {
        int i;
        for (i = 0; i < size; i++) {
            E obj = (E) elementData[i];
            if (e.compareTo(obj) < 0) {
                break;
            }
        }
        for (int k = size; k > i; k--) {
            elementData[k] = elementData[k-1];
        }
        elementData[i] = e;
        size++;
    }

    public int find(E key) {
        int low = 0;
        int high = size - 1;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;
            E obj = (E) elementData[mid];
            if (key.equals(obj)) {
                // found it
                return mid;
            } else if (key.compareTo(obj) > 0) {
                // it's in upper half
                low = mid + 1;
            } else {
                // it's in lower half
                high = mid - 1;
            }
        }
        // can't find it
        return -1;
    }

    public boolean delete(E e) {
        int index = find(e);
        if (index == -1) {
            return false;
        } else {
            for (int i = index; i < size - 1; i++) {
                elementData[i] = elementData[i+1];
            }
            size--;
            return true;
        }
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return Arrays.toString(elementData);
    }
}
