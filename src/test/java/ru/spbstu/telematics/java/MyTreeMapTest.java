package ru.spbstu.telematics.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

class MyTreeMapTest {

    static int size = 3;
    static int max = 100;
    static final Random random = new Random();

    static Map<String,Double> treeMap;
    static Map<String,Double> myTreeMap;

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
        assertEquals(treeMap.size(),myTreeMap.size());
        Set<Map.Entry<String,Double>> t1 = treeMap.entrySet();
        for (Map.Entry<String, Double> i : t1) {
            i.setValue((double)(i.getValue()+6));
        }
        Set<Map.Entry<String,Double>> t2 = myTreeMap.entrySet();
        for (Map.Entry<String, Double> i : t2) {
            i.setValue((double)i.getValue()+3);
        }
    }

    @Test
    void TestForSortedMap() {
        //assertEquals(treeMap.size(),myTreeMap.size());
    }

    @Test
    void TestForNavigableMap() {
        //assertEquals(treeMap.size(),myTreeMap.size());
    }

}
