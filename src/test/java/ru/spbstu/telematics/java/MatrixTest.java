package ru.spbstu.telematics.java;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;


import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Unit test for simple App.
 */
@RunWith(Enclosed.class)
public class MatrixTest {

    static String MatrixEmptyError = ".\\test\\MatrixEmptyError.txt";
    static String MatrixFormatError = ".\\test\\MatrixFormatError.txt";
    static String MatrixOk_3x4 = ".\\test\\MatrixOk_3x4.txt";
    static String MatrixOk_4x2 = ".\\test\\MatrixOk_4x2.txt";
    static String MatrixOk_5x5 = ".\\test\\MatrixOk_5x5.txt";
    static String MatrixStructureError = ".\\test\\MatrixStructureError.txt";

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
                    { "0xf 0x2 0x3", null , new NumberFormatException()} ,
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
                    assertTrue(false);
                } catch (NumberFormatException ex) {
                    assertTrue(true);
                }
                return;
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
                            {4, 54, 454, 43, 323},
                            {32, 3, 23, 34, -1},
                            {4, 43, 3,0, 0},
                            {3232, 3, 4, 3, -1},
                            {3, 4, -1, 0, -43}} , null} ,
                    { MatrixStructureError, null , new IllegalStructureMatrixException("")} ,
                    { MatrixFormatError, null , new NumberFormatException()} ,
                    { MatrixEmptyError, null , null} ,
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
                    assertTrue(false); //if we waiting for result, but get exception
                }
            } else {
                try {
                    Matrix.parseMatrix(inputPath);
                    assertTrue(false);

                } catch (Exception ex) {
                    if (ex.getClass().equals(exceptionExcepted.getClass())) {
                        assertTrue(true);
                    } else {
                        assertTrue(false);
                    }
                }
            }

        }
    }

    /*
    @RunWith(Parameterized.class)
    public class MulMatrixTest {

        @Test
        public void testMulMatrix()
        {
            assertTrue( true );
        }
    }
    */

}
