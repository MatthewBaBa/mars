package com.wy.sort;

import java.util.Comparator;

/**
 * @author matthew_wu
 * @since 2020/6/25 15:11
 */
public class HeapSort extends Sorter {

    @Override
    public <T extends Comparable<T>> void sort(T[] list) {
        sort(list, Comparator.naturalOrder());
    }

    @Override
    public <T> void sort(T[] list, Comparator<T> comp) {
        // 第一个非叶节点为length/2-1
        //1.构建大顶堆
        buildMaxHeap(list, comp);
        //2.调整堆结构+交换堆顶元素与末尾元素
        for (int i = list.length - 1; i > 0; i--) {
            swap(list, 0, i);
            adjustHeap(list, 0, i, comp);
        }
    }

    //建立一个小根堆
    private <T> void buildMaxHeap(T[] list, Comparator<T> comp) {
        for (int i = list.length/2 - 1; i >= 0 ; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjustHeap(list, i, list.length, comp);
        }
    }

    private <T> void adjustHeap(T[] list, int i, int length, Comparator<T> comp) {
        T top = list[i];
        for (int k = 2*i + 1; k < length; k = 2*k + 1) {
            if (k + 1 < length && comp.compare(list[k], list[k+1]) < 0) {
                k++;
            }
            //筛选结束，不用往下调整了
            if(comp.compare(list[k], top) <= 0) {
                break;
            }
            //需要往下调整
            list[i] = list[k];
            i = k;
        }
        list[i] = top;
    }
}
