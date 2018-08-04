
    
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
        size = 3;
        type = 5;
        setBlockArray(generateTetrominoArray(false));
    }
    
    /**
     * Generate the new tetromino thorough individulized J Tetromino data
     *
     * @param isGhost:
     *            boolean of if the tetromino is a ghost type or not
     */
    private Block[] generateTetrominoArray(boolean isGhost) {
        int color = 5;
        Block[] tetrominoArray = new Block[4];

        // start iteration at 0 
        for (int i=0; i< tetrominoData.length; i++) {
            int x = tetrominoData[i][0];
            int y = tetrominoData[i][1];

            Block block = new Block(color,
                                    xReferencePosition + x,
                                    yReferencePosition + y,
                                    isGhost);
            tetrominoArray[i] = block;
        }
        return tetrominoArray;
    }
}