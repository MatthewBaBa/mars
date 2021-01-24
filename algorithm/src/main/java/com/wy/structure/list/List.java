package com.wy.structure.list;

/**
 * 没有SortedList
 * 所有列表都已经按照添加项的顺序进行了“排序”(FIFO排序)，
 * 可以使用另一个顺序“使用”它们，包括元素的自然排序，使用java.util.Collections.sort().
 * @author matthew_wu
 * @since 2020/6/26 12:02
 */
public interface List<E> {

    void add(E e);

    E get(int index);

    void set(E e, int index);

    void remove(int index);

    int size();

    boolean isEmpty();



}
