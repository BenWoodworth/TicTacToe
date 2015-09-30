/*
 * TicTacToe.java
 * 
 * Version: 0
 */

//TODO Printing numbers?
//TODO Comments

import java.util.Scanner;

/**
 * The driver class for the project.
 * 
 * @author Ben Woodworth
 */
public class TicTacToe {
    public static Scanner scan;

    /**
     * Starts a game of Tic Tac Toe with the given arguments
     * 
     * @param args  The arguments to start the game with
     */
    public static void main(String args[]) {
        if (args.length <= 1 || args.length > 3) err();

        String x = args[0];
        String o = args[1];
        
        int size = 3;
        if (args.length == 3) size = Integer.parseInt(args[2]) * 2 + 1;
        if (size < 3 || size > 7) err();
        
        
        scan = new Scanner(System.in);
        startGame(x, o, size);
        scan.close();
    }

    /**
     * Shows how to properly start the game then exits the program
     */
    public static void err() {
        System.err.println(
            "Usage: java TicTacToe player-X player-O [size]\n" +
            "where player-X and player-O are one of:\n" +
            "\thuman bad good random\n"+
            "and [size] is optional in the range from 1 to 3"
        );
        System.exit(-1);
    }

    /**
     * Starts a new game of Tic Tac Toe.
     * 
     * @param playerX   The type of player X will be.
     * @param playerO   The type of player O will be.
     * @param size      The width/height of the board.
     */
    public static int startGame(String playerX, String playerO, int size) {
        Player[] players = new Player[2];
        players[0] = Player.getNewPlayer(playerX);
        players[1] = Player.getNewPlayer(playerO);

        if (players[0] == null || players[1] == null) err();
        
        Board board = new Board(size);

        int turn = 0;
        while (board.getState() == Board.UNFINISHED) {
            System.out.println("\n" + board + "\n");

            int curPlayer = turn * 2 - 1;
            int move = players[turn].move(board, curPlayer);
            board.setSpace(move, curPlayer);

            turn = 1 - turn;
        }
        System.out.println(board.gameFinishMessage);
        System.out.println("\n" + board + "\n");

        return board.getState();
    }
}
