
    
public class TTetromino extends Tetromino {
    
    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new T type Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */ 
    public TTetromino (int xRef, int yRef) {
        super(xRef, yRef);
        tetrominoData = new int[][] {{1, 0}, {1, 1}, {0, 1}, {2, 1}};
        size = 3;
        type = 2;
        setBlockArray(generateTetrominoArray(false));
    }
}