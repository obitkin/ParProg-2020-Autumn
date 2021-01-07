package ru.spbstu.telematics.java;

import java.util.concurrent.*;

public class Multiplier {

    static private CopyOnWriteArrayList<Future<?>> result = new CopyOnWriteArrayList<>();

    static private ExecutorService service = Executors.newFixedThreadPool(2);

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

        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                result.add(service.submit(new MultiplyWorker(A,B,i,j,res)));

        int done = 0;
        while (!result.isEmpty()) {
            for (Future<?> f : result) {
                if (f.isDone()) {
                    done++;
                    result.remove(f);
                }
            }
            Thread.yield();
        }

        return res;
    }

    static private class MultiplyWorker implements Runnable {

        double[][] a;
        double[][] b;
        int i;
        int j;
        double[][] res;

        public MultiplyWorker(double[][] a, double[][] b, int i, int j, double[][] res) {
            this.a = a;
            this.b = b;
            this.i = i;
            this.j = j;
            this.res = res;
        }

        @Override
        public void run() {
            double cell = 0;
            for (int length = 0; length < a[i].length; length++) {
                cell += a[i][length] * b[length][j];
            }
            res[i][j] = cell;
        }
    }
}
