package TeamSix.logic;


/**
 * Extends Tetromino to provide Z-Tetromino.
 */
public class ZTetromino extends Tetromino {

    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new Z type Tetromino when one is called for.
     *
     * @param xRef:   x-coordinate of reference position
     * @param yRef:   y-coordinate of reference position
     * @param isGhost Whether the new tetromino is a ghost or not.
     */
    public ZTetromino(int xRef, int yRef, boolean isGhost) {
        super(xRef, yRef, isGhost);
        tetrominoData = new int[][] {{0, 0}, {1, 0}, {1, 1}, {2, 1}};
        size = 3;
        type = 4;
        setBlockArray(generateTetrominoArray(isGhost));
    }

    /**
     * Copies the given ZTetromino and potentially changes it to a ghost.
     *
     * @param tetrominoToCopy The ZTetromino to copy.
     * @param convertToGhost  Whether the copy should be a ghost or not.
     */
    public ZTetromino(Tetromino tetrominoToCopy, boolean convertToGhost) {
        super(tetrominoToCopy, convertToGhost);
    }
}
