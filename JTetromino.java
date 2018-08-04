
    
public class JTetromino extends Tetromino {
    
    private final int [][] tetrominoData = {{2, 0}, {1, 0}, {1, 1}, {1, 2}};
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
        for (int i =0; i< tetrominoData.length; i++) {
            for (int j = 0; j<tetrominoData[i].length; j++) {
                tetrominoInfo[i][j] = tetrominoData[i][j];
            }
        }
        size = 3;
        type = 5;
        setBlockArray(generateTetrominoArray(false));
    }
}