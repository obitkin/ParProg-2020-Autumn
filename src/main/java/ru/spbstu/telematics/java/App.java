package ru.spbstu.telematics.java;

/*
Program for multiplication NumberOfMatrix=2 matrix.
*/

import java.io.FileNotFoundException;

public class App
{
    static int NumberOfMatrix = 2; //must be >= 1

    public static void main( String[] args ) {
        if(args.length == NumberOfMatrix) {

            Matrix result;
            Matrix[] masMatrix = new Matrix[NumberOfMatrix];
            try {

                for (int countOfMatrix = 0; countOfMatrix < NumberOfMatrix; countOfMatrix++){
                    masMatrix[countOfMatrix] = new Matrix(args[countOfMatrix]);
                }

                result = masMatrix[0];

                for (int countOfMatrix = 1; countOfMatrix < NumberOfMatrix; countOfMatrix++){
                    try {
                        result = result.multiplication(masMatrix[countOfMatrix]);
                    } catch (MatrixNotJoint ex) {
                        throw new MatrixNotJoint("Can't multiply result to matrix № " + countOfMatrix);
                    }
                }

            }
            catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
            }
            catch (FileNotFoundException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
            catch (IllegalStructureMatrixException ex) {
                System.out.println(ex.getMessage());
            }
            catch (MatrixNotJoint ex) {
                System.out.println(ex.getMessage());
            }

        } else {
            System.out.println("Require " +  NumberOfMatrix +" arguments. There are " + args.length);
        }

    }
}
