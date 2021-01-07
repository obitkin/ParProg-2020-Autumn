package ru.spbstu.telematics.java;

import java.util.concurrent.*;

public class Multiplier {

    static private CopyOnWriteArrayList<Future<?>> result = new CopyOnWriteArrayList<>();

    static private ExecutorService service;

    static public double[][] multiplySerial(double[][] A, double[][] B) {
        if (A[0].length != B.length)
            return null;

        int row = A.length;
        int column = B[0].length;
        double[][] res = new double[row][column];

        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                for (int k = 0; k < A[i].length; k++)
                    res[i][j] += A[i][k] * B[k][j];

        return res;
    }

    static public double[][] multiplyParallel(double[][] A, double[][] B) {
        if (A[0].length != B.length)
            return null;

        int row = A.length;
        int column = B[0].length;
        double[][] res = new double[row][column];

        int available = Runtime.getRuntime().availableProcessors()-7;
        System.out.println();
        service = Executors.newFixedThreadPool(available);

        int numberOfRowPerThread = row / available;

        for (int i = 0; i < available; i++) {
            if (i == available - 1)
                result.add(service.submit(new MultiplyWorker(A,B,numberOfRowPerThread*i,row,res)));
            else
                result.add(service.submit(new MultiplyWorker(A,B,numberOfRowPerThread*i,numberOfRowPerThread*(i+1),res)));
        }

        int done = 0;
        while (!result.isEmpty()) {
            for (Future<?> f : result) {
                if (f.isDone()) {
                    done++;
                    result.remove(f);
                }
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    static private class MultiplyWorker implements Runnable {

        double[][] a;
        double[][] b;
        int i;
        int I;
        double[][] res;

        public MultiplyWorker(double[][] a, double[][] b, int i, int I, double[][] res) {
            this.a = a;
            this.b = b;
            this.i = i;
            this.I = I;
            this.res = res;
        }

        @Override
        public void run() {

            double cell = 0;
            int rowLengthA = a[i].length;
            int rowLengthB = b[0].length;

            for (int row = i; row < I; row++) {
                for (int column = 0; column < rowLengthB; column++) {
                    cell = 0;
                    for (int length = 0; length < rowLengthA; length++) {
                        cell += a[row][length] * b[length][column];
                    }
                    res[row][column] = cell;
                }
            }
        }
    }
}
