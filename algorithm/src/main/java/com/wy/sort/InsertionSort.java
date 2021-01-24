package com.wy.sort;

import java.util.Comparator;

/**
 * @author matthew_wu
 * @since 2020-06-24 18:22
 */
public class InsertionSort extends Sorter {

    @Override
    public <T extends Comparable<T>> void sort(T[] list) {
        sort(list, Comparator.naturalOrder());
    }

    @Override
    public <T> void sort(T[] list, Comparator<T> comp) {
        for (int i = 1; i < list.length; i++) {
            int j = i;
            while (j > 0 && comp.compare(list[j],list[j-1]) < 0 ) {
                swap(list, j, j-1);
                j--;
            }
        }
    }
}
