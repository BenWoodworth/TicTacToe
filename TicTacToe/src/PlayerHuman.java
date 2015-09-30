
/*
 * PlayerHuman.java
 * 
 * Version: 0
 */

/**
 * Handles moves by the player.
 * 
 * @author Ben Woodworth
 */
public class PlayerHuman implements Player {

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
        System.out.println("human player " + turnChar + " moving...");
        
        String message = "Player " + turnChar +
                ": Enter the col and row for a move (-1 to quit): ";
        
        int x, y;
        while (true) {
            System.out.print(message);
            try {
                String[] split = TicTacToe.scan.nextLine().split(" ", 2);
                x = Integer.parseInt(split[0]);
                if (x == -1) {
                    System.out.println(turnChar + " quits the game");
                    System.exit(0);
                }
                y = Integer.parseInt(split[1]);
                if (x < 0 || x >= board.getSize()) continue;
                if (y < 0 || y >= board.getSize()) continue;
                if (board.getSpace(x, y) != Board.EMPTY) continue;
                break;
            } catch (NumberFormatException e) {
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        System.out.println("Player puts " + turnChar +
                " at (" + x + ", " + y + ").");
        
        return x + y * board.getSize();
    }
    
}
