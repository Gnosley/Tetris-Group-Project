
public class OTetromino extends Tetromino {

    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new O type Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */
    public OTetromino (int xRef, int yRef, boolean isGhost) {
        super(xRef, yRef, isGhost);
        tetrominoData = new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        size = 2;
        type = 1;
        setBlockArray(generateTetrominoArray(isGhost));
    }

    /**
     * Copy constructor for a Tetromino
     *
     * @param tetrominoToCopy The Tetromino to change
     * @param convertToGhost Whether the copy should be a ghost tetromino
     */
    public OTetromino(Tetromino tetrominoToCopy, boolean convertToGhost) {
        super(tetrominoToCopy, convertToGhost);
    }

    /**
     * Don't do anything if not the first rotation test. OTetromino can not wallkick.
     *
     * @param moveType The direction of rotation to do. 'q' for counter
     * clockwise. 'e' for clockwise.
     * @param testNum The rotation test number.
     */
    @Override
    protected void rotate(char moveType, int testNum) {
        if (testNum == 0) rotate(moveType);
    }
}