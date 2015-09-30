/*
 * BoardEvaluator.java
 * 
 * Version: 0
 */

/**
 * A class that evaluates the value of a board
 * 
 * @author Ben Woodworth
 */
public class BoardEvaluator {
    public static final long INF = Long.MAX_VALUE;
    
    /**
     * Returns the value of a given board.
     * 
     * @param board     The board to be evaluated.
     * @param player    The current player's turn.
     *
     * @return Returns a value/score for the board.
     */
    public static long getBoardValue(Board board, int player) {
        if (board.getState() == Board.TIE) return 0;
        if (board.getWinningPlayer() == player) return INF;
        if (board.getWinningPlayer() == -player) return -INF;
        return rawBoardValue(board, player) - 2 * rawBoardValue(board, -player);
    }
    
    /**
     * Calculates the value of a board without taking
     * wins/losses/ties into account.
     * 
     * @param board     The board to be evaluated.
     * @param player    The current player's turn
     * 
     * @return Returns a value/score for the board.
     */
    private static long rawBoardValue(Board board, int player) {
        // Sum the values of each unblocked row/column/diagonal
        
        long result = 0;
        boolean blocked;
        int count, curSpace;

        // Rows
        for (int r = 0; r < board.getSize(); r++) {
            blocked = false;
            count = 0;
            for (int c = 0; c < board.getSize() && !blocked; c++) {
                curSpace = board.getSpace(c, r);
                if (curSpace == player) count++;
                if (curSpace == -player) blocked = true;
            }
            if (!blocked) result += rowVal(count, board.getSize());
        }

        // Columns
        for (int c = 0; c < board.getSize(); c++) {
            blocked = false;
            count = 0;
            for (int r = 0; r < board.getSize() && !blocked; r++) {
                curSpace = board.getSpace(c, r);
                if (curSpace == player) count++;
                if (curSpace == -player) blocked = true;
            }
            if (!blocked) result += rowVal(count, board.getSize());
        }
        
        // First diagonal [\]
        blocked = false;
        count = 0;
        for (int i = 0; i < board.getSize() && !blocked; i++) {
            curSpace = board.getSpace(i, i);
            if (curSpace == player) count++;
            if (curSpace == -player) blocked = true;
        }
        if (!blocked) result += rowVal(count, board.getSize());

        // Second diagonal [/]
        blocked = false;
        count = 0;
        for (int i = 0; i < board.getSize() && !blocked; i++) {
            curSpace = board.getSpace(board.getSize() - i - 1, i);
            if (curSpace == player) count++;
            if (curSpace == -player) blocked = true;
        }
        if (!blocked) result += rowVal(count, board.getSize());
        
        return result;
    }
    
    /**
     * Calculates the value of a row
     * 
     * @param num   Number of pieces in the row
     * @param size  Size (width) of the board
     * 
     * @return Returns the value of the row
     */
    private static long rowVal(int num, int size) {
        return pow(num, 2 * size + 1);
    }
    
    /**
     * Quick power method for integers
     * Modified code from http://goo.gl/i6Koky
     * 
     * @param base  The base of the exponent
     * @param exp   The power of the exponent
     * 
     * @return Returns b to the p power
     */
    private static long pow(int base, int exp) {
        long result = 1;
        while (exp != 0) {
            if ((exp & 1) == 1)
                result *= base;
            exp >>= 1;
            base *= base;
        }
        return result;
    }
}
