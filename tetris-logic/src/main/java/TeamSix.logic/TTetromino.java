package TeamSix.logic;


/**
 * Extends Tetromino to provide T-Tetromino.
 */
public class TTetromino extends Tetromino {

    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new T type Tetromino when one is called for.
     *
     * @param xRef:   x-coordinate of reference position
     * @param yRef:   y-coordinate of reference position
     * @param isGhost Whether the new tetromino is a ghost or not.
     */
    public TTetromino(int xRef, int yRef, boolean isGhost) {
        super(xRef, yRef, isGhost);
        tetrominoData = new int[][] {{1, 0}, {1, 1}, {0, 1}, {2, 1}};
        size = 3;
        type = 2;
        setBlockArray(generateTetrominoArray(isGhost));
    }

    /**
     * Copies the given TTetromino and potentially changes it to a ghost.
     *
     * @param tetrominoToCopy The TTetromino to copy.
     * @param convertToGhost  Whether the copy should be a ghost or not.
     */
    public TTetromino(Tetromino tetrominoToCopy, boolean convertToGhost) {
        super(tetrominoToCopy, convertToGhost);
    }
}
