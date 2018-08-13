package TeamSix.logic;


/**
 * Extends Tetromino to provide J-Tetromino.
 */
public class JTetromino extends Tetromino {

    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new J type Tetromino when one is called for.
     *
     * @param xRef:   x-coordinate of reference position
     * @param yRef:   y-coordinate of reference position
     * @param isGhost Whether the tetromino should be a ghost or not.
     */
    public JTetromino(int xRef, int yRef, boolean isGhost) {
        super(xRef, yRef, isGhost);
        tetrominoData = new int[][] {{0, 0}, {0, 1}, {1, 1}, {2, 1}};
        size = 3;
        type = 5;
        setBlockArray(generateTetrominoArray(isGhost));
    }

    /**
     * Copies the given JTetromino and potentially changes it to a ghost.
     *
     * @param tetrominoToCopy The JTetromino to copy.
     * @param convertToGhost  Whether the copy should be a ghost or not.
     */
    public JTetromino(Tetromino tetrominoToCopy, boolean convertToGhost) {
        super(tetrominoToCopy, convertToGhost);
    }
}
