package ru.spbstu.telematics.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Multiplier {
    ExecutorService service = Executors.newCachedThreadPool();
    double[][] A;
    double[][] B;
    double[][] res;

    double[][] multiply(double[][] A, double[][] B) {
        if (A[0].length != B.length)
            return null;

        this.A = A;
        this.B = B;

        int row = A.length;
        int column = B[0].length;
        this.res = new double[row][column];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                service.submit(new multiplyWorker(A,B,i,j,res));

        return res;
    }

    class multiplyWorker implements Runnable{

        multiplyWorker(double[][] A, double[][] B, int i, int j, double[][] res) {

        }

        @Override
        public void run() {

        }
    }
}
