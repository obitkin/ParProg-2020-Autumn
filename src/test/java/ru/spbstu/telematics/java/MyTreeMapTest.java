package ru.spbstu.telematics.java;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import java.util.*;

public class MyTreeMapTest {

    static int size = 10;
    static int Mod = 0;
    static final Random random = new Random();

    static Map<Double,String> treeMap;
    static Map<Double,String> myTreeMap;


    static void setUp() {
        treeMap = new TreeMap<>();
        myTreeMap = new MyTreeMap<>();
        for (int i = 0; i < size; i++ ) {
            myTreeMap.put((double) i, String.valueOf(i*i));
            treeMap.put((double) i,String.valueOf(i*i));
        }
    }

    void CheckEquals(Map<?,?> left, Map<?,?> right) {

        assertEquals(left.isEmpty(),right.isEmpty());
        assertEquals(left.size(),right.size());

        Iterator<?> it1 = left.keySet().iterator();
        Iterator<?> it2 = right.keySet().iterator();
        System.out.println("\n");
        while (it1.hasNext() && it2.hasNext()) {
            Object o1 = it1.next();
            Object o2 = it2.next();

            assertEquals(o1,o2);
            assertEquals(left.get(o1),left.get(o2));
            System.out.println(o1.toString() + ": " + left.get(o1) + "   |   " + o2.toString() + " : " + left.get(o2));
        }

        if (it1.hasNext() != it2.hasNext())
            fail();
    }

    @Test
    public void TestForMap() {

        setUp();

        CheckEquals(treeMap,myTreeMap);

        treeMap.remove(new Double(1));
        myTreeMap.remove(new Double(1));

        for (int i = 0; i < size /2; i++){
            treeMap.remove(new Double(i));
            myTreeMap.remove(new Double(i));
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
