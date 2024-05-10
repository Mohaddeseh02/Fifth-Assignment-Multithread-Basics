package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        private final int[][] A;
        private final int[][] B;
        private final int[][] C;
        private final int startRow;
        private final int endRow;
        List<List<Integer>> tempMatrixProduct;
        public BlockMultiplier(int[][] A, int[][] B, int[][] C, int startRow, int endRow) {
            this.A = A;
            this.B = B;
            this.C = C;
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        public void run() {
            for (int i = startRow; i < endRow; i++) {
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < B[0].length; j++) {
                    int sum = 0;
                    for (int k = 0; k < A[0].length; k++) {
                        sum += A[i][k] * B[k][j];
                    }
                    row.add(sum);
                    C[i][j] = sum;
                }
                tempMatrixProduct.add(row);
            }
        }
    }

    /*
    Matrix A is of the form p x q
    Matrix B is of the form q x r
    both p and r are even numbers
    */
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B){
        
    }

    public static void main(String[] args) {
        // Test your code here
    }
}
