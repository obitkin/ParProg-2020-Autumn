package ru.spbstu.telematics.java;

/*
Program for multiplication NumberOfMatrix=2 matrix.
*/

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
                    result = result.multiplication(masMatrix[countOfMatrix]);
                }

            }
            catch (Exception ex) {

            }

        } else {
            System.out.println("Require " +  NumberOfMatrix +" arguments. There are " + args.length);
        }

    }
}
