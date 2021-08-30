package com.example.interview;

import java.util.LinkedHashMap;
import java.util.Map;

class LRUCache<K,V> extends LinkedHashMap<K,V> {

    private int capacity;

    /**
     * the ordering mode
     * - <tt>true</tt> for access-order,
     * - <tt>false</tt> for insertion-order
     * @param capacity
     */
    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return super.size()>capacity;
    }


    public static void main(String[] args) {
        LRUCache<Integer,String> lruCache=new LRUCache<>(3);
        lruCache.put(1,"1");
        lruCache.put(2,"2");
        lruCache.put(3,"3");
        System.out.println(lruCache.keySet());
        lruCache.put(4,"4");
        System.out.println(lruCache.keySet());
        lruCache.put(5,"5");
        System.out.println(lruCache.keySet());
    }
}
