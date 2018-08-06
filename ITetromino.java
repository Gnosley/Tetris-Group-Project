public class ITetromino extends Tetromino {

    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new I Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */
    public ITetromino(int xRef, int yRef, boolean isGhost) {
        super(xRef, yRef);
        tetrominoData = new int[][] {{1, 0}, {1, 1}, {1, 2}, {1, 3}};
        size = 4;
        type = 0;
        setBlockArray(generateTetrominoArray(isGhost));
    }

    public ITetromino(Tetromino tetrominoToCopy, boolean convertToGhost) {
        super(tetrominoToCopy, convertToGhost);
    }

}
