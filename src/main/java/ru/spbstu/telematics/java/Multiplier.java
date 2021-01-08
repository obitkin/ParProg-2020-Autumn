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
        int a_h_b_v = A[0].length;
        int a_v = A.length;
        int b_h = B[0].length;

        double[][] res = new double[a_v][b_h];

        Future f = exec.submit(new MultiplyTask(A, B, res,
                                    0, 0,  a_h_b_v, a_v,
                                    0, 0,  b_h, a_h_b_v,
                                    0, 0,  b_h, a_v));
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
        private int a_i, a_j, a_h, a_v, b_i, b_j, b_h, b_v, c_i, c_j, c_h, c_v;

        MultiplyTask(double[][] a, double[][] b, double[][] c,
                     int a_i, int a_j, int a_h, int a_v,
                     int b_i, int b_j, int b_h, int b_v,
                     int c_i, int c_j, int c_h, int c_v) { //a_h == b_v, a_v == c_v, b_h == c_h
            this.a = a;
            this.b = b;
            this.c = c;
            this.a_i = a_i;
            this.a_j = a_j;
            this.a_h = a_h;
            this.a_v = a_v;
            this.b_i = b_i;
            this.b_j = b_j;
            this.b_h = b_h;
            this.b_v = b_v;
            this.c_i = c_i;
            this.c_j = c_j;
            this.c_h = c_h;
            this.c_v = c_v;
        }

        public void run() {
            //System.out.format("[%d,%d]x[%d,%d](%d)\n",a_i,a_j,b_i,b_j,size);
            if (a_h <= MINIMUM_THRESHOLD && a_v <= MINIMUM_THRESHOLD && b_h <= MINIMUM_THRESHOLD) {
                for (int i = 0; i < a_v; ++i) { //rows
                    for (int j = 0; j < b_h; ++j) { //columns
                        for (int k = 0; k < a_h; ++k) {
                            c[c_i+i][c_j+j] += a[a_i+i][a_j+k] * b[b_i+k][b_j+j];
                        }
                    }
                }
            } else {
                int A_left = a_h / 2; //81 columns / 2 == 40 [0..39] 40 columns       1 and 3 in A
                int A_right = a_h - A_left; //81 - 40 == 41 [40..80] 41 columns       2 and 4 in A
                int B_top = A_left;         //1 and 2 in B
                int B_bot = A_right;        //3 and 4 in B
                int A_top = a_v / 2;        //1 and 2 in A
                int A_bot = a_v - A_top;    //3 and 4 in A
                int B_left = b_h / 2;       //1 and 3 in B
                int B_right = b_h - B_left; //2 and 4 in B
                MultiplyTask[] tasks = {
                        // ______A______        ______B______       ______C______
                        // |  1  |  2  |        |  1  |  2  |       |  1  |  2  |
                        // |-----|-----|    *   |-----|-----|  ==   |-----|-----|
                        // |  3  |  4  |        |  3  |  4  |       |  3  |  4  |
                        // -------------        -------------       -------------
                        new MultiplyTask(a, b, c,
                                a_i, a_j, A_left, A_top, //A1
                                b_i, b_j, B_left, B_top, //B1
                                c_i, c_j, B_left, A_top),//C1

                        new MultiplyTask(a, b, c,
                                a_i, a_j + A_left, A_right, A_top, //A2
                                b_i + B_top, b_j, B_left, B_bot, //B3
                                c_i, c_j, B_left, A_top),//C1

                        new MultiplyTask(a, b, c,
                                a_i, a_j, A_left, A_top, //A1
                                b_i, b_j + B_left, B_right, B_top, //B2
                                c_i, c_j + B_left, B_right, A_top),//C2

                        new MultiplyTask(a, b, c,
                                a_i, a_j + A_left, A_right, A_top, //A2
                                b_i + B_top, b_j + B_left, B_right, B_bot, //B4
                                c_i, c_j + B_left, B_right, A_top),//C2

                        new MultiplyTask(a, b, c,
                                a_i + A_top, a_j, A_left, A_bot, //A3
                                b_i, b_j, B_left, B_top, //B1
                                c_i + A_top, c_j, B_left, A_bot),//C3

                        new MultiplyTask(a, b, c,
                                a_i + A_top, a_j + A_left, A_right, A_bot, //A4
                                b_i + B_top, b_j, B_left, B_bot, //B3
                                c_i + A_top, c_j, B_left, A_bot),//C3

                        new MultiplyTask(a, b, c,
                                a_i + A_top, a_j, A_left, A_bot, //A3
                                b_i, b_j + B_left, B_right, B_top, //B2
                                c_i + A_top, c_j + B_left, B_right, A_bot),//C4

                        new MultiplyTask(a, b, c,
                                a_i + A_top, a_j + A_left, A_right, A_bot, //A4
                                b_i + B_top, b_j + B_left, B_right, B_bot, //B4
                                c_i + A_top, c_j + B_left, B_right, A_bot),//C4
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
