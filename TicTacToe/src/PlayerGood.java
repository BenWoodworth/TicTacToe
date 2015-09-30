/*
 * PlayerGood.java
 * 
 * Version: 0
 */

/**
 * Player that uses the minimax algorithm to determine the best move.
 * 
 * @author Ben Woodworth
 */
public class PlayerGood implements Player {

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
        System.out.println("good player " + turnChar + " moving...");
        
        BoardTree tree = new BoardTree(board, turn);
        int move = tree.getBestMove();
        
        int x = move % board.getSize();
        int y = move / board.getSize();
        System.out.println("Player puts " + turnChar +
                " at (" + x + ", " + y + ").");
        
        return move;
    }

}
