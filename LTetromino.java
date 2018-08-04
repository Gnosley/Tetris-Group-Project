
    
public class LTetromino extends Tetromino {
    
    private final int [][] tetrominoData = {{2, 2}, {1, 0}, {1, 1}, {1, 2}};
    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new L type Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */
    public LTetromino (int xRef, int yRef) {
        super(xRef, yRef);for (int i =0; i< tetrominoData.length; i++) {
            for (int j = 0; j<tetrominoData[i].length; j++) {
                tetrominoInfo[i][j] = tetrominoData[i][j];
            }
        }
        size = 3;
        type = 6;
        setBlockArray(generateTetrominoArray(false));
    }
    
}