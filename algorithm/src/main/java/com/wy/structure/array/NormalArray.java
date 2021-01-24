package com.wy.structure.array;

import java.util.Arrays;

/**
 * @author matthew_wu
 * @since 2020/6/26 12:30
 */
public class NormalArray<E extends Comparable<E>> {
    private transient Object[] elementData;
    private int size;

    public NormalArray(int capacity) {
        elementData = new Object[capacity];
    }

    public void insert(E e) {
        elementData[size++] = e;
    }

    public int find(E key) {
        int j;
        for(j = 0; j < size; j++) {            // for each element
            if( key.equals(elementData[j] )) {  // found item?
                break;                          // exit loop before end
            }
        }
        return j;
    }

    public boolean delete(E e) {
        int index = find(e);
        if (index == size) {
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
