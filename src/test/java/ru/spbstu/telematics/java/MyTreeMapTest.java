package ru.spbstu.telematics.java;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import java.util.*;

public class MyTreeMapTest {

    static int numberTest = 10000;
    static int size = 25;
    static int mod = 150;
    static final Random random = new Random(2);

    String value = new String("Const Value for put without adding new entry");

    static Map<Double,String> treeMap;
    static Map<Double,String> myTreeMap;

    static void setUp(int size) {
        treeMap = new TreeMap<>();
        myTreeMap = new MyTreeMap<>();

        for (int i = 0; i < size; i++ ) {
            myTreeMap.put((double) i, String.valueOf(i*i));
            treeMap.put((double) i, String.valueOf(i*i));
        }
    }

    void checkEquals(Map<?,?> left, Map<?,?> right) {
        if (left == null && right == null)
            return;

        assertEquals(left.isEmpty(),right.isEmpty());
        assertEquals(left.size(),right.size());


        Iterator<?> KeyIterator1 = left.keySet().iterator();
        Iterator<?> KeyIterator2 = right.keySet().iterator();

        /*
        System.out.println("\n\n\n");
        while (KeyIterator1.hasNext() || KeyIterator2.hasNext()) {
            Object o1;
            Object o2;
            if (KeyIterator1.hasNext() && KeyIterator2.hasNext()) {
                o1 = KeyIterator1.next();
                o2 = KeyIterator2.next();
                System.out.println(o1.toString() + ": " + left.get(o1) + "   |   " + o2.toString() + " : " + right.get(o2));
            }
            else if (KeyIterator1.hasNext()) {
                o1 = KeyIterator1.next();
                System.out.println(o1.toString() + ": " + left.get(o1));
            }
            else {
                o2 = KeyIterator2.next();
                System.out.println(o2.toString() + ": " + right.get(o2));
            }
        }
        */
        KeyIterator1 = left.keySet().iterator();
        KeyIterator2 = right.keySet().iterator();

        while (KeyIterator1.hasNext() && KeyIterator2.hasNext()) {
            Object o1 = KeyIterator1.next();
            Object o2 = KeyIterator2.next();
            assertEquals(o1,o2);
            assertEquals(left.get(o1),right.get(o2));
        }
        if (KeyIterator1.hasNext() != KeyIterator2.hasNext())
            fail();

        Iterator<?> ValueIterator1 = left.values().iterator();
        Iterator<?> ValueIterator2 = right.values().iterator();

        while (ValueIterator1.hasNext() && ValueIterator2.hasNext()) {
            Object o1 = ValueIterator1.next();
            Object o2 = ValueIterator2.next();
            assertEquals(o1,o2);
            assertEquals(left.containsValue(o1),right.containsValue(o2));
        }
        if (ValueIterator1.hasNext() != ValueIterator2.hasNext())
            fail();

        Iterator<?> EntryIterator1 = left.entrySet().iterator();
        Iterator<?> EntryIterator2 = right.entrySet().iterator();

        while (EntryIterator1.hasNext() && EntryIterator2.hasNext()) {
            Object o1 = EntryIterator1.next();
            Object o2 = EntryIterator2.next();
            assertEquals(o1,o2);
        }
        if (EntryIterator1.hasNext() != EntryIterator2.hasNext())
            fail();
    }

    @Test
    public void TestRandomModification() {
        setUp(size);
        checkEquals(treeMap,myTreeMap);

        /* 10000 random operation on TreeMap and MyTreemap
        *  50% for put
        *  50% for remove
        * */

        for (int i = 0; i < numberTest; i++){
            int r  = random.nextInt(mod);
            boolean add = random.nextBoolean();
            boolean constValue = random.nextBoolean();
            if (add) {
                if (constValue) {
                    assertEquals(treeMap.put((double)r,value),
                            myTreeMap.put((double)r,value));
                }
                else {
                    //System.out.println("Add K: " + r + "   V: " + String.valueOf(r*r*r)); debug
                    assertEquals(treeMap.put((double)r,String.valueOf(r*r*r)),
                            myTreeMap.put((double)r,String.valueOf(r*r*r)));
                }
            }
            else {
                //System.out.println("-Del K: " + r); debug
                assertEquals(treeMap.remove((double)r),
                myTreeMap.remove((double)r));
            }
        }
        checkEquals(treeMap,myTreeMap);
    }

    @Test
    public void TestConcurrentModification() {
        setUp(size);
        boolean t1, t2;
        checkEquals(treeMap,myTreeMap);

        try {
            for (Double i : treeMap.keySet()) {
                treeMap.remove(i);
            }
            t1 = false; //ConcurrentModificationException didn't happen
        } catch (ConcurrentModificationException ex) {
            t1 = true; //ConcurrentModificationException happened
        }

        try {
            for (Double i : myTreeMap.keySet()) {
                myTreeMap.remove(i);
            }
            t2 = false; //ConcurrentModificationException didn't happen
        } catch (ConcurrentModificationException ex) {
            t2 = true; //ConcurrentModificationException happened
        }
        assertEquals(t1,t2);

        setUp(size);
        try {
            for (Double i : treeMap.keySet()) {
                treeMap.put((double)mod,value);
            }
            t1 = false; //ConcurrentModificationException didn't happen
        } catch (ConcurrentModificationException ex) {
            t1 = true; //ConcurrentModificationException happened
        }

        try {
            for (Double i : myTreeMap.keySet()) {
                myTreeMap.put((double)mod,value);
            }
            t2 = false; //ConcurrentModificationException didn't happen
        } catch (ConcurrentModificationException ex) {
            t2 = true; //ConcurrentModificationException happened
        }
        assertEquals(t1,t2);


        setUp(size);
        try {
            for (String i : treeMap.values()) {
                treeMap.remove((double)0);
            }
            t1 = false; //ConcurrentModificationException didn't happen
        } catch (ConcurrentModificationException ex) {
            t1 = true; //ConcurrentModificationException happened
        }

        try {
            for (String i : myTreeMap.values()) {
                myTreeMap.remove((double)0);
            }
            t2 = false; //ConcurrentModificationException didn't happen
        } catch (ConcurrentModificationException ex) {
            t2 = true; //ConcurrentModificationException happened
        }
        assertEquals(t1,t2);

        setUp(size);
        try {
            for (String i : treeMap.values()) {
                treeMap.put((double)mod,i);
            }
            t1 = false; //ConcurrentModificationException didn't happen
        } catch (ConcurrentModificationException ex) {
            t1 = true; //ConcurrentModificationException happened
        }

        try {
            for (String i : myTreeMap.values()) {
                myTreeMap.put((double)mod,i);
            }
            t2 = false; //ConcurrentModificationException didn't happen
        } catch (ConcurrentModificationException ex) {
            t2 = true; //ConcurrentModificationException happened
        }
        assertEquals(t1,t2);
    }

    @Test
    public void TestNoSuchElementException() {
        setUp(size);
        boolean t1, t2;
        checkEquals(treeMap,myTreeMap);
        Iterator<?> it1 = treeMap.keySet().iterator();
        Iterator<?> it2 = myTreeMap.keySet().iterator();

        t1 = false;
        try {
            while (true) {
                it1.next();
            }
        } catch (NoSuchElementException ex) {
            t1 = true;
        }

        t2 = false;
        try {
            while (true) {
                it2.next(); //i know that it will be interrupted
            }
        } catch (NoSuchElementException ex) {
            t2 = true;
        }
        assertEquals(t1,t2);
    }

    @Test
    public void TestContainsAndGet() {
        setUp(size);

        for (Double i : treeMap.keySet()) {
            assertTrue(myTreeMap.containsKey(i));
            assertEquals(treeMap.get(i),myTreeMap.get(i));
        }

        for (Double i : myTreeMap.keySet()) {
            assertTrue(treeMap.containsKey(i));
            assertEquals(myTreeMap.get(i),treeMap.get(i));
        }

        for (String i : treeMap.values()) {
            assertTrue(myTreeMap.containsValue(i));
        }

        for (String i : myTreeMap.values()) {
            assertTrue(treeMap.containsValue(i));
        }

        for (double i = 0; i < size + 20; i++) {
            assertEquals(treeMap.get(i),myTreeMap.get(i));  //in containers only 0..size
        }
    }

    @Test
    public void TestRandomModificationIteratorOfSets() {

        setUp(size);
        checkEquals(treeMap,myTreeMap);
        Iterator<?> it1 = treeMap.keySet().iterator();
        Iterator<?> it2 = myTreeMap.keySet().iterator();

        while (it1.hasNext()) {
            boolean remove = random.nextBoolean();
            it1.next();
            it2.next();
            if (remove) {
                it1.remove();
                it2.remove();
            }
        }
        checkEquals(treeMap,myTreeMap);

        setUp(size*4);
        it1 = treeMap.values().iterator();
        it2 = myTreeMap.values().iterator();

        while (it1.hasNext() || it2.hasNext()) {
            boolean remove = random.nextBoolean();
            it1.next();
            it2.next();
            if (remove) {
                it1.remove();
                it2.remove();
            }
        }
        checkEquals(treeMap,myTreeMap);

        setUp(size*4);
        it1 = treeMap.entrySet().iterator();
        it2 = myTreeMap.entrySet().iterator();

        while (it1.hasNext() || it2.hasNext()) {
            boolean remove = random.nextBoolean();
            it1.next();
            it2.next();
            if (remove) {
                it1.remove();
                it2.remove();
            }
        }
        checkEquals(treeMap,myTreeMap);

    }
}
