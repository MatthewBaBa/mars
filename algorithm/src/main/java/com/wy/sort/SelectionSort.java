package com.wy.sort;

import java.util.Comparator;

/**
 * @author matthew_wu
 * @since 2020-06-24 14:51
 */
public class SelectionSort extends Sorter {

    @Override
    public <T extends Comparable<T>> void sort(T[] list) {
        sort(list, Comparator.naturalOrder());
    }

    @Override
    public <T> void sort(T[] list, Comparator<T> comp) {
        for (int i = 0; i < list.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < list.length; j++) {
                if (comp.compare(list[min], list[j]) > 0) {
                    min = j;
                }
            }
            if (min != i) {
                swap(list, i, min);
            }
        }
    }
}
