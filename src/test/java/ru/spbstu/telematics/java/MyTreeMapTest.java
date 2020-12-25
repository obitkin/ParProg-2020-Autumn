package ru.spbstu.telematics.java;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import java.util.*;

public class MyTreeMapTest {

    static int size = 25;
    static int Mod = 150;
    static final Random random = new Random(2);

    String value = new String("Const Value for put without adding new entry");

    static Map<Double,String> treeMap;
    static Map<Double,String> myTreeMap;

    static void setUp() {
        treeMap = new TreeMap<>();
        myTreeMap = new MyTreeMap<>();

        for (int i = 0; i < size; i++ ) {
            myTreeMap.put((double) i, String.valueOf(i*i));
            treeMap.put((double) i, String.valueOf(i*i));
        }
    }

    void CheckEquals(Map<?,?> left, Map<?,?> right) {
        if (left == null && right == null)
            return;

        assertEquals(left.isEmpty(),right.isEmpty());
        assertEquals(left.size(),right.size());

        Iterator<?> KeyIterator1 = left.keySet().iterator();
        Iterator<?> KeyIterator2 = right.keySet().iterator();

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
    public void TestForMap() {
        setUp();
        CheckEquals(treeMap,myTreeMap);

        System.out.println("\n\n\n");

        /* 10000 random operation on TreeMap and MyTreemap
        *  50% for put
        *  50% for remove
        * */

        for (int i = 0; i < 10000; i++){
            int r  = random.nextInt(250);
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
        CheckEquals(treeMap,myTreeMap);
/*
        Set<Double> k1 = treeMap.keySet();
        Set<Double> k2 = myTreeMap.keySet();

        for (String i : k1) {
            System.out.println(i + " " + treeMap.get(i));
        }
        System.out.println("-------------");
        for (String i : k2) {
            System.out.println(i + " " + myTreeMap.get(i));
        }

        for (int i = 0; i < myTreeMap.size() / 2; i++ ) {
            myTreeMap.remove(String.valueOf(i*i));
            treeMap.remove(String.valueOf(i*i));
        }
        System.out.println("-------------");
        assertEquals(treeMap.containsKey("16"),myTreeMap.containsKey("16"));

        Collection<Double> v1 = treeMap.values();
        Collection<Double> v2 = myTreeMap.values();

        assertEquals(treeMap.size(),myTreeMap.size());

        for (Double i : v1) {
            System.out.println(i);
        }
        System.out.println("---------------");

        for (Double i : v2) {
            System.out.println(i);
        }

        System.out.println("---------------");

        treeMap.entrySet().remove(5.0);
        myTreeMap.entrySet().remove(5.0);

        k1 = treeMap.keySet();
        k2 = myTreeMap.keySet();
        try {
            for (String i : k1){
                System.out.println(i + " " + treeMap.get(i));
                treeMap.remove(i);
            }
        }catch (ConcurrentModificationException ex) {
            System.out.println(ex);
        }

        System.out.println("---------------");
        try {
            for (String i : k2){
                System.out.println(i + " " + myTreeMap.get(i));
                myTreeMap.remove(i);
            }
        }catch (ConcurrentModificationException ex) {
            System.out.println(ex);
        }

        assertEquals(treeMap.size(),myTreeMap.size());

        treeMap.clear();
        myTreeMap.clear();

*/
    }




}
