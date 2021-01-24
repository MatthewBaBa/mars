package com.wy.sort;

import java.util.Comparator;

/**
 * @author matthew_wu
 * @since 2020/6/25 9:51
 */
public class ShellSort extends Sorter {

    @Override
    public <T extends Comparable<T>> void sort(T[] list) {
        sort(list, Comparator.naturalOrder());
    }

    @Override
    public <T> void sort(T[] list, Comparator<T> comp) {
        //增量gap，并逐步缩小增量
        for (int gap = list.length/2; gap > 0; gap /= 2) {
            for (int i = gap; i < list.length; i++) {
                int j = i;
                while (j - gap >= 0 && comp.compare(list[j], list[j-gap]) < 0) {
                    swap(list, j, j-gap);
                    j -= gap;
                }
            }
        }
    }
    
}
