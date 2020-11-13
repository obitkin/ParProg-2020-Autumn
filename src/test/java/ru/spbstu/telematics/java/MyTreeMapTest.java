package ru.spbstu.telematics.java;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import java.util.Random;

class MyTreeMapTest {

    static int size = 50;
    static int max = 100;
    static final Random random = new Random();

    static TreeMap<String,Double> treeMap;
    static MyTreeMap<String,Double> myTreeMap;


    @BeforeAll
    static void setUp() {
        treeMap = new TreeMap<>();
        myTreeMap = new MyTreeMap<>();
        for (int i = 0; i < size; i++ ) {
            treeMap.put(String.valueOf(random.nextInt(max)), (double) i);
            myTreeMap.put(String.valueOf(random.nextInt(max)), (double) i);
        }
    }

    @Test
    void Test() {
        //assertEquals(treeMap.size(),myTreeMap.size());
    }


}
