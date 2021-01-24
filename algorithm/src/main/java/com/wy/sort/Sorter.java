package com.wy.sort;

import java.util.Comparator;

/**
 * 排序器接口(策略模式: 将算法封装到具有共同接口的独立的类中使得它们可以相互替换)
 *
 * @author matthew_wu
 * @since 2020-06-02 18:13
 */
public abstract class Sorter {

    /**
     * 排序
     *
     * @param list 待排序数组
     * @return void
     **/
    public abstract <T extends Comparable<T>> void sort(T[] list);

    /**
     * @param list 待排序数组
     * @param comp 比较两个对象的比较器
     * @return void
     **/
    public abstract <T> void sort(T[] list, Comparator<T> comp);

    /**
     * 交换方法
     * @param list, i, j
     * @return void
     **/
    protected  <T> void swap(T[] list, int i, int j) {
        T temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }
}
