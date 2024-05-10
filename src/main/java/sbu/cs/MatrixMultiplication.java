package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class

    // کلاس BlockMultiplier برای ضرب ماتریس در چندین ترد استفاده می شود
    public static class BlockMultiplier implements Runnable
    {
        private final int[][] A;
        private final int[][] B;
        private final int[][] C;
        private final int startRow;
        private final int endRow;
        List<List<Integer>> tempMatrixProduct;


        // سازنده کلاس BlockMultiplier
        public BlockMultiplier(int[][] A, int[][] B, int[][] C, int startRow, int endRow) {
            this.A = A;
            this.B = B;
            this.C = C;
            this.startRow = startRow;
            this.endRow = endRow;
            this.tempMatrixProduct = new ArrayList<>(); // Initialize tempMatrixProduct
        }

        // تابع run که در آن ضرب ماتریس انجام می شود
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
        int p = matrix_A.size();
        int q = matrix_A.get(0).size();
        int r = matrix_B.get(0).size();

        int[][] A = matrix_A.stream().map(u -> u.stream().mapToInt(i -> i).toArray()).toArray(int[][]::new);
        int[][] B = matrix_B.stream().map(u -> u.stream().mapToInt(i -> i).toArray()).toArray(int[][]::new);
        int[][] C = new int[p][r];

        // ایجاد چهار ترد برای ضرب ماتریس
        Thread thread1 = new Thread(new BlockMultiplier(A, B, C, 0, p / 4));
        Thread thread2 = new Thread(new BlockMultiplier(A, B, C, p / 4, p/2));
        Thread thread3 = new Thread(new BlockMultiplier(A, B, C, p / 2, 3 * p / 4));
        Thread thread4 = new Thread(new BlockMultiplier(A, B, C, 3 * p / 4, p));

        // شروع ترد ها
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        // انتظار تا ترد ها کار خود را تمام کنند
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // تبدیل ماتریس نهایی به فرمت لیست
        List<List<Integer>> result = new ArrayList<>();
        for (int[] row : C) {
            List<Integer> listRow = new ArrayList<>();
            for (int value : row) {
                listRow.add(value);
            }
            result.add(listRow);
        }

        return result;
    }

    public static void main(String[] args) {
        // Test your code here
    }
}
