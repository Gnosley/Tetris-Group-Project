
    
public class OTetromino extends Tetromino {
    
    private final int [][] tetrominoData = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
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
        for (int i =0; i< tetrominoData.length; i++) {
            for (int j = 0; j<tetrominoData[i].length; j++) {
                tetrominoInfo[i][j] = tetrominoData[i][j];
            }
        }
        size = 2;
        type = 1;
        setBlockArray(generateTetrominoArray(false));
    }
    
    
}