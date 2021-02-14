package ru.spbstu.telematics.java;

import org.junit.Test;

public class AppTest {

    static String MatrixEmptyError = ".\\test\\MatrixEmptyError.txt"; //Empty file
    static String MatrixFormatError = ".\\test\\MatrixFormatError.txt"; //Error if file contains not number or '.'
    static String MatrixOk_3x4 = ".\\test\\MatrixOk_3x4.txt";
    static String MatrixOk_4x2 = ".\\test\\MatrixOk_4x2.txt";
    static String MatrixOk_5x5 = ".\\test\\MatrixOk_5x5.txt";
    static String MatrixStructureError = ".\\test\\MatrixStructureError.txt"; //Error if size of rows is not equals
    static String MatrixOkResult_3x2 = ".\\test\\MatrixOkResult_3x2.txt"; //Result MatrixOk_3x4*MatrixOk_4x2
    static String MatrixOkResult_5x5 = ".\\test\\MatrixOkResult_5x5.txt"; //Result MatrixOk_5x5*MatrixOk_5x5
    static String FileNotFound = ""; //Non-existent file

    @Test
    public void testApp() { //Check tests step by step cause main doesn't return value

        App.main(new String[]{MatrixOk_3x4,MatrixOk_4x2});
        App.main(new String[]{MatrixOk_5x5,MatrixOk_5x5});
        App.main(new String[]{MatrixOkResult_5x5,MatrixOk_5x5});
        App.main(new String[]{MatrixOkResult_5x5,MatrixOkResult_5x5});
        App.main(new String[]{MatrixOk_5x5,MatrixOkResult_5x5});

        App.main(new String[]{MatrixOk_3x4});
        App.main(new String[]{MatrixOk_5x5,MatrixOk_5x5,MatrixOk_5x5});
        App.main(new String[0]);
        App.main(new String[]{});

        App.main(new String[]{MatrixOk_3x4,MatrixFormatError});
        App.main(new String[]{MatrixFormatError,MatrixOk_4x2});
        App.main(new String[]{MatrixFormatError,FileNotFound});

        App.main(new String[]{FileNotFound,MatrixOk_4x2});
        App.main(new String[]{MatrixOk_5x5,FileNotFound});
        App.main(new String[]{FileNotFound,MatrixStructureError});

        App.main(new String[]{MatrixStructureError,FileNotFound});
        App.main(new String[]{MatrixStructureError,MatrixOk_3x4});
        App.main(new String[]{MatrixOkResult_3x2,MatrixStructureError});

        App.main(new String[]{MatrixOk_4x2,MatrixOkResult_3x2});
        App.main(new String[]{MatrixOkResult_3x2,MatrixOk_4x2});
        App.main(new String[]{MatrixOkResult_3x2,MatrixOkResult_3x2});
        App.main(new String[]{MatrixOkResult_3x2,MatrixOk_5x5});
        App.main(new String[]{MatrixOk_4x2,MatrixOk_4x2});
        App.main(new String[]{MatrixOk_5x5,MatrixOk_4x2});
        App.main(new String[]{MatrixOk_5x5,MatrixOkResult_3x2});
        App.main(new String[]{MatrixOk_5x5,MatrixOk_3x4});

        App.main(new String[]{MatrixOk_5x5,MatrixEmptyError});
        App.main(new String[]{MatrixEmptyError,MatrixOk_5x5});
        App.main(new String[]{MatrixEmptyError,MatrixStructureError});
        App.main(new String[]{MatrixEmptyError,MatrixEmptyError});

    }
}
