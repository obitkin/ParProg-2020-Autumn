package ru.spbstu.telematics.java;

import org.junit.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class AppTest {

    private final Random random = new Random();

    double[][] A;
    double[][] B;

    int row = 100;
    int column = 100;

    @Test
    public void ConcurrencyTest() {
        A = new double[row][column];
        B = new double[row][column];

        //int g = Runtime.getRuntime().availableProcessors();
        //System.out.println(g);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                A[i][j] = random.nextDouble();
                B[i][j] = random.nextDouble();
            }
        }
        long t = System.currentTimeMillis();
        double[][] res1 = Multiplier.multiplyParallel(A,B);
        System.out.println("Parallel execution time " + (System.currentTimeMillis() - t));



        t = System.currentTimeMillis();
        double[][] res2 = Multiplier.multiplySerial(A,B);
        System.out.println("Serial execution time   " + (System.currentTimeMillis() - t));

        //System.out.println(Arrays.deepToString(res1));
        //System.out.println(Arrays.deepToString(res2));
        for (int i = 0; i < row; i++)
            assertArrayEquals(res1[i],res2[i],0.001);
    }

}
