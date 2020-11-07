package ru.spbstu.telematics.java;

/*
Program for multiplication NumberOfMatrix=2 matrixces.
*/

import java.io.FileNotFoundException;

public class App
{
    static int NumberOfMatrix = 2; //must be >= 1

    public static int main( String[] args ) {
        if(args.length == NumberOfMatrix) {

            Matrix result;
            Matrix[] Matrixs = new Matrix[NumberOfMatrix];
            try {

                for (int countOfMatrix = 0; countOfMatrix < NumberOfMatrix; countOfMatrix++){
                    Matrixs[countOfMatrix] = new Matrix(args[countOfMatrix]);
                    System.out.println(Matrixs[countOfMatrix].toString());
                    if (countOfMatrix + 1 != NumberOfMatrix)
                        System.out.println("*");
                }

                result = Matrixs[0];

                for (int countOfMatrix = 1; countOfMatrix < NumberOfMatrix; countOfMatrix++){
                        result = result.multiplication(Matrixs[countOfMatrix]);
                }

                System.out.println("==\n" + result.toString());
                return 0;

            }
            catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
                return 2;
            }
            catch (FileNotFoundException ex) {
                System.out.println(ex.getLocalizedMessage());
                return 3;
            }
            catch (IllegalStructureMatrixException ex) {
                System.out.println(ex.getMessage());
                return 4;
            }
            catch (MatrixNotJoint ex) {
                System.out.println(ex.getMessage());
                return 5;
            }
            catch (NullPointerException ex) {
                System.out.println(ex.getMessage());
                return 6;
            }

        } else {
            System.out.println("Require " +  NumberOfMatrix +" arguments. There are " + args.length);
            return 1;
        }
    }
}
