package org.teamsix;


/**
 * Extends Tetromino to provide L-Tetromino.
 */
public class LTetromino extends Tetromino {

    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new L type Tetromino when one is called for.
     *
     * @param xRef:   x-coordinate of reference position
     * @param yRef:   y-coordinate of reference position
     * @param isGhost Whether the tetromino should be a ghost or not.
     */
    public LTetromino(int xRef, int yRef, boolean isGhost) {
        super(xRef, yRef, isGhost);
        tetrominoData = new int[][] {{0, 1}, {1, 1}, {2, 1}, {2, 0}};
        size = 3;
        type = 6;
        setBlockArray(generateTetrominoArray(isGhost));
    }

    /**
     * Copies the given LTetromino and potentially changes it to a ghost.
     *
     * @param tetrominoToCopy The LTetromino to copy.
     * @param convertToGhost  Whether the copy should be a ghost or not.
     */
    public LTetromino(Tetromino tetrominoToCopy, boolean convertToGhost) {
        super(tetrominoToCopy, convertToGhost);
    }

}
