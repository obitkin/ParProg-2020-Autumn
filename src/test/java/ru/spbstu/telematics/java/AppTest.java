package ru.spbstu.telematics.java;

import org.junit.*;

import static org.junit.Assert.*;

public class AppTest {

    static int numberOfBerriesInField = 10000;
    static int boundOfBerriesInOneTime = 5;

    @Test
    public void ConcurrencyTest() {
        Field F = new Field(numberOfBerriesInField,boundOfBerriesInOneTime);

        Neighbor N1 = new Neighbor(0,F);
        Neighbor N2 = new Neighbor(0,F);

        N1.setNeighbor(N2);
        N2.setNeighbor(N1);

        Thread neighbor1 = new Thread(N1);
        Thread neighbor2 = new Thread(N2);
        neighbor1.start();
        neighbor2.start();


        try {
            neighbor1.join();
            neighbor2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(N1.progress()+N2.progress(),numberOfBerriesInField -F.berries);
    }
}
