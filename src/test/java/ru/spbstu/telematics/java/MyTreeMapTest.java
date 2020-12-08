package ru.spbstu.telematics.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

class MyTreeMapTest {

    static int size = 10;
    static int max = 100;
    static final Random random = new Random();

    static Map<String,Double> treeMap;
    static MyTreeMap<String,Double> myTreeMap;

    @BeforeAll
    static void setUp() {
        treeMap = new TreeMap<>();
        myTreeMap = new MyTreeMap<>();
        for (int i = 0; i < size; i++ ) {
            myTreeMap.put(String.valueOf(i*i), (double) i);
            treeMap.put(String.valueOf(i*i), (double) i);
        }
    }

    @Test
    void TestForMap() {
        assertEquals(treeMap.isEmpty(),myTreeMap.isEmpty());
        assertEquals(treeMap.size(),myTreeMap.size());

        Set<String> k1 = treeMap.keySet();
        Set<String> k2 = myTreeMap.keySet();

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
        assertEquals(treeMap.size(),myTreeMap.size());

        treeMap.clear();
        myTreeMap.clear();


    }


}
