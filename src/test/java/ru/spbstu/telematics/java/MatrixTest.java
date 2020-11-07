package ru.spbstu.telematics.java;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;


import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;

/**
 * Unit test for simple App.
 */

public class MatrixTest {

    String MatrixEmptyError = ".\\test\\MatrixEmptyError.txt";
    String MatrixFormatError = ".\\test\\MatrixFormatError.txt";
    String MatrixOk_3x4 = ".\\test\\MatrixOk_3x4.txt";
    String MatrixOk_4x2 = ".\\test\\MatrixOk_4x2.txt";
    String MatrixOk_5x5 = ".\\test\\MatrixOk_5x5.txt";
    String MatrixStructureError = ".\\test\\MatrixStructureError.txt";

    @Before
    public void setUp() throws Exception {

    }

    @RunWith(Parameterized.class)
    public class StringToArrayDoubleTest {

        @Parameterized.Parameters
        public Collection<Object[]> data() {
            return Arrays.asList(new Object[][] {
                    { "fdfd", new double[] {1,2,3} ,null}
            });
        }

        private String input;
        private double[] result;
        NumberFormatException except;

        public StringToArrayDoubleTest(String input, double[] result, NumberFormatException except) {
            this.input = input;
            this.result = result;
            this.except = except;
        }

        @Test
        public void testToArrayDouble() {
            if(except == null) {
                assertEquals(result,Matrix.stringToArrayOfDouble(input));
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
    public class ParseMatrixTest {

        @Test
        public void testParseMatrix()
        {
            try {
                Matrix.parseMatrix(MatrixOk_3x4);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalStructureMatrixException e) {
                e.printStackTrace();
            }
        }
    }

    @RunWith(Parameterized.class)
    public class MulMatrixTest {

        @Test
        public void testMulMatrix()
        {
            assertTrue( true );
        }
    }
}
