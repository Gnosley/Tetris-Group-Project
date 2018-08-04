import java.util.Random;

public class GenerateTetromino {
    private static Random rand = new Random();
    private Tetromino tetromino;
    
    
    public GenerateTetromino () {}
    
    public Tetromino newTetromino (int xRef, int yRef) {
        int type = rand.nextInt(7);
        generate(xRef, yRef, type);
        return tetromino;
    }
    
    public Tetromino oldTetromino (int xRef, int yRef, int type) {
        generate(xRef, yRef, type);
        return tetromino;
    }
    
    public Tetromino generate (int xReference, int yReference, int type) {
        switch(type) {
            case 0: tetromino = new ITetromino(xReference, yReference);
                    break;
            case 1: tetromino = new OTetromino(xReference, yReference);
                    break;
            case 2: tetromino = new TTetromino(xReference, yReference);
                    break;
            case 3: tetromino = new STetromino(xReference, yReference);
                    break;
            case 4: tetromino = new ZTetromino(xReference, yReference);
                    break;
            case 5: tetromino = new JTetromino(xReference, yReference);
                    break;
            case 6: tetromino = new LTetromino(xReference, yReference);
                    break;
        }
        return tetromino;
    }
    
}