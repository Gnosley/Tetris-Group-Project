
    
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
        size = 2;
        type = 1;
        setBlockArray(generateTetrominoArray(false));
    }
    
    /**
     * Generate the new tetromino thorough individulized O Tetromino data
     *
     * @param isGhost:
     *            boolean of if the tetromino is a ghost type or not
     */
    private Block[] generateTetrominoArray(boolean isGhost) {
        int color = 1;
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