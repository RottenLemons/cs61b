package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // fill this in
        if (side == Side.NORTH) {
            // Don't you dare try to write all of your
            // code in this method. You will want to write
            // helper methods. And those helper methods should
            // have helper methods.
            rotateLeft(board);
            shiftAndMerge(board);
            rotateRight(board);
            return;
        } else if (side == Side.EAST) {
            rotateRight(board);
            rotateRight(board);
            shiftAndMerge(board);
            rotateRight(board);
            rotateRight(board);
            return;
        } else if (side == Side.WEST) {
            shiftAndMerge(board);
            return;
        } else { // SOUTH
            rotateRight(board);
            shiftAndMerge(board);
            rotateLeft(board);
            return;
        }
    }

    public static void shiftAndMerge(int[][] board) {
        shiftBoard(board);
        merge(board);
        shiftBoard(board);
    }

    public static void shiftBoard(int[][] board) {
        for (int r = 0; r < board.length; r++) {
            board[r] = shiftRow(board[r]);
        }
    }

    public static int[] shiftRow(int[] row) {
        int countX = 0;
        for (int c = 0; c < row.length; c++) {
            if (row[c] == 0) {
                countX++;
            } else {
                row[c - countX] = row[c];
            }
        }

        for (int c = 0; c < countX; c++) {
            row[row.length - c - 1] = 0;
        }

        return row;
    }

    public static void merge(int[][] board) {
        for (int r = 0; r < board.length; r++) {
            board[r] = mergeRow(board[r]);
        }
    }

    public static int[] mergeRow(int[] row) {
        for (int c = 0; c < row.length - 1; c++) {
            if (row[c] + row[c + 1] != 0 && row[c] == row[c + 1]) {
                row[c] *= 2;
                row[c + 1] = 0;
            }
        }
        return row;
    }
}
