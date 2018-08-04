
    
public class ITetromino extends Tetromino {
    
    private final int [][] tetrominoData = {{1, 0}, {1, 1}, {1, 2}, {1, 3}};
    private Block[] tetrominoArray = new Block[4];
    
    /**
     * Constructor for a new I Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */
    public ITetromino (int xRef, int yRef) {
        super(xRef, yRef);
        for (int i =0; i< tetrominoData.length; i++) {
            for (int j = 0; j<tetrominoData[i].length; j++) {
                tetrominoInfo[i][j] = tetrominoData[i][j];
            }
        }
        size = 4;
        type = 0;
        setBlockArray(generateTetrominoArray(false));
    }
    
}