/*
 * PlayerRandom.java
 * 
 * Version: 0
 */

/**
 * A player who moves randomly
 * 
 * @author Ben Woodworth
 */
public class PlayerRandom implements Player {

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
        System.out.println("random player " + turnChar + " moving...");
        
        int move = (int)(Math.random() * board.getEmptySpaces());
        move = board.getEmptySpaceIndex(move);
        
        int x = move % board.getSize();
        int y = move / board.getSize();
        System.out.println("Player puts " + turnChar +
                " at (" + x + ", " + y + ").");
        
        return move;
    }

}
