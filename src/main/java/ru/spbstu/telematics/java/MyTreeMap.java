package ru.spbstu.telematics.java;

import java.util.*;

public class MyTreeMap<K,V> implements Map<K,V>{

    private Entry<K,V> root;

    int size = 0;
    int Mod = 0;

    public MyTreeMap() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public void clear(){
        size = 0;
        root = null;
        Mod = 0;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Set<Map.Entry<K,V>> t2 = entrySet();
        for (Map.Entry<K,V> i : t2) {
            if (i.getValue().equals(value))
                return true;
        }
        return false;
    }

    private Entry<K,V> getEntry(Object key) {
        Comparable<? super K> k = (Comparable<? super K>) key;
        Objects.requireNonNull(key);

        Entry<K,V> p = root;
        while (p != null) {
            int cmp = k.compareTo(p.key);
            if (cmp < 0)
                p = p.left;
            else if (cmp > 0)
                p = p.right;
            else
                return p;
        }
        return null;
    }

    @Override
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

    private void insert(K key, V value){
        Comparable<? super K> k = (Comparable<? super K>) key;
        Objects.requireNonNull(key);

        Entry<K,V> t = root;
        while (t != null) {
            int cmp = k.compareTo(t.key);
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

    @Override
    public V get(Object key) {
        Entry<K,V> p = getEntry(key);
        return (p==null ? null : p.value);
    }

    @Override
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

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
            put(e.getKey(), e.getValue());
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
                if (deletion.right != null)
                    deletion.right.father = deletion.father;
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
                if(deletion.left != null)
                    deletion.left.father = deletion.father;
            }
            else {
                deletion.father.right = deletion.left;
                if (deletion.left != null)
                    deletion.left.father = deletion.father;
            }
        }
    }

    /* Множества вершин, ключей, значений, а также методы, возвращающие их */

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public Set<K> keySet() {
        return new KeySet();
    }

    @Override
    public Collection<V> values() {
        return new Values();
    }

    class EntrySet extends AbstractSet<Map.Entry<K,V>> {

        public Iterator<Map.Entry<K,V>> iterator() {
            return new EntryIterator(root);
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

    class KeySet extends AbstractSet<K> {

        public Iterator<K> iterator() {
            return new KeyIterator(root);
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

    class Values extends AbstractCollection<V> {
        public Iterator<V> iterator() {
            return new ValueIterator(root);
        }

        public int size() {
            return MyTreeMap.this.size();
        }

        public boolean contains(Object o) {
            return MyTreeMap.this.containsValue(o);
        }

        public boolean remove(Object o) {
            Set<Map.Entry<K,V>> t2 = entrySet();
            for (Map.Entry<K, V> i : t2) {
                if (i.getValue().equals(o)) {
                    deleteEntry((Entry<K, V>) i);
                    return true;
                }
            }
            return false;
        }

        public void clear() {
            MyTreeMap.this.clear();
        }

    }

    /* Итераторы */

    abstract class PrivateEntryIterator<T> implements Iterator<T> {
        MyTreeMap.Entry<K,V> next;
        MyTreeMap.Entry<K,V> lastReturned;
        int expectedModCount;

        PrivateEntryIterator(MyTreeMap.Entry<K,V> root) {
            Entry<K, V> first = root;
            while (first.left != null) {
                first = first.left;
            }
            expectedModCount = Mod;
            lastReturned = null;
            next = first;
        }

        public final boolean hasNext() {
            return next != null;
        }

        final MyTreeMap.Entry<K,V> nextEntry() {
            MyTreeMap.Entry<K,V> e = next;
            if (e == null)
                throw new NoSuchElementException();
            if (Mod != expectedModCount)
                throw new ConcurrentModificationException();

            if(next.right != null) {
                next = next.right;
                while (next.left != null)
                    next = next.left;
                lastReturned = e;
                return e;
            }

            while(true) {
                if(next.father == null) {
                    next = null;
                    lastReturned = e;
                    return e;
                }
                if(next.father.left == next) {
                    next = next.father;
                    lastReturned = e;
                    return e;
                }
                next = next.father;
            }
        }

        public void remove() {
            if (lastReturned == null)
                throw new IllegalStateException();
            if (Mod != expectedModCount)
                throw new ConcurrentModificationException();
            // deleted entries are replaced by their successors
            if (lastReturned.left != null && lastReturned.right != null)
                next = lastReturned;
            deleteEntry(lastReturned);
            expectedModCount = Mod;
            lastReturned = null;
        }
    }

    final class EntryIterator extends PrivateEntryIterator<Map.Entry<K,V>> {
        EntryIterator(MyTreeMap.Entry<K,V> root) {
            super(root);
        }
        public Map.Entry<K,V> next() {
            return nextEntry();
        }
    }

    final class KeyIterator extends PrivateEntryIterator<K> {
        KeyIterator(MyTreeMap.Entry<K,V> root) {
            super(root);
        }
        public K next() {
            return nextEntry().key;
        }
    }

    final class ValueIterator extends PrivateEntryIterator<V> {
        ValueIterator(MyTreeMap.Entry<K,V> root) {
            super(root);
        }
        public V next() {
            return nextEntry().value;
        }
    }

    /* Класс вершина дерева */

    static class Entry<K, V> implements Map.Entry<K, V>{
        private K key;
        private V value;
        private Entry<K, V> left;
        private Entry<K, V> right;
        private Entry<K, V> father;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return Objects.equals(getKey(), entry.getKey()) &&
                    Objects.equals(getValue(), entry.getValue()) &&
                    Objects.equals(left, entry.left) &&
                    Objects.equals(right, entry.right) &&
                    Objects.equals(father, entry.father);
        }

        @Override
        public int hashCode() {
            return Objects.hash(getKey(), getValue(), left, right, father);
        }
    }

}