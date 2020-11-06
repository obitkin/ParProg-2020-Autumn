package ru.spbstu.telematics.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class Matrix {
    private double [][] matrix;

    private Matrix (int stringsSize, int columnsSize) {
        matrix = new double[stringsSize][columnsSize];
    }

    public Matrix (String filePath) throws NumberFormatException,
            FileNotFoundException, IllegalStructureMatrixException{
        this.matrix = parseMatrix(filePath);
    }

    private double[][] parseMatrix(String filePath) throws NumberFormatException,
            FileNotFoundException, IllegalStructureMatrixException {

        int stringsSize = 0;
        int columnsSize = 0;
        double[][] matrixTmp = null;

        Scanner Scan = new Scanner(new FileReader(filePath));
        while (Scan.hasNextLine() && Scan.hasNextDouble()){

            double[][] tmp = new double[++stringsSize][];
            tmp = Arrays.copyOf(matrixTmp,matrixTmp.length);
            matrixTmp = tmp;

            try {
                matrixTmp[stringsSize - 1] = stringToArrayOfDouble(Scan.nextLine());
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("Can't read string № " + (stringsSize - 1));
            }

            if (columnsSize == 0) {
                columnsSize = matrixTmp[stringsSize - 1].length;
            }
            else {
                if (columnsSize != matrixTmp[stringsSize - 1].length) {
                    throw new IllegalStructureMatrixException(
                            "columnsSize of string № " +
                            (stringsSize - 1) +
                            " == " +
                            matrixTmp[stringsSize - 1].length +
                            " columnsSize of previous string == " +
                            columnsSize);
                }
            }
        }
        Scan.close();
        return matrixTmp;
    }

    private double[] stringToArrayOfDouble(String str) throws NumberFormatException{
        String regex = " ";
        int realSizeOfOneString = 0;
        String[] elementsOfOneString = str.split(regex);
        double[] res = new double[elementsOfOneString.length];
        for (int i = 0; i < res.length; i++, realSizeOfOneString++) {
            if (!elementsOfOneString[i].equals(regex))
                res[realSizeOfOneString] = Double.parseDouble(elementsOfOneString[i]);
            else {
                realSizeOfOneString--;
            }
        }
        res = Arrays.copyOf(res,realSizeOfOneString);
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

    public double Get(int i, int j) {
        return this.matrix[i][j];
    }

    private class IllegalStructureMatrixException extends Exception {
        public IllegalStructureMatrixException(String s) {
        }
    }
}
