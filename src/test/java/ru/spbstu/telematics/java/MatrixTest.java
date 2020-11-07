package ru.spbstu.telematics.java;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class MatrixTest extends TestCase
{

    String MatrixEmptyError = ".\\test\\MatrixEmptyError.txt";
    String MatrixFormatError = ".\\test\\MatrixFormatError.txt";
    String MatrixOk_3x4 = ".\\test\\MatrixOk_3x4.txt";
    String MatrixOk_4x2 = ".\\test\\MatrixOk_4x2.txt";
    String MatrixOk_5x5 = ".\\test\\MatrixOk_5x5.txt";
    String MatrixStructureError = ".\\test\\MatrixStructureError.txt";
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MatrixTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( MatrixTest.class );
    }

    /**
     * Rigourous Test :-)
     */

    public void testToArrayDouble()
    {
        Matrix.stringToArrayOfDouble();
    }

    public void testParseMatrix()
    {
        Matrix.parseMatrix();
    }

    public void testMulMatrix()
    {
        assertTrue( true );
    }
}
