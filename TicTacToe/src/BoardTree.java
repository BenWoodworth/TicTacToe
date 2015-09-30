import java.util.ArrayList;
import java.util.List;

/*
 * BoardTree.java
 * 
 * Version: 0
 */

/**
 * The BoardTree class organizes boards into a tree and
 * evaluates board values using the minimax algorithm.
 * 
 * @author Ben Woodworth
 */
public class BoardTree {
    public static final int SEARCH_TIME = 5000;

    BoardTreeNode head;
    Board board;
    int turn;

    List<BoardTreeNode> toGrow;

    /**
     * Create a new BoardTree object.
     * 
     * @param board     The board to represent the head of the tree.
     * @param turn      The current turn in the game.
     * @param depth     The depth of the tree.
     */
    public BoardTree(Board board, int turn) {
        head = new BoardTreeNode(board, turn);
        this.board = board;
        this.turn = turn;

        toGrow = new ArrayList<BoardTreeNode>();
        toGrow.add(head);
        growTree(SEARCH_TIME);
    }

    /**
     * Grows the tree for a certain amount of time, or until
     * the tree covers all the possible boards.
     * 
     * @param ms    The amount of time to create the tree.
     */
    public void growTree(int ms) {
        long endTime = System.currentTimeMillis() + ms;
        int i;
        for (i = 0; i < toGrow.size(); i++) {
            if (System.currentTimeMillis() > endTime) break;
            BoardTreeNode[] newNodes = toGrow.get(i).growChildren();
            for (BoardTreeNode n : newNodes)
                if (n.children.length > 0 && n != null)
                    toGrow.add(n);
        }
        if (i < toGrow.size())
            toGrow = toGrow.subList(i, toGrow.size() - 1);
        else if (i == toGrow.size())
            toGrow.clear();
    }

    /**
     * Returns the best move for the current board / player
     * 
     * @return The index of the best move
     */
    public int getBestMove() {
        long max = 0;
        int index = -1;
        for (int i = 0; i < head.children.length; i++) {
            long value = head.children[i].getValue(turn);
            if (index == -1 || value > max) {
                max = value;
                index = i;
            }
        }
        return board.getEmptySpaceIndex(index);
    }

    /**
     * The BoardTreeNode class holds information about
     * each board in the tree.
     */
    class BoardTreeNode {
        public BoardTreeNode[] children;
        public Board board;
        private int turn, childCount;

        /**
         * Creates a new BoardTreeNode.
         * 
         * @param board     The Board held by this node.
         * @param turn      The player whose turn it is.
         * @param depth     The depth within the tree.
         * @param maxDepth  The maximum depth of the tree.
         */
        public BoardTreeNode(Board board, int turn) {
            this.board = board;
            childCount = 0;
            this.turn = turn;

            if (board.getState() == Board.UNFINISHED)
                children = new BoardTreeNode[board.getEmptySpaces()];
            else
                children = new BoardTreeNode[0];
        }
        /**
         * Creates a new BoardTreeNode.
         * 
         * @param board     The Board held by this node.
         * @param parent    The parent BoardTreeNode.
         */
        public BoardTreeNode(Board board, BoardTreeNode parent) {
            this(board, -parent.turn);
        }

        /**
         * Adds a child to this node.
         * 
         * @param child     Child node to add.
         */
        public void addChild(Board board) {
            children[childCount++] = new BoardTreeNode(board, this);
        }

        /**
         * Finds the value of the board using a partial minimax search.
         * 
         * @param player    The player analyzing the board.
         * @return Returns the value of the board.
         */
        public long getValue(int player) {
            if (childCount > 0) {
                final long inf = BoardEvaluator.INF;
                long result = children[0].getValue(player);
                long value;
                if (player == turn) { //Max
                    for (int i = 1; i < children.length && result != inf; i++) {
                        value = children[i].getValue(player);
                        if (value > result) result = value;
                    }
                } else { //Min
                    for (int i = 1; i < children.length && result != -inf; i++) {
                        value = children[i].getValue(player);
                        if (value < result) result = value;
                    }
                }
                return result;
            }
            return BoardEvaluator.getBoardValue(board, player);
        }

        /**
         * Populates the node with more children
         */
        public BoardTreeNode[] growChildren() {
            if (childCount > 0 || children.length == 0) return children;
            for (int i = 0; i < board.getSpaces(); i++) {
                if (board.getSpace(i) != Board.EMPTY) continue;
                if (board.getState() != Board.UNFINISHED) continue;
                Board newBoard = new Board(board);
                newBoard.setSpace(i, turn);
                addChild(newBoard);
            }
            return children;
        }
    }
}