package fr.sudoku.model;

import java.util.Arrays;
import java.util.Random;

public class Board {

    private static final int N = 9;
    private static final int SRN = 3;

    private static final int K = 30;
    private final int[][] grid = new int[N][N];

    private final boolean[][] isFixed = new boolean[N][N];

    private final Random rnd = new Random();

    public Board() {
        // Initialize the grid with 0
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N ; j++) {
                this.grid[i][j] = 0;
            }
        }
    }


    /** fills randomly the board with numbers from 1 to 9
     * while respecting the rules of the game
     */
    public void initializeBoard() {

        // Fill the diagonal
        for (int i = 0; i<N; i=i+SRN) {
            fillBox(i, i);
        }

        fillRemaining(0, SRN);

        // Remove Randomly K digits to make game
        removeKDigits();

        // Set the fixed cells to true
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N ; j++) {
                this.isFixed[i][j] = this.grid[i][j] != 0;
            }
        }

        System.out.println(this);
    }

    private void removeKDigits()
    {
        int count = K;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N)-1;

            // extract coordinates i  and j
            int i = (cellId/N);
            int j = cellId%N;
            if (j != 0)
                j = j - 1;

            if (this.grid[i][j] != 0)
            {
                count--;
                this.grid[i][j] = 0;
            }
        }
    }

    private boolean fillRemaining(int i, int j)
    {
        //  System.out.println(i+" "+j);
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < SRN)
        {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN)
        {
            if (j== (i/SRN) *SRN)
                j =  j + SRN;
        }
        else
        {
            if (j == N-SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++)
        {
            if (checkIfSafe(i, j, num))
            {
                this.grid[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                this.grid[i][j] = 0;
            }
        }
        return false;
    }

    // Fill a 3 x 3 matrix.
    private void fillBox(int row,int col)
    {
        int num;
        for (int i=0; i<SRN; i++)
        {
            for (int j=0; j<SRN; j++)
            {
                do
                {
                    num = randomGenerator(N);
                }
                while (!unusedInBox(row, col, num));

                grid[row+i][col+j] = num;
            }
        }
    }

    /** Get the value of a cell
     * @param row the row of the cell
     * @param column the column of the cell
     * @return the value of the cell
     */
    public int getCellValue(int row, int column) {
        return this.grid[row][column];
    }

    public void setCellValue(int i, int j, int value) {
        if(this.isFixed[i][j]) {
            throw new IllegalStateException("This cell is fixed.");
        } else if(checkIfSafe(i, j, value)) {
            this.grid[i][j] = value;
        } else {
            throw new IllegalStateException("Invalid value.");
        }
    }

    public boolean isFixed(int i, int j) {
        return this.isFixed[i][j];
    }

    // Random generator
    int randomGenerator(int num) {
        return rnd.nextInt(num)+1;
    }

    // Check if safe to put in cell
    boolean checkIfSafe(int i, int j, int num) {
        return (unusedInRow(i, num) &&
                unusedInCol(j, num) &&
                unusedInBox(i-i%SRN, j-j%SRN, num));
    }

    // check in the row for existence
    boolean unusedInRow(int i,int num) {
        for (int j = 0; j<N; j++)
            if (this.grid[i][j] == num)
                return false;
        return true;
    }

    // check in the cow for existence
    boolean unusedInCol(int j, int num) {
        for (int i = 0; i<N; i++)
            if (this.grid[i][j] == num)
                return false;
        return true;
    }

    boolean unusedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (this.grid[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 9; i++) {
            builder.append(Arrays.toString(this.grid[i]));
            builder.append("\n");
        }

        for (int i = 0; i < 9; i++) {
            builder.append(Arrays.toString(this.isFixed[i]));
            builder.append("\n");
        }

        return builder.toString();
    }
}
