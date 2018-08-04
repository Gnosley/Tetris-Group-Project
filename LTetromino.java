
    
public class LTetromino extends Tetromino {
    
    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new L type Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */
    public LTetromino (int xRef, int yRef) {
        super(xRef, yRef);
        tetrominoData = {{2, 2}, {1, 0}, {1, 1}, {1, 2}};
        size = 3;
        type = 6;
        setBlockArray(generateTetrominoArray(false));
    }
    
}