public class STetromino extends Tetromino {

    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new S type Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */
    public STetromino (int xRef, int yRef, boolean isGhost) {
        super(xRef, yRef);
        tetrominoData = new int[][] {{1, 0}, {1, 1}, {2, 1}, {2, 2}};
        size = 3;
        type = 3;
        setBlockArray(generateTetrominoArray(isGhost));
    }

    public STetromino(Tetromino tetrominoToCopy, boolean convertToGhost) {
        super(tetrominoToCopy, convertToGhost);
    }
}
