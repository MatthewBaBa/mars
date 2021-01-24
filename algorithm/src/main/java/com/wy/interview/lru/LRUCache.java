package com.wy.interview.lru;

/**
 * @author matthew_wu
 * @since 2020/12/23 2:54 下午
 */
public interface LRUCache<E, T> {

    /**
     * get value
     * @param key
     * @return T
     **/
    T get(E key);

    /**
     * set key-value pair
     * @param key
     * @param value
     * @return void
     **/
    void put(E key, T value);

}
