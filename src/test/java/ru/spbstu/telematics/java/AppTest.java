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

    void test(int m, int n, int l) {
        init(m, n, l);
        System.out.println("  " + m + "*" + n + " x " + n + "*" + l);
        init(m, n, l);

        long t = System.currentTimeMillis();
        double[][] resSerial = Multiplier.multiplySerial(A,B);
        System.out.println("Serial execution time   " + (System.currentTimeMillis() - t));

        double[][] resParallel = null;
        for (int k = 2; k <= 8; k *= 2) {
            t = System.currentTimeMillis();
            resParallel = Multiplier.multiplyParallel(A,B,k);
            System.out.println("Threads " + k + "  " + (System.currentTimeMillis() - t));

            check(resSerial,resParallel);
        }
        System.out.println("------------------------------");
    }

    void check(double[][] resSerial, double[][] resParallel) {

        //first check
        for (int j = 0; j < resSerial.length; j++)
            assertArrayEquals(resSerial[j],resParallel[j],0.00001);

        //second check
        if (resSerial.length != resParallel.length) {
            fail();
        }
        else {
            for (int i = 0; i < resSerial.length; i++) {
                if (resSerial[i].length != resParallel[i].length) {
                    fail();
                }
                else {
                    for (int j = 0; j < resSerial[i].length; j++) {
                        if (Double.compare(resSerial[i][j],resParallel[i][j]) != 0)
                            fail();
                    }
                }
            }
        }
    }

    @Test
    public void MatrixMulTest() {
        System.out.println("Square matrices:");
        int m = 1, n = 1, l = 1;

        for (int i = 1; i <= 12; i++) { //from 2^0 to 2^(i-1)
            test(m, n, l);
            m = n = l = l * 2;
        }

        System.out.println("Rectangular matrices:");
        m = 1; n = 10000; l = 1;
        test(m, n, l);
        m = 250; n = 100; l = 150;
        test(m, n, l);
        m = 500; n = 200; l = 300;
        test(m, n, l);
        m = 750; n = 300; l = 500;
        test(m, n, l);
        m = 800; n = 500; l = 1000;
        test(m, n, l);
        m = 900; n = 700; l = 1000;
        test(m, n, l);
        m = 1500; n = 1000; l = 900;
        test(m, n, l);
        m = 2000; n = 1500; l = 1500;
        test(m, n, l);
        m = 2000; n = 2000; l = 2500;
        test(m, n, l);


    }
}
