package com.wy.interview.lru;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author matthew_wu
 * @since 2021/2/25 11:34 上午
 */
public class CustomLRUCache {
    private Map<Integer, Integer> cacheMap = new HashMap<>();
    private LinkedList<Integer> recentlyList = new LinkedList<>();
    private int capacity;

    public CustomLRUCache(int capacity) {
        this.capacity = capacity;
    }

    public void put(int key, int value) {
        if (cacheMap.containsKey(key)) {
            recentlyList.remove((Integer) key);
        } else if (cacheMap.size() == capacity){
            cacheMap.remove(recentlyList.removeFirst());
        }
        recentlyList.add(key);
        cacheMap.put(key, value);
    }

    public int get(int key) {
        if (!cacheMap.containsKey(key)) {
            return -1;
        }
        recentlyList.remove((Integer) key);
        recentlyList.add(key);
        return cacheMap.get(key);
    }

    public static void main(String[] args) {
        CustomLRUCache cache = new CustomLRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        // returns 1
        System.out.println(cache.get(1));
        // 驱逐 key 2
        cache.put(3, 3);
        // returns -1 (not found)
        System.out.println(cache.get(2));
        // 驱逐 key 1
        cache.put(4, 4);
        // returns -1 (not found)
        System.out.println(cache.get(1));
        // returns 3
        System.out.println(cache.get(3));
        // returns 4
        System.out.println(cache.get(4));
    }

}
