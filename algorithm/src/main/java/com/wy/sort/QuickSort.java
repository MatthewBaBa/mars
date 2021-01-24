package com.wy.sort;

import java.util.Comparator;

/**
 * @author matthew_wu
 * @since 2020/6/25 13:32
 */
public class QuickSort extends Sorter {

    @Override
    public <T extends Comparable<T>> void sort(T[] list) {
        sort(list, Comparator.naturalOrder());
    }

    @Override
    public <T> void sort(T[] list, Comparator<T> comp) {
        quickSort(list, 0, list.length - 1, comp);
    }

    /**
     * size = right - left + 1;
     * 当分区size的数值很小时，可以使用插入排序或者手动排序做优化
     */
    private <T> void quickSort(T[] list, int left, int right, Comparator<T> comp) {
        if (left < right) {
            dealPivot(list, left, right, comp);
            int partition = partition(list, left, right, comp);
            quickSort(list, left, partition - 1, comp);
            quickSort(list, partition + 1, right, comp);
        }
    }

    private <T> int partition(T[] list, int left, int right, Comparator<T> comp) {
        //枢纽值被放在序列末尾
        int pivot = right - 1;
        int i = left;
        int j = right - 1;
        while (true) {
            while (comp.compare(list[++i], list[pivot]) < 0) {
            }
            while (j > left && comp.compare(list[--j], list[pivot]) > 0) {
            }
            if (i < j) {
                swap(list, i, j);
            } else {
                break;
            }
        }
        // restore pivot
        if (i < right) {
            swap(list, i, pivot);
        }
        // return pivot location
        return i;
    }

    private <T> void dealPivot(T[] list, int left, int right, Comparator<T> comp) {
        int mid = (left + right) / 2;
        if (comp.compare(list[left], list[mid]) > 0) {
            swap(list, left, mid);
        }

        if (comp.compare(list[left], list[right]) > 0) {
            swap(list, left, right);
        }

        if (comp.compare(list[mid], list[right]) > 0) {
            swap(list, mid, right);
        }
        swap(list, mid, right - 1);
    }

    /**
     * 手动排序
     */
    private <T> void manualSort(T[] list, int left, int right, Comparator<T> comp) {
        int size = right - left + 1;
        // no sort necessary
        if(size <= 1) {
            return;
        }
        // 2-sort left and right
        if(size == 2) {
            if(comp.compare(list[left], list[right]) > 0) {
                swap(list, left, right);
            }
            return;
        }
        // size is 3, 3-sort left, center, & right
        if(comp.compare(list[left], list[right-1]) > 0) {
            swap(list, left, right-1);
        }
        if(comp.compare(list[left], list[right]) > 0) {
            swap(list, left, right);
        }
        if(comp.compare(list[right-1], list[right]) > 0) {
            swap(list,right-1, right);
        }
    }

}
