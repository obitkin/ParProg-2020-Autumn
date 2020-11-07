package ru.spbstu.telematics.java;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Enclosed.class)
public class MatrixTest {

    static String MatrixEmptyError = ".\\test\\MatrixEmptyError.txt";
    static String MatrixFormatError = ".\\test\\MatrixFormatError.txt";
    static String MatrixOk_3x4 = ".\\test\\MatrixOk_3x4.txt";
    static String MatrixOk_4x2 = ".\\test\\MatrixOk_4x2.txt";
    static String MatrixOk_5x5 = ".\\test\\MatrixOk_5x5.txt";
    static String MatrixStructureError = ".\\test\\MatrixStructureError.txt";
    static String MatrixOkResult_3x2 = ".\\test\\MatrixOkResult_3x2.txt";
    static String MatrixOkResult_5x5 = ".\\test\\MatrixOkResult_5x5.txt";
    static String FileNotFound = "";

    @RunWith(Parameterized.class)
    public static class StringToArrayDoubleTest {

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    { "1 2 3", new double[] {1,2,3} , null} ,
                    { "1 2.3 -33", new double[] {1,2.3,-33} , null} ,
                    { "1    2     -000", new double[] {1,2,0} , null} ,
                    { "0    .2     .0", new double[] {0,0.2,0} , null} ,
                    { "..1 2 3", null , new NumberFormatException()} ,
                    { "1ds 2 3sdf", null , new NumberFormatException()} ,
                    { "0xf 0x2 0x3", null , new NumberFormatException()}
            });
        }

        private String input;
        private double[] resultExpected;
        private NumberFormatException exceptionExpected;

        public StringToArrayDoubleTest(String input, double[] resultExpected, NumberFormatException exceptionExpected) {
            this.input = input;
            this.resultExpected = resultExpected;
            this.exceptionExpected = exceptionExpected;
        }

        @Test
        public void testToArrayDouble() {
            if(exceptionExpected == null) {
                assertArrayEquals(resultExpected, Matrix.stringToArrayOfDouble(input),0.001);
            }
            else {
                try {
                    Matrix.stringToArrayOfDouble(input);
                    fail();
                } catch (NumberFormatException ex) {
                    assertTrue(true);
                }
            }
        }
    }

    @RunWith(Parameterized.class)
    public static class ParseMatrixTest {

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    { MatrixOk_3x4, new double[][] {
                            {4, 5, 2, -1},
                            {10, 2, 2, 0},
                            {-1, 3, 4, 5}} , null} ,
                    { MatrixOk_4x2, new double[][] {
                            {4, 5},
                            {-1, 0},
                            {3, 4},
                            {2, 3}} , null} ,
                    { MatrixOk_5x5, new double[][] {
                            {4,   -4,    1,	 -1,   3},
                            {6,   3,     5,	 -4,   -1},
                            {4,   -3,    3,   0,    0},
                            {3,    3,    4,   3,   -1},
                            {3,   4,     -1,  0,    0}} , null} ,
                    { MatrixStructureError, null , new IllegalStructureMatrixException()} ,
                    { MatrixFormatError, null , new NumberFormatException()} ,
                    { MatrixEmptyError, null , new NullPointerException()} ,
                    { FileNotFound, null , new FileNotFoundException()}
            });
        }

        private String inputPath;
        private double[][] resultExcpected;
        private Exception exceptionExcepted;

        public ParseMatrixTest(String inputPath, double[][] resultExcpected, Exception exceptionExcepted) {
            this.inputPath = inputPath;
            this.resultExcpected = resultExcpected;
            this.exceptionExcepted = exceptionExcepted;
        }

        @Test
        public void testParseMatrix()
        {
            if(exceptionExcepted == null) {
                try {
                    assertTrue(Arrays.deepEquals(resultExcpected,Matrix.parseMatrix(inputPath)));
                } catch (Exception ex) {
                    fail(); //if we waiting for result, but get exception
                }
            } else {
                try {
                    Matrix.parseMatrix(inputPath);
                    fail();
                } catch (Exception ex) {
                    if (ex.getClass().equals(exceptionExcepted.getClass())) {
                        assertTrue(true);
                    } else {
                        fail();
                    }
                }
            }

        }
    }


    @RunWith(Parameterized.class)
    public static class MulMatrixTest {

        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            try {
                return Arrays.asList(new Object[][] {
                        { new Matrix(MatrixOk_3x4), new Matrix(MatrixOk_4x2) , new Matrix(MatrixOkResult_3x2) , null} ,
                        { new Matrix(MatrixOk_4x2), new Matrix(MatrixOk_3x4) , null , new MatrixNotJoint()} ,
                        { new Matrix(MatrixOk_4x2), new Matrix(MatrixOk_5x5) , null , new MatrixNotJoint()} ,
                        { new Matrix(MatrixOk_3x4), new Matrix(MatrixOk_5x5) , null , new MatrixNotJoint()} ,
                        { new Matrix(MatrixOk_3x4), new Matrix(MatrixOk_3x4) , null , new MatrixNotJoint()} ,
                        { new Matrix(MatrixOk_5x5), new Matrix(MatrixOk_5x5) , new Matrix(MatrixOkResult_5x5) , null}
                });
            } catch (Exception ex) {
                return null; //never happen, just because Matrix throw Exception
            }
        }

        private Matrix leftMatrix;
        private Matrix rightMatrix;
        private Matrix resultExpected;
        private Exception exceptionExpected;

        public MulMatrixTest(Matrix leftMatrix, Matrix rightMatrix, Matrix resultExpected, Exception exceptionExpected) {
            this.leftMatrix = leftMatrix;
            this.rightMatrix = rightMatrix;
            this.resultExpected = resultExpected;
            this.exceptionExpected = exceptionExpected;
        }

        @Test
        public void testMulMatrix() {
            if (exceptionExpected == null) {
                try {
                    assertEquals(resultExpected,leftMatrix.multiplication(rightMatrix));
                } catch (Exception ex) {
                    fail();
                }
            } else {
                try {
                    assertEquals(resultExpected,leftMatrix.multiplication(rightMatrix));
                } catch (MatrixNotJoint ex) {
                    if (ex.getClass().equals(exceptionExpected.getClass())) {
                        assertTrue(true);
                    } else {
                        fail();
                    }
                }
            }
        }
    }
}

