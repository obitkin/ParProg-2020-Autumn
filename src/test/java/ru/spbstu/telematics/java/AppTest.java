package ru.spbstu.telematics.java;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

import java.util.*;

public class AppTest {

    static int numberOfBerriesInField = 10000;

    public static void main(String[] args) {
        System.out.println("main");

        Field F = new Field(numberOfBerriesInField);

        Neighbor N1 = new Neighbor(0,F);
        Neighbor N2 = new Neighbor(0,F);

        N1.setNeighbor(N2);
        N2.setNeighbor(N1);

        new Thread(N1).start();
        new Thread(N2).start();
    }

    @Test
    public void CuncorrencyTest() {

    }
}
