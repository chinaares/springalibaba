package com.example.interview;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-09-10 14:16:10
 */
public class WeekHashMapDemo {
    public static void main(String[] args) {
        myhashmap();
        System.out.println("====================");
        myweekhashMap();
    }

    private static void myweekhashMap() {

        WeakHashMap<Integer, String> map = new WeakHashMap<>();

        Integer key = new Integer(1);
        String value = "hashMap";

        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);
    }

    private static void myhashmap() {
        HashMap<Integer, String> map = new HashMap<>();

        Integer key = new Integer(1);
        String value = "hashMap";

        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);
    }
}
