package com.wy.sort;

import java.util.Comparator;

/**
 *
 * @author matthew_wu
 * @since 2020-06-02 18:18
 */
public class BubbleSort extends Sorter {

    @Override
    public <T extends Comparable<T>> void sort(T[] list) {
        sort(list, Comparator.naturalOrder());
    }

    @Override
    public <T> void sort(T[] list, Comparator<T> comp) {
        for (int i = list.length - 1; i > 0; i--) {
            boolean flag = true;
            for (int j = 0; j < i; j++) {
                if (comp.compare(list[j], list[j+1]) > 0) {
                    swap(list, j, j+1);
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }
}
