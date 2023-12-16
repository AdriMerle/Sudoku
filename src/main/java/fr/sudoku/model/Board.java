package fr.sudoku.model;

public class Board {
    private final int[][] grid = new int[9][9];

    public Board() {
        // Initialize the grid with 0
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9 ; j++) {
                this.grid[i][j] = 0;
            }
        }
    }

    /** Check grid integrity
     * Each row, column and square must contain only one time each number from 1 to 9
     * @return true if the grid is valid, false otherwise
     */
    public boolean checkGrid() {
        // Check each row
        for (int i = 0; i < 9; i++) {
            if (!checkRow(i)) {
                return false;
            }
        }

        // Check each column
        for (int i = 0; i < 9; i++) {
            if (!checkColumn(i)) {
                return false;
            }
        }

        // Check each square
        for (int i = 0; i < 9; i++) {
            if (!checkSquare(i)) {
                return false;
            }
        }

        return true;
    }

    /** Check if a row is valid
     * @param row the row to check
     * @return true if the row is valid, false otherwise
     */
    private boolean checkRow(int row) {
        boolean[] numbers = initNumbers();

        // Check each number
        for (int i = 0; i < 9; i++) {
            if (this.grid[row][i] != 0) {
                if (numbers[this.grid[row][i] - 1]) {
                    return false;
                } else {
                    numbers[this.grid[row][i] - 1] = true;
                }
            }
        }

        return true;
    }

    /** Check if a column is valid
     * @param column the column to check
     * @return true if the column is valid, false otherwise
     */
    private boolean checkColumn(int column) {
        boolean[] numbers = initNumbers();

        // Check each number
        for (int i = 0; i < 9; i++) {
            if (this.grid[i][column] != 0) {
                if (numbers[this.grid[i][column] - 1]) {
                    return false;
                } else {
                    numbers[this.grid[i][column] - 1] = true;
                }
            }
        }

        return true;
    }

    /** Check if a square is valid
     * @param square the square to check
     * @return true if the square is valid, false otherwise
     */
    public boolean checkSquare(int square) {
        boolean[] numbers = initNumbers();

        // Check each number
        for (int i = 0; i < 9; i++) {
            int row = (square / 3) * 3 + (i / 3);
            int column = (square % 3) * 3 + (i % 3);

            if (this.grid[row][column] != 0) {
                if (numbers[this.grid[row][column] - 1]) {
                    return false;
                } else {
                    numbers[this.grid[row][column] - 1] = true;
                }
            }
        }

        return true;
    }

    private boolean[] initNumbers() {
        boolean[] numbers = new boolean[9];

        // Initialize the array
        for (int i = 0; i < 9; i++) {
            numbers[i] = false;
        }

        return numbers;
    }

    /** Get the value of a cell
     * @param row the row of the cell
     * @param column the column of the cell
     * @return the value of the cell
     */
    public int getCellValue(int row, int column) {
        return this.grid[row][column];
    }
}
