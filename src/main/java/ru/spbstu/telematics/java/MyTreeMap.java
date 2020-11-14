package ru.spbstu.telematics.java;

import java.util.*;

public class MyTreeMap<K,V>
        extends AbstractMap<K,V>
        implements Map<K,V>{

    private final Comparator<? super K> comparator;

    private MyTreeMap.Entry<K,V> root;

    int size;



    public MyTreeMap() {
        this.comparator = null;
        this.root = null;
        this.size = 0;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
       return binaryTreeTaversalForSet(root);
    }

    private <K,V> Set<Map.Entry<K,V>> binaryTreeTaversalForSet(MyTreeMap.Entry<K,V> node) {
        Set<Map.Entry<K,V>> set = null;
        if (node != null) {
            set = new HashSet<>();
            set.add(node);
            if (node.left != null)
                set.addAll(binaryTreeTaversalForSet(node.left));
            if (node.right != null)
                set.addAll(binaryTreeTaversalForSet(node.right));
        }
        return set;
    }

    @Override
    public V put(K key, V value) {
        if(root == null) {
            root = new Entry<>(key,value);
            size =  1;
            return null;
        }
        if (containsKey(key)) {
            V oldValue = get(key);
            insert(key,value,root);
            return oldValue;
        }
        insert(key,value,root);
        return null;
    }

    Entry<K,V> insert(K key, V value, Entry<K,V> t){
        if(t == null){
            return new Entry<>(key,value);
        }
        int cmp;
        if (comparator != null) {
            cmp = comparator.compare(key,t.key);
        }
        else {
            cmp = ((Comparable<? super K>) key).compareTo(t.key);
        }

        if (cmp < 0) {
            t.left = insert(key,value,t.left);
        }
        else if (cmp > 0) {
            t.right = insert(key,value,t.right);
        }
        else if (cmp == 0) {
            t.value = value;
        }

        t = skew(t);
        t = split(t);
        return t;
    }

    private Entry<K,V> skew(Entry<K,V> t) {

        if (t == null){
            return null;
        }
        else if (t.left == null){
            return t;
        }
        else if (t.left.level == t.level) {
            return new Entry<>(t.left.key, t.left.value,
                    t.left.level, t.left.left,
                    new Entry<>(t.key,t.value,t.level,t.left.right,t.right));
        }
        else
            return t;
    }

    private Entry<K,V> split(Entry<K,V> t) {
        if (t == null) {
            return null;
        }
        else if (t.right == null || t.right.right == null) {
            return t;
        }
        else if(t.level == t.right.right.level) {
            return new Entry<>(t.right.key,t.right.value,t.right.level+1,
                    new Entry<>(t.key,t.value,t.level,t.left, t.right.left), t.right.right);
        }
        else
            return t;
    }


    static final class Entry<K,V> implements Map.Entry<K,V> {
        K key;
        V value;
        Entry<K, V> left;
        Entry<K, V> right;
        int level;

        Entry(K key,V value, int level, Entry left, Entry right){
            this.key = key;
            this.value = value;
            this.level = level;
            this.left = left;
            this.right = right;
        }

        Entry(K key,V value){
            this.key = key;
            this.value = value;
            this.level = 1;
            this.left = null;
            this.right = null;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

}