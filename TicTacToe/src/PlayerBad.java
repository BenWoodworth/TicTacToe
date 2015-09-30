/*
 * PlayerBad.java
 * 
 * Version: 0
 */

/**
 * A player that takes the first open space available starting
 * from the top left moving across each row.
 * 
 * @author Ben Woodworth
 */
public class PlayerBad implements Player {

    /**
     * Will return an index representing its move choice.
     * 
     * @param board     The game board.
     * @param turn      The current player's turn.
     * 
     * @return Returns the index which represents the player's move.
     */
    @Override
    public int move(Board board, int turn) {
        char turnChar = Board.toChar(turn);
        System.out.println("bad player " + turnChar + " moving...");

        int move = board.getEmptySpaceIndex(0);

        int x = move % board.getSize();
        int y = move / board.getSize();
        System.out.println("Player puts " + turnChar +
                " at (" + x + ", " + y + ").");
        
        return move;
    }
}
