package ru.spbstu.telematics.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class Matrix {
    private double [][] matrix;

    Matrix (int stringsSize, int columnsSize) {
        matrix = new double[stringsSize][columnsSize];
    }

    public Matrix (String filePath) {
        int stringsSize = 0;
        int columnsSize = 0;

        try (Scanner Walk = new Scanner(new FileReader(filePath))) {
            while (Walk.hasNextLine() && Walk.hasNextDouble()){

                double[][] tmp = new double[++stringsSize][];
                tmp = Arrays.copyOf(matrix,matrix.length);
                matrix = tmp;
                matrix[stringsSize - 1] = stringToArray(Walk.nextLine());
                if (columnsSize == 0) {
                    columnsSize = matrix[stringsSize - 1].length;
                }
                else {
                    if (columnsSize != matrix[stringsSize - 1].length) {
                        throw new RuntimeException();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private double[] stringToArray(String str) {
        String[] mas = str.split(" ");
        double[] res = new double[mas.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = Double.parseDouble(mas[i]);
        }
        return res;
    }

    public Matrix multiplication(Matrix other) {
        if (this.matrix[0].length != other.matrix.length) {
            throw new RuntimeException();
        }
        Matrix res = new Matrix(this.matrix.length, other.matrix[0].length);
        for(int i = 0; i < res.matrix.length; i++) {
            for (int j = 0; j < res.matrix[0].length; j++) {
                int Sum = 0;
                for (int k = 0; k < res.matrix[0].length; k++) {
                    Sum += this.matrix[i][k] * other.matrix[k][j];
                }
                res.matrix[i][j] = Sum;
            }
        }
        return res;
    }

}
