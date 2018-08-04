
    
public class OTetromino extends Tetromino {
    
    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new O type Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */
    public OTetromino (int xRef, int yRef) {
        super(xRef, yRef);
        tetrominoData = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        size = 2;
        type = 1;
        setBlockArray(generateTetrominoArray(false));
    }
    
    
}