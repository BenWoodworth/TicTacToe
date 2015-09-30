/*
 * Player.java
 * 
 * Version: 0
 */

/**
 * This will be an interface for all other player
 * classes (Human, Computer, etc.) to use.
 * 
 * @author Ben Woodworth
 */
public interface Player {
    
    /**
     * Will return an index representing its move choice.
     * 
     * @param board     The game board.
     * @param turn      The current player's turn.
     * 
     * @return Returns the index which represents the player's move.
     */
    public int move(Board board, int turn);
    
    /**
     * Creates a new Player object given a player type.
     * 
     * @param type      The type of player.
     * @return Returns a new player object.
     */
    public static Player getNewPlayer(String type) {
        type = type.toLowerCase();
        if (type.equals("human"))
            return new PlayerHuman();
        else if (type.equals("random"))
            return new PlayerRandom();
        else if (type.equals("good"))
            return new PlayerGood();
        else if (type.equals("bad"))
            return new PlayerBad();
        else if (type.equals("moderate"))
            return new PlayerModerate();
        return null;
    }
}
