package com.example.interview;

import java.util.HashMap;
import java.util.Map;

class LRUCacheDemo {

    //构建承载体node
    class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node() {
            this.prev = this.next = null;
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.prev = this.next = null;
        }
    }

    //构建双向队列
    class DoubleLinkedList<K, V> {
        Node<K, V> head;
        Node<K, V> tail;

        public DoubleLinkedList() {
            head = new Node<>();
            tail = new Node<>();
            head.next = tail;
            tail.prev = head;
        }

        //添加头节点
        public void addHead(Node<K, V> node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        //删除节点
        public void removeNode(Node<K, V> node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            node.prev = null;
            node.next = null;
        }

        //获取最后一个节点
        public Node getLast() {
            return tail.prev;
        }
    }

    private int cacheSize;
    Map<Object, Node<Object, Object>> map;
    DoubleLinkedList<Object, Object> doubleLinkedList;

    public LRUCacheDemo(int cacheSize) {
        this.cacheSize = cacheSize;
        this.map = new HashMap<>();
        this.doubleLinkedList = new DoubleLinkedList<>();
    }

    public Object get(Object key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        final Node<Object, Object> node = map.get(key);
        this.doubleLinkedList.removeNode(node);
        this.doubleLinkedList.addHead(node);
        return node.value;
    }

    public void put(Object key, Object value) {
        if (map.containsKey(key)) {
            final Node<Object, Object> node = map.get(key);
            node.value = value;
            map.put(key, node);
            this.doubleLinkedList.removeNode(node);
            this.doubleLinkedList.addHead(node);
        }else{
            if(map.size()==this.cacheSize){
                final Node last = this.doubleLinkedList.getLast();
                map.remove(last.key);
                doubleLinkedList.removeNode(last);
            }
            //新增
            Node<Object,Object> newNode=new Node<>(key,value);
            map.put(key, newNode);
            this.doubleLinkedList.addHead(newNode);
        }
    }

    public static void main(String[] args) {
        LRUCacheDemo lruCacheDemo=new LRUCacheDemo(3);
        lruCacheDemo.put(1,"1");
        lruCacheDemo.put(2,"2");
        lruCacheDemo.put(3,"3");
        System.out.println(lruCacheDemo.map.keySet());
        lruCacheDemo.put(4,"4");
        System.out.println(lruCacheDemo.map.keySet());
        lruCacheDemo.put(3,"3");
        System.out.println(lruCacheDemo.map.keySet());
        lruCacheDemo.put(5,"5");
        System.out.println(lruCacheDemo.map.keySet());
        lruCacheDemo.get(3);
        System.out.println(lruCacheDemo.map.keySet());
    }
}
