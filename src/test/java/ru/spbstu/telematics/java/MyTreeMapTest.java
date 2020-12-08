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
        for (int i = 0; i < myTreeMap.size() / 2; i++ ) {
            myTreeMap.remove(String.valueOf(i*i));
            treeMap.remove(String.valueOf(i*i));
        }

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

        myTreeMap.remove("4");
        Set<MyTreeMap.Entry<String,Double>> t2 = myTreeMap.entrySet();

        for (MyTreeMap.Entry<String, Double> i : t2) {
            System.out.println(i);

        }

        myTreeMap.put("De",2.0);
    }


}
