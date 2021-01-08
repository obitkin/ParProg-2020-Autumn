package ru.spbstu.telematics.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Multiplier {

    private static final int  MINIMUM_THRESHOLD = 64;
    private static final ExecutorService exec = Executors.newWorkStealingPool();

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

        Future f = exec.submit(new MultiplyTask(A, B, res, 0, 0, 0, 0, 0, 0, A.length));
        try {
            f.get();
            exec.shutdown();
        } catch (Exception e) {

        }
        return res;
    }

    static class MultiplyTask implements Runnable{
        private double[][] a;
        private double[][] b;
        private double[][] c;
        private int a_i, a_j, b_i, b_j, c_i, c_j, size;

        MultiplyTask(double[][] a, double[][] b, double[][] c, int a_i, int a_j, int b_i, int b_j, int c_i, int c_j, int size) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.a_i = a_i;
            this.a_j = a_j;
            this.b_i = b_i;
            this.b_j = b_j;
            this.c_i = c_i;
            this.c_j = c_j;
            this.size = size;
        }

        public void run() {
            //System.out.format("[%d,%d]x[%d,%d](%d)\n",a_i,a_j,b_i,b_j,size);
            if (size <= MINIMUM_THRESHOLD) {
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        for (int k = 0; k < size; ++k) {
                            c[c_i+i][c_j+j] += a[a_i+i][a_j+k] * b[b_i+k][b_j+j];
                        }
                    }
                }
            } else {
                int h = size/2;
                MultiplyTask[] tasks = {
                        new MultiplyTask(a, b, c, a_i, a_j, b_i, b_j, c_i, c_j, h),
                        new MultiplyTask(a, b, c, a_i, a_j+h, b_i+h, b_j, c_i, c_j, h),

                        new MultiplyTask(a, b, c, a_i, a_j, b_i, b_j+h, c_i, c_j+h, h),
                        new MultiplyTask(a, b, c, a_i, a_j+h, b_i+h, b_j+h, c_i, c_j+h, h),

                        new MultiplyTask(a, b, c, a_i+h, a_j, b_i, b_j, c_i+h, c_j, h),
                        new MultiplyTask(a, b, c, a_i+h, a_j+h, b_i+h, b_j, c_i+h, c_j, h),

                        new MultiplyTask(a, b, c, a_i+h, a_j, b_i, b_j+h, c_i+h, c_j+h, h),
                        new MultiplyTask(a, b, c, a_i+h, a_j+h, b_i+h, b_j+h, c_i+h, c_j+h, h)
                };

                List<Future<?>> t = new ArrayList<>();
                Sequentializer[] fs = new Sequentializer[tasks.length/2];

                for (int i = 0; i < tasks.length; i+=2) {
                    fs[i/2] = new Sequentializer(tasks[i], tasks[i+1]);
                    t.add(exec.submit(fs[i/2]));

                }
                try {
                    for (int i = 0; i < fs.length; ++i) {
                        t.get(i).get();
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    static class Sequentializer implements Runnable{
        private MultiplyTask first, second;
        Sequentializer(MultiplyTask first, MultiplyTask second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public void run() {
            first.run();
            second.run();
        }

    }


}
