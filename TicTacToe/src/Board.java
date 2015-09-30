/* 
 * Board.java 
 * 
 * Version: 0
 */

/**
 * @author Ben Woodworth
 * 
 * The Board class holds information about the game
 * board, and gives methods to interact with it.
 */
public class Board {
    public static final int X = -1;
    public static final int EMPTY = 0;
    public static final int O = 1;

    private static final byte UNKNOWN = 0;
    public static final byte X_WINS = 1;
    public static final byte O_WINS = 2;
    public static final byte TIE = 3;
    public static final byte UNFINISHED = 4;

    private int size;
    private int[] board;
    private byte state;

    public String gameFinishMessage;
    
    /**
     * Creates a new Board object
     * 
     * @param size    The width/height of the new Board
     */
    public Board(int size) {
        this.size = size;
        board = new int[size * size];
        state = UNFINISHED;
        gameFinishMessage = null;
    }
    
    /**
     * Creates a new board from an existing board
     * 
     * @param b     The board to copy
     */
    public Board(Board b) {
        size = b.size;
        board = b.board.clone();
        state = b.state;
    }

    /**
     * Get the size of the board
     * 
     * @return  Returns the size of the board
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Gets the number of spaces on the board.
     * 
     * @return Returns the number of spaces on the board.
     */
    public int getSpaces() {
        return board.length;
    }

    /**
     * Gets the player that occupies the specified space
     * 
     * @param index     Index to get
     * @retrun Returns the player in the specified space
     */
    public int getSpace(int index) {
        return board[index];
    }

    /**
     * Sets the specified space to a player
     * @param index     Index to set
     * @param val       Player to set space to
     */
    public void setSpace(int index, int val) {
        board[index] = val;
        state = UNKNOWN;
    }

    /**
     * Gets the player that occupies the specified space
     * 
     * @param x     Column to get
     * @param y     Row to get
     *
     * @return The player occupying the specified space
     */
    public int getSpace(int x, int y) {
        return board[x + y * size];
    }

    /**
     * Sets the specified space to a player
     * 
     * @param x     Column
     * @param y     Row
     * @param val   Value
     */
    public void setSpace(int x, int y, int val) {
        setSpace(x + y * size, val);
    }

    /**
     * Gets the index of a space
     * @param index     The empty space index to find
     * @return Returns the index of the space
     */
    public int getEmptySpaceIndex(int index) {
        int curIndex = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] != Board.EMPTY) continue;
            if (curIndex++ == index) return i;
        }
        return -1;
    }

    /**
     * Gets the current state of the board (Who won / tie / unfinished)
     * 
     * @return Current board state
     */
    public byte getState() {
        if (state != UNKNOWN) return state;
        
        // Diagonal Win Check
        int a = getSpace(0, 0);
        int b = getSpace(size - 1, 0);
        if (a == EMPTY) a = -2;
        if (b == EMPTY) b = -2;
        for (int i = 1; i < size && !(a == -2 && b == -2); i++) {
            if (getSpace(i, i) != a) a = -2;
            if (getSpace(size - i - 1, i) != b) b = -2;
        }
        if (a != -2) {
            gameFinishMessage = toChar(a) + " wins on the diagonal!";
            return state = winner(a);
        }
        if (b != -2) {
            gameFinishMessage = toChar(b) + " wins on the diagonal!";
            return state = winner(b);
        }

        // Row+Column Win Check
        for (int i = 0; i < size; i++) {
            int r = getSpace(0, i);
            int c = getSpace(i, 0);
            if (r == EMPTY) r = -2;
            if (c == EMPTY) c = -2;
            for (int j = 1; j < size && !(r == -2 && c == -2); j++) {
                if (getSpace(j, i) != r) r = -2;
                if (getSpace(i, j) != c) c = -2;
            }
            if (r != -2) {
                gameFinishMessage = toChar(r) + " wins in row: " + i;
                return state = winner(r);
            }
            if (c != -2) {
                gameFinishMessage = toChar(c) + " wins in column: " + i;
                return state = winner(c);
            }
        }

        // Tie/Unfinished Check
        for (int i = 0; i < board.length; i++) {
            if (board[i] == EMPTY) return state = UNFINISHED;
        }
        gameFinishMessage = "Its a tie, no one wins.";
        return state = TIE;
    }
    
    /**
     * Gets a winner constant given a player constant
     * 
     * @param player    The player constant
     * 
     * @return Returns the winner constant
     */
    private static byte winner(int player){
        return player == X ? X_WINS : O_WINS;
    }

    /**
     * Get the player who won the game.
     * 
     * @return Returns the winner, or EMPTY if unfinished
     */
    public byte getWinningPlayer() {
        getState();
        if (state == X_WINS) return X;
        if (state == O_WINS) return O;
        return EMPTY;
    }

    /**
     * Counts the number of empty spaces on the board.
     * 
     * @return The number of empty spaces.
     */
    public int getEmptySpaces() {
        int result = 0;
        for (int i = 0; i < board.length; i++)
            if (board[i] == EMPTY) result++;
        return result;
    }

    /**
     * Creates a String representing the board
     * 
     * @return The board as a String
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < size; row++) {
            if (row != 0) sb.append('\n');
            for (int col = 0; col < size; col++) {
                sb.append(toChar(getSpace(col, row)));
                if (col != size - 1) sb.append('|');
            }
            if (row == size - 1) continue;
            sb.append('\n');
            for (int i = 0; i < 2 * size - 1; i++) {
                if (i % 2 == 0)
                    sb.append('-');
                else
                    sb.append('+');
            }
        }
        return sb.toString();
    }
    
    /**
     * Gets the character that represents a player
     * 
     * @param player    The player to get the character
     * 
     * @return returns the the character that represents the player.
     */
    public static char toChar(int player) {
        if (player == X) return 'X';
        if (player == O) return 'O';
        return ' ';
    }
}
