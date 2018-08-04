
    
public class JTetromino extends Tetromino {
    
    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new J type Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */ 
    public JTetromino (int xRef, int yRef) {
        super(xRef, yRef);
        tetrominoData = {{2, 0}, {1, 0}, {1, 1}, {1, 2}};
        size = 3;
        type = 5;
        setBlockArray(generateTetrominoArray(false));
    }
}