package com.wy.sort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author matthew_wu
 * @since 2020-06-24 11:23
 */
public class SorterTest {
    public static void main(String[] args) {
        Sorter sorter = new HeapSort();
        Integer[] list = new Integer[30];
        // fill array with random numbers
        for (int i = 0; i < list.length; i++) {
            list[i] = (int) (Math.random() * 99);
        }
        System.out.println(Arrays.toString(list));
        // 为什么Comparator.naturalOrder().reversed()会有范型错误
        sorter.sort(list, Comparator.comparing((Integer i) -> i).reversed());
        System.out.println(Arrays.toString(list));
    }
}
