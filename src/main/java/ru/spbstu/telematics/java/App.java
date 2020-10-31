package ru.spbstu.telematics.java;

import java.io.FileReader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        if(args.length == 2) {

            try {
                Matrix matrix1 = new Matrix(args[0]);
                Matrix matrix2 = new Matrix(args[1]);
                matrix1.multiplication(matrix2);
            }
            catch (Exception ex) {

            }

        } else {
            System.out.println("Require 2 arguments. There are " + args.length);
        }

    }
}
