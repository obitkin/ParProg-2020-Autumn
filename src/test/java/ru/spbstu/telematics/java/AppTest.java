package ru.spbstu.telematics.java;

import org.junit.*;
import static org.junit.Assert.*;

public class AppTest {

    static String MatrixEmptyError = ".\\test\\MatrixEmptyError.txt";
    static String MatrixFormatError = ".\\test\\MatrixFormatError.txt";
    static String MatrixOk_3x4 = ".\\test\\MatrixOk_3x4.txt";
    static String MatrixOk_4x2 = ".\\test\\MatrixOk_4x2.txt";
    static String MatrixOk_5x5 = ".\\test\\MatrixOk_5x5.txt";
    static String MatrixStructureError = ".\\test\\MatrixStructureError.txt";
    static String MatrixOkResult_3x2 = ".\\test\\MatrixOkResult_3x2.txt";
    static String MatrixOkResult_5x5 = ".\\test\\MatrixOkResult_5x5.txt";
    static String FileNotFound = "";

    @Test
    public void testApp() {
        assertEquals(0,App.main(new String[]{MatrixOk_3x4,MatrixOk_4x2}));
        assertEquals(0,App.main(new String[]{MatrixOk_5x5,MatrixOk_5x5}));
        assertEquals(0,App.main(new String[]{MatrixOkResult_5x5,MatrixOk_5x5}));
        assertEquals(0,App.main(new String[]{MatrixOkResult_5x5,MatrixOkResult_5x5}));
        assertEquals(0,App.main(new String[]{MatrixOk_5x5,MatrixOkResult_5x5}));

        assertEquals(1,App.main(new String[]{MatrixOk_3x4}));
        assertEquals(1,App.main(new String[]{MatrixOk_5x5,MatrixOk_5x5,MatrixOk_5x5}));
        assertEquals(1,App.main(new String[0]));
        assertEquals(1,App.main(new String[]{}));

        assertEquals(2,App.main(new String[]{MatrixOk_3x4,MatrixFormatError}));
        assertEquals(2,App.main(new String[]{MatrixFormatError,MatrixOk_4x2}));
        assertEquals(2,App.main(new String[]{MatrixFormatError,FileNotFound}));

        assertEquals(3,App.main(new String[]{FileNotFound,MatrixOk_4x2}));
        assertEquals(3,App.main(new String[]{MatrixOk_5x5,FileNotFound}));
        assertEquals(3,App.main(new String[]{FileNotFound,MatrixStructureError}));

        assertEquals(4,App.main(new String[]{MatrixStructureError,FileNotFound}));
        assertEquals(4,App.main(new String[]{MatrixStructureError,MatrixOk_3x4}));
        assertEquals(4,App.main(new String[]{MatrixOkResult_3x2,MatrixStructureError}));

        assertEquals(5,App.main(new String[]{MatrixOk_4x2,MatrixOkResult_3x2}));
        assertEquals(5,App.main(new String[]{MatrixOkResult_3x2,MatrixOk_4x2}));
        assertEquals(5,App.main(new String[]{MatrixOkResult_3x2,MatrixOkResult_3x2}));
        assertEquals(5,App.main(new String[]{MatrixOkResult_3x2,MatrixOk_5x5}));
        assertEquals(5,App.main(new String[]{MatrixOk_4x2,MatrixOk_4x2}));
        assertEquals(5,App.main(new String[]{MatrixOk_5x5,MatrixOk_4x2}));
        assertEquals(5,App.main(new String[]{MatrixOk_5x5,MatrixOkResult_3x2}));
        assertEquals(5,App.main(new String[]{MatrixOk_5x5,MatrixOk_3x4}));

        assertEquals(6,App.main(new String[]{MatrixOk_5x5,MatrixEmptyError}));
        assertEquals(6,App.main(new String[]{MatrixEmptyError,MatrixOk_5x5}));
        assertEquals(6,App.main(new String[]{MatrixEmptyError,MatrixStructureError}));
        assertEquals(6,App.main(new String[]{MatrixEmptyError,MatrixEmptyError}));
    }
}
