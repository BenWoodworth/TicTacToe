/*
 * PlayerModerate.java
 * 
 * Version: 0
 */

/**
 * A class that plays the game better than bad, but worse than good.
 * I used this to test board evaluation without needing to do a
 * minimax search.
 * 
 * @author Ben Woodworth
 */
public class PlayerModerate implements Player {

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
        System.out.println("moderate player " + turnChar + " moving...");

        int move = getMove(board, turn);

        int x = move % board.getSize();
        int y = move / board.getSize();
        System.out.println("Player puts " + turnChar +
                " at (" + x + ", " + y + ").");
        
        return move;
    }

    /**
     * Gets the move for the bad player.
     * 
     * @param board     The current board.
     * @param turn      The current turn.
     * 
     * @return Returns the move to play.
     */
    private static int getMove(Board board, int turn) {
        // Evaluate available moves
        long bestScore = 0;
        int bestMove = -1;
        long curScore;
        for (int i = 0; i < board.getSpaces(); i++) {
            if (board.getSpace(i) != Board.EMPTY) continue;
            Board testBoard = new Board(board);
            testBoard.setSpace(i, turn);
            
            curScore = BoardEvaluator.getBoardValue(testBoard, turn);
            if (bestMove == -1 || curScore > bestScore) {
                bestScore = curScore;
                bestMove = i;
            }
        }
        return bestMove;
    }
}
