package ru.spbstu.telematics.java;

import org.junit.*;

import static org.junit.Assert.*;

public class AppTest {

    //Parameters for Field
    static int numberOfBerries = 10000; //Изначальное число ягод на поле
    static int numberOfMaxBerries= 20000; //Максимальное число ягод на поле
    static int boundTakenBerriesInOneTime = 50; //Число ягод >1, которое можно забрать за раз (rand(boundTakenBerriesInOneTime))

    //Parameters for Bushes
    static int timeOutBush = 10; //Период добавления новых ягод
    static int boundProducedBerriesInOneTime = 50; //Число ягод >1, которое которые выросли за раз (rand(boundProducedBerriesInOneTime))

    //Parameters for Neighbors
    static int timeOutNeighbor = 10; //Период сбора ягод
    static int maxNeighborBerries = 6000; //Максимальное количество ягод у Neighbor, при достижении заканчивает

    @Test
    public void ConcurrencyTest() {
        Field field = new Field(boundTakenBerriesInOneTime,numberOfBerries,numberOfMaxBerries);

        Flag flag = new Flag();

        Neighbor N1 = new Neighbor(field,flag,timeOutNeighbor,maxNeighborBerries);
        Neighbor N2 = new Neighbor(field,flag,timeOutNeighbor,maxNeighborBerries);
        Bush B1 = new Bush(field,boundProducedBerriesInOneTime,timeOutBush);

        N1.setNeighbor(N2);
        N2.setNeighbor(N1);

        Thread neighbor1 = new Thread(N1);
        Thread neighbor2 = new Thread(N2);
        Thread bush1 = new Thread(B1);

        neighbor1.start();
        neighbor2.start();
        bush1.start();
        try {
            neighbor1.join();
            neighbor2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(N1.progress()+N2.progress(),numberOfBerries -field.berries+field.producedBerries);
        System.out.println();
        System.out.println("+Neighbor 0: " + N1.progress());
        System.out.println("+Neighbor 1: " + N2.progress());
        System.out.println("==");
        System.out.println("+Number of berries at the beginning: " + numberOfBerries);
        System.out.println("+Number of produced berries: " + field.producedBerries);
        System.out.println("-Number of berries remaining in the field: " + field.berries);
    }
}
