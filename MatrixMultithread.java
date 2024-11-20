import java.util.Scanner;

class MatrixOperation implements Runnable {
    private int[][] A;
    private int[][] B;
    private int[][] result;
    private int row;
    private int col;
    private boolean isAddition;

    public MatrixOperation(int[][] A, int[][] B, int[][] result, int row, int col, boolean isAddition) {
        this.A = A;
        this.B = B;
        this.result = result;
        this.row = row;
        this.col = col;
        this.isAddition = isAddition;
    }

    @Override
    public void run() {
        if (isAddition) {
            result[row][col] = A[row][col] + B[row][col];
        } else {
            result[row][col] = 0;
            for (int k = 0; k < A.length; k++) {
                result[row][col] += A[row][k] * B[k][col];
            }
        }
    }
}

public class MatrixMultithread {
    private static final int MAX = 3;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int[][] A = new int[MAX][MAX];
        int[][] B = new int[MAX][MAX];
        int[][] resultAdd = new int[MAX][MAX];
        int[][] resultMul = new int[MAX][MAX];

        // Input matrices A and B
        System.out.println("Enter elements of 3x3 matrix A:");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                A[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter elements of 3x3 matrix B:");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                B[i][j] = sc.nextInt();
            }
        }

        // Perform matrix addition
        Thread[][] addThreads = new Thread[MAX][MAX];
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                addThreads[i][j] = new Thread(new MatrixOperation(A, B, resultAdd, i, j, true));
                addThreads[i][j].start();
            }
        }
        // Join threads for addition
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                addThreads[i][j].join();
            }
        }

        // Print result of matrix addition
        System.out.println("\nResult of matrix addition:");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                System.out.print(resultAdd[i][j] + " ");
            }
            System.out.println();
        }

        // Perform matrix multiplication
        Thread[][] mulThreads = new Thread[MAX][MAX];
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                mulThreads[i][j] = new Thread(new MatrixOperation(A, B, resultMul, i, j, false));
                mulThreads[i][j].start();
            }
        }
        // Join threads for multiplication
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                mulThreads[i][j].join();
            }
        }

        // Print result of matrix multiplication
        System.out.println("\nResult of matrix multiplication:");
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                System.out.print(resultMul[i][j] + " ");
            }
            System.out.println();
        }
    }
}
