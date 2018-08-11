package org.teamsix;


/**
 * Extends Tetromino to provide S-Tetromino.
 */
public class STetromino extends Tetromino {

    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new S type Tetromino when one is called for.
     *
     * @param xRef:   x-coordinate of reference position
     * @param yRef:   y-coordinate of reference position
     * @param isGhost Whether the tetromino should be a ghost or not.
     */
    public STetromino(int xRef, int yRef, boolean isGhost) {
        super(xRef, yRef, isGhost);
        tetrominoData = new int[][] {{0, 1}, {1, 1}, {1, 0}, {2, 0}};
        size = 3;
        type = 3;
        setBlockArray(generateTetrominoArray(isGhost));
    }

    /**
     * Copies the given STetromino and potentially changes it to a ghost.
     *
     * @param tetrominoToCopy The STetromino to copy.
     * @param convertToGhost  Whether the copy should be a ghost or not.
     */
    public STetromino(Tetromino tetrominoToCopy, boolean convertToGhost) {
        super(tetrominoToCopy, convertToGhost);
    }
}
