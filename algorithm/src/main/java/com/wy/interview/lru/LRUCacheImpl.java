package com.wy.interview.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 直接利用 LinkedHashMap 的 removeEldestEntry来实现
 * @author matthew_wu
 * @since 2020/12/23 3:03 下午
 */
public class LRUCacheImpl<E, T> implements LRUCache<E, T> {

    private LinkedHashMap<E, T> cache;

    public LRUCacheImpl(int capacity) {
        cache = new LinkedHashMap<E, T>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<E, T> eldest) {
                return size() > capacity;
            }
        };
    }

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public T get(E key) {
        lock.readLock().lock();
        T value = cache.get(key);
        lock.readLock().unlock();
        return value;
    }

    @Override
    public void put(E key, T value) {
        lock.writeLock().lock();
        cache.put(key, value);
        lock.writeLock().unlock();
    }

    @Override
    public String toString() {
        return cache.toString();
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> lruCache = new LRUCacheImpl<>(2);
        lruCache.put(1,1);
        lruCache.put(2,2);
        System.out.println(lruCache);
        System.out.println(lruCache.get(1));
        lruCache.put(3,3);
        System.out.println(lruCache.get(2));
        lruCache.put(4,4);
        System.out.println(lruCache);
        System.out.println(lruCache.get(3));
        System.out.println(lruCache.get(4));
    }

}
