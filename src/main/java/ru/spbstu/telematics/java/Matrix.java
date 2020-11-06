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

        int rowsSize = 0;
        int columnsSize = 0;
        double[][] matrixTmp = null;

        Scanner Scan = new Scanner(new FileReader(filePath));
        while (Scan.hasNextLine() && Scan.hasNextDouble()){

            double[][] tmp = new double[++rowsSize][];
            tmp = Arrays.copyOf(matrixTmp,matrixTmp.length);
            matrixTmp = tmp;

            try {
                matrixTmp[rowsSize - 1] = stringToArrayOfDouble(Scan.nextLine());
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("Can't read string № " + (rowsSize - 1));
            }

            if (columnsSize == 0) {
                columnsSize = matrixTmp[rowsSize - 1].length;
            }
            else {
                if (columnsSize != matrixTmp[rowsSize - 1].length) {
                    throw new IllegalStructureMatrixException(
                            "columnsSize of string № " +
                            (rowsSize - 1) +
                            " == " +
                            matrixTmp[rowsSize - 1].length +
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
        int realSizeOfOneRow = 0;
        String[] elementsOfOneString = str.split(regex);
        double[] res = new double[elementsOfOneString.length];
        for (int i = 0; i < res.length; i++, realSizeOfOneRow++) {
            if (!elementsOfOneString[i].equals(regex))
                res[realSizeOfOneRow] = Double.parseDouble(elementsOfOneString[i]);
            else {
                realSizeOfOneRow--;
            }
        }
        res = Arrays.copyOf(res,realSizeOfOneRow);
        return res;
    }

    public Matrix multiplication(Matrix other) throws MatrixNotJoint {
        if (this.getColumnSize() != other.getRowSize()) {
            throw new MatrixNotJoint("");
        }
        Matrix res = new Matrix(this.getRowSize(), other.getColumnSize());
        for(int i = 0; i < res.getRowSize(); i++) {
            for (int j = 0; j < res.getColumnSize(); j++) {
                int Sum = 0;
                for (int k = 0; k < res.getColumnSize(); k++) {
                    Sum += this.get(i,k) * other.get(k,j);
                }
                res.set(i,j,Sum);
            }
        }
        return res;
    }

    public double get(int i, int j) {
        return this.matrix[i][j];
    }

    public int getRowSize() {
        return this.matrix.length;
    }

    public int getColumnSize() {
        return this.matrix[0].length;
    }

    public void set(int indexI, int indexJ, double value) {
        this.matrix[indexI][indexJ] = value;
    }

    private class IllegalStructureMatrixException extends Exception {
        public IllegalStructureMatrixException(String s) {
        }
    }

    private class MatrixNotJoint extends Exception {
        public MatrixNotJoint(String s) {
        }
    }
}
