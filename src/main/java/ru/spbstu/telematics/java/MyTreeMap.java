package ru.spbstu.telematics.java;

import java.util.*;

public class MyTreeMap<K,V>{

    private final Comparator<? super K> comparator;

    private Entry<K,V> root;

    int size = 0;
    int Mod = 0;

    public MyTreeMap() {
        this.comparator = null;
        this.root = null;
        this.size = 0;
    }

    public void clear(){
        size = 0;
        root = null;
        Mod++;
    }


    public Set<K> keySet() {
        return new KeySet();
    }

    public Collection<V> values() {
        return new Values();
    }

    public int size(){
        return size;
    }


    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    public boolean containsValue(Object value) {
        Set<Entry<K,V>> t2 = entrySet();
        for (Entry<K,V> i : t2) {
            if (i.getValue().equals(value))
                return true;
        }
        return false;
    }

    private Entry<K,V> getEntry(Object key) {
        Comparable<? super K> k = null;
        if (comparator == null)
            k = (Comparable<? super K>) key;
        Objects.requireNonNull(key);

        Entry<K,V> p = root;
        while (p != null) {
            int cmp = (k == null) ? comparator.compare((K)key,p.key) : k.compareTo(p.key);
            if (cmp < 0)
                p = p.left;
            else if (cmp > 0)
                p = p.right;
            else
                return p;
        }
        return null;
    }

    public Set<Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    public V put(K key, V value) {
        if(root == null) {
            root = new Entry<K,V>(key,value,null);
            size =  1;
            return null;
        }
        Entry<K,V> p = getEntry(key);
        if (p != null) {
            return p.setValue(value);
        }
        insert(key,value);
        size++;
        Mod++;
        return null;
    }

    void insert(K key, V value){
        Comparable<? super K> k = null;
        if (comparator == null)
            k = (Comparable<? super K>) key;
        Objects.requireNonNull(key);
        Entry<K,V> t = root;
        while (t != null) {
            int cmp = (k == null) ? comparator.compare((K)key,t.key) : k.compareTo(t.key);
            if (cmp < 0)
                if (t.left == null) {
                    t.left = new Entry<>(key, value, t);
                    return;
                }
                else
                    t = t.left;
            else if (cmp > 0)
                if (t.right == null) {
                    t.right = new Entry<>(key, value, t);
                    return;
                }
                else
                    t = t.right;
        }
    }

    public V get(Object key) {
        Entry<K,V> p = getEntry(key);
        return (p==null ? null : p.value);
    }

    public V remove(Object key) {
        Entry<K,V> p = getEntry(key);
        if (p == null)
            return null;

        V oldValue = p.value;
        deleteEntry(p);
        size--;
        Mod++;
        return oldValue;
    }

    public void putAll(MyTreeMap<? extends K, ? extends V> m) {
        Set<? extends Entry<? extends K, ? extends V>> t2 = m.entrySet();
        for (MyTreeMap.Entry<? extends K, ? extends V> i : t2) {
           this.put(i.getKey(),i.getValue());
        }
        Mod++;
    }

    private void deleteEntry(Entry<K,V> p) {
        if (p.left == null && p.right == null) {
            if (p == root) {
                root = null;
                return;
            }
            if (p.father.left == p) {
                p.father.left = null;
            }
            else {
                p.father.right = null;
            }
        }
        else if(p.left == null) {
            Entry<K,V> deletion = p.findMinRight();
            K key = deletion.getKey();
            V value = deletion.getValue();
            p.key = key;
            p.setValue(value);
            if (deletion.father.right == deletion) {
                deletion.father.right = deletion.right;
            }
            else {
                deletion.father.left = deletion.right;
                if (deletion.right != null)
                    deletion.right.father = deletion.father;
            }
        }
        else {
            Entry<K,V> deletion = p.findMaxLeft();
            K key = deletion.getKey();
            V value = deletion.getValue();
            p.key = key;
            p.setValue(value);
            if (deletion.father.left == deletion) {
                deletion.father.left = deletion.left;
            }
            else {
                deletion.father.right = deletion.left;
                if (deletion.left != null)
                    deletion.left.father = deletion.father;
            }
        }
    }

    public class TreeIterator implements Iterator<Entry<K,V>>{
        private Entry<K,V> next;
        int expected;

        public TreeIterator(Entry<K,V> root) {
            expected = Mod;
            next = root;
            if(next == null)
                return;

            while (next.left != null)
                next = next.left;
        }

        public boolean hasNext(){
            return next != null;
        }

        public Entry<K,V> next(){
            if(!hasNext()) throw new NoSuchElementException();
            if (expected != Mod) {
                throw new ConcurrentModificationException();
            }
            Entry<K,V> r = next;

            if(next.right != null) {
                next = next.right;
                while (next.left != null)
                    next = next.left;
                return r;
            }

            while(true) {
                if(next.father == null) {
                    next = null;
                    return r;
                }
                if(next.father.left == next) {
                    next = next.father;
                    return r;
                }
                next = next.father;
            }
        }
    }

    class EntrySet extends AbstractSet<Entry<K,V>> {

        public Iterator<Entry<K,V>> iterator() {
            return new TreeIterator(root);
        }

        public boolean contains(Object o) {
            if (!(o instanceof Entry))
                return false;
            Entry<?,?> entry = (Entry<?,?>) o;
            Object value = entry.getValue();
            Entry<K,V> p = getEntry(entry.getKey());
            return p != null && p.getValue().equals(value);
        }

        public boolean remove(Object o) {
            if (!(o instanceof Entry))
                return false;
            Entry<?,?> entry = (Entry<?,?>) o;
            Object value = entry.getValue();
            Entry<K,V> p = getEntry(entry.getKey());
            if (p != null && p.getValue().equals(value)) {
                deleteEntry(p);
                return true;
            }
            return false;
        }

        public int size() {
            return MyTreeMap.this.size();
        }

        public void clear() {
            MyTreeMap.this.clear();
        }

    }

    public class TreeIteratorKey implements Iterator<K>{
        private Entry<K,V> next;
        int expected;
        public TreeIteratorKey(Entry<K,V> root) {
            expected = Mod;
            next = root;
            if(next == null)
                return;

            while (next.left != null)
                next = next.left;
        }

        public boolean hasNext(){
            return next != null;
        }

        public K next(){
            if(!hasNext()) throw new NoSuchElementException();
            if (expected != Mod) {
                throw new ConcurrentModificationException();
            }
            Entry<K,V> r = next;

            if(next.right != null) {
                next = next.right;
                while (next.left != null)
                    next = next.left;
                return r.getKey();
            }

            while(true) {
                if(next.father == null) {
                    next = null;
                    return r.getKey();
                }
                if(next.father.left == next) {
                    next = next.father;
                    return r.getKey();
                }
                next = next.father;
            }
        }
    }

    class KeySet extends AbstractSet<K> {

        public Iterator<K> iterator() {
            return new TreeIteratorKey(root);
        }

        public boolean contains(Object o) {
            if (!(o instanceof Entry))
                return false;
            Entry<?,?> entry = (Entry<?,?>) o;
            Object value = entry.getValue();
            Entry<K,V> p = getEntry(entry.getKey());
            return p != null && p.getValue().equals(value);
        }

        public boolean remove(Object o) {
            if (!(o instanceof Entry))
                return false;
            Entry<?,?> entry = (Entry<?,?>) o;
            Object value = entry.getValue();
            Entry<K,V> p = getEntry(entry.getKey());
            if (p != null && p.getValue().equals(value)) {
                deleteEntry(p);
                return true;
            }
            return false;
        }

        public int size() {
            return MyTreeMap.this.size();
        }

        public void clear() {
            MyTreeMap.this.clear();
        }

    }

    public class TreeIteratorValue implements Iterator<V>{
        private Entry<K,V> next;
        private int expected;
        public TreeIteratorValue(Entry<K,V> root) {
            expected = Mod;
            next = root;
            if(next == null)
                return;

            while (next.left != null)
                next = next.left;
        }

        public boolean hasNext(){
            return next != null;
        }

        public V next(){
            if(!hasNext()) throw new NoSuchElementException();
            if (expected != Mod) {
                throw new ConcurrentModificationException();
            }
            Entry<K,V> r = next;

            if(next.right != null) {
                next = next.right;
                while (next.left != null)
                    next = next.left;
                return r.getValue();
            }

            while(true) {
                if(next.father == null) {
                    next = null;
                    return r.getValue();
                }
                if(next.father.left == next) {
                    next = next.father;
                    return r.getValue();
                }
                next = next.father;
            }
        }
    }

    class Values extends AbstractCollection<V> {
        public Iterator<V> iterator() {
            return new TreeIteratorValue(root);
        }

        public int size() {
            return MyTreeMap.this.size();
        }

        public boolean contains(Object o) {
            return MyTreeMap.this.containsValue(o);
        }

        public boolean remove(Object o) {
            Set<Entry<K,V>> t2 = entrySet();
            for (Entry<K, V> i : t2) {
                if (i.getValue().equals(o)) {
                    deleteEntry(i);
                    return true;
                }
            }
            return false;
        }

        public void clear() {
            MyTreeMap.this.clear();
        }

    }

    static class Entry<K, V>{
        K key;
        V value;
        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> father;

        Entry(K key,V value, Entry<K,V> left, Entry<K,V> right, Entry<K,V> father){
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        Entry(K key,V value, Entry<K, V> father){
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.father = father;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public Entry<K, V> findMaxLeft(){
            Entry<K, V> res = this.left;
            while (res.right != null)
                res = res.right;
            return res;
        }

        public Entry<K, V> findMinRight(){
            Entry<K, V> res = this.right;
            while (res.left != null)
                res = res.left;
            return res;
        }

        @Override
        public String toString() {
            return  key +
                    "=" + value;
        }
    }

}