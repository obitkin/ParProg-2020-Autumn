package ru.spbstu.telematics.java;

import org.junit.*;
import java.util.Random;

import static org.junit.Assert.*;

public class AppTest {

    private final Random random = new Random();

    double[][] A;
    double[][] B;

    //Result matrix == m * l
    void init(int m, int n, int l) {

        A = new double[m][n];
        B = new double[n][l];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = random.nextDouble();
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < l; j++) {
                B[i][j] = random.nextDouble();
            }
        }
    }

    @Test
    public void ConcurrencyTest() {
        System.out.println("Square matrix:");
        int m = 1, n = 1, l = 1;

        for (int i = 1; i <= 12; i++) {
            m = n = l = l * 2;
            System.out.println("i = " + i + "  " + m + "*" + n + " x " + n + "*" + l);
            init(m, n, l);

            long t = System.currentTimeMillis();
            double[][] res2 = Multiplier.multiplySerial(A,B);
            System.out.println("Serial execution time   " + (System.currentTimeMillis() - t));

            t = System.currentTimeMillis();
            double[][] res1 = Multiplier.multiplyParallel(A,B);
            System.out.println("Parallel execution time " + (System.currentTimeMillis() - t));

            for (int j = 0; j < m; j++)
                assertArrayEquals(res1[j],res2[j],0.00001);

            System.out.println("------------------------");
        }
    }

}
