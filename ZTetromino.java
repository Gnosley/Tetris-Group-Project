
    
public class ZTetromino extends Tetromino {
    
    private final int [][] tetrominoData = {{0, 0}, {1, 0}, {1, 1}, {2, 1}};
    private Block[] tetrominoArray = new Block[4];

    /**
     * Constructor for a new Z type Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */
    public ZTetromino (int xRef, int yRef) {
        super(xRef, yRef);
        for (int i =0; i< tetrominoData.length; i++) {
            for (int j = 0; j<tetrominoData[i].length; j++) {
                tetrominoInfo[i][j] = tetrominoData[i][j];
            }
        }
        size = 3;
        type = 4;
        setBlockArray(generateTetrominoArray(false));
    }
    
}