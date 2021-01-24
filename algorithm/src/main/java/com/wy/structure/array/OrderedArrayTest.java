package com.wy.structure.array;

/**
 * @author matthew_wu
 * @since 2020/6/26 17:45
 */
public class OrderedArrayTest {

    public static void main(String[] args) {
        OrderedArray<Integer> array = new OrderedArray<>(10);
        for (int i = 0; i < 9; i++) {
            array.insert( (int) (Math.random() * 99));
        }
        array.insert(40);
        System.out.println(array);
        System.out.println(array.find(40));
        System.out.println(array.delete(40));
        System.out.println(array);
        System.out.println(array.size());
    }
}
