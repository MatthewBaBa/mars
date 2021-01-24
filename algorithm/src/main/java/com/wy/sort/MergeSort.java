package com.wy.sort;

import java.util.Comparator;
import java.util.Objects;

/**
 * 递归归并的思想
 * @author matthew_wu
 * @since 2020/6/25 11:00
 */
public class MergeSort extends Sorter {

    @Override
    public <T extends Comparable<T>> void sort(T[] list) {
        sort(list, Comparator.naturalOrder());
    }

    @Override
    public <T> void sort(T[] list, Comparator<T> comp) {
        @SuppressWarnings("unchecked")
        T[] temp =  (T[]) new Object[list.length];
        sort(list, 0, list.length-1, temp, comp);
    }

    private <T> void sort(T[] list, int left, int right, T[] temp, Comparator<T> comp) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(list, left, mid, temp, comp);
            sort(list, mid+1, right, temp, comp);
            merge(list, left, mid, right, temp, comp);
        }
    }

    private <T> void merge(T[] list, int left, int mid, int right, T[] temp, Comparator<T> comp) {
        // 左指针
        int i = left;
        // 右指针
        int j = mid + 1;
        // 临时指针, temp数组使用
        int t = 0;

        while (i <= mid && j <= right) {
            if (comp.compare(list[i], list[j]) > 0) {
                temp[t++] = list[j++];
            } else {
                temp[t++] = list[i++];
            }
        }
        //复制左边剩余元素
        while (i <= mid) {
            temp[t++] = list[i++];
        }
        //复制右边剩余元素
        while (j <= right) {
            temp[t++] = list[j++];
        }

        t = 0;
        while (left <= right) {
            list[left++] = temp[t++];
        }
    }
}
