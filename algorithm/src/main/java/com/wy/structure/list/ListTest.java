package com.wy.structure.list;

/**
 * @author matthew_wu
 * @since 2020/6/26 10:45
 */
public class ListTest {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        list.set("110000", 2);
        System.out.println(list);
        list.remove(7);
        list.add("ttt");
        System.out.println(list);
        System.out.println(list.size());
    }
}
