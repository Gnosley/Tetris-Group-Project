import java.util.Random;
import java.util.Arrays;
import java.lang.Math;
import java.util.stream.*;

/** 
 *  Used to randomly generate new tetrominos from the piece subclasses to be placed on the board
 */

public class GenerateTetromino {
    private static Random rand = new Random();
    private Tetromino tetromino;
	private final int numPieces = 7;
	private final double[] hardDistribution = { (2.5/28.0), (5.0/28.0), (8.0/28.0), 
							(13.0/28.0), (18.0/28.0), (23.0/28.0), (28.0/28.0)};
	
	private int[] pieceStats = new int[numPieces];
	String difficulty = "MEDIUM";
    
    
    public GenerateTetromino () {}
    

	
/**
 *	Constructor which randomly determines type based on difficulty, then calls the 
 *  generate method to generate the piece
 *  @param xRef: int, x reference position of the piece on the board
 *  @param yRef: int, y reference position of the piece on the board
 */
	
    public Tetromino newTetromino (int xRef, int yRef) {
       int total = IntStream.of(pieceStats).sum();
		if( difficulty.equals("EASY") ){
		if ( total < 10 ){ // Generate from even distribution when less than 10 pieces played.
			int type = rand.nextInt(numPieces);
			generate(xRef, yRef, type);
			pieceStats[type] += 1;
			return tetromino;
		}
		if ( total >= 10) { // Generate from skewed distribution when more than 10 pieces played.
			double drop = Math.random(); // Generate a random number on (0,1)
			// Set the dropInterval list with the skewed distribution
			// Depending on the value of the random number drop, select the proper piece type
			double[] dropRates = updateDropRates();	
			double[] dropInterval = updateDropInterval(dropRates);
			int type = determineType(dropInterval, drop);
			generate(xRef, yRef, type);
			pieceStats[type] += 1;
			return tetromino;
		}
		}
		if ( difficulty.equals("MEDIUM") ){ // Standard random generation
			int type = rand.nextInt(numPieces);
			generate(xRef, yRef, type);
			pieceStats[type] += 1;
			return tetromino;
		}
		if ( difficulty.equals("HARD") ){ // Reduced drop rates for I,O,T, increased for S,Z,J,L
			double drop = Math.random(); // Generate a random number on (0,1)
			// Depending on the value of the random number drop, select the proper piece type
			int type = determineType(hardDistribution, drop);	
			generate(xRef, yRef, type);
			pieceStats[type] += 1;
			return tetromino;
		}
		return null;
    }

/**
 *  Copy constructor, generate a tetromino of a particular type at an (x,y) location
 * @param xRef: int, x reference position of the piece on the board
 * @param yRef: int, y reference position of the piece on the board
 * @param type: int, from 0-6
 */ 
    public Tetromino oldTetromino (int xRef, int yRef, int type) {
        generate(xRef, yRef, type);
        return tetromino;
    }
    
/**
 * Update list holding the chance to drop each type in "EASY" difficulty
 */ 
	public double[] updateDropRates(){
		double[] dropRates = new double[numPieces];
		int lowest = pieceStats[0];
		int lowestIndex = 0;
		for (int i = 0; i < pieceStats.length; i++){
			if ( pieceStats[i] < lowest ){ // determine the least frequently seen piece
				lowest = pieceStats[i];
				lowestIndex = i;
			}
		}
		dropRates[lowestIndex] = 0.35; // Give the least seen piece a 35% drop rate
		for (int i = 0; i < dropRates.length; i++){
			if ( i != lowestIndex ) {
				dropRates[i] = 0.65/6.0; // Give the other pieces a 65/6 % drop rate
			}
		}
		return dropRates;
	}
/**
 * List holding threshold values based on which a random number on (0,1) will
 * determine the type of piece to be dropped
 * @param dropRates: double[dropRates] is returned from updateDropRates()
 */
	public double[] updateDropInterval(double[] dropRates){
		double[] dropInterval = new double[numPieces];
		for ( int i = 0; i < dropRates.length; i++){
			double sum = 0;
			for ( int j = 0; j <= i; j++){
				sum += dropRates[j] ;
			}
			dropInterval[i] = sum;
		}
		return dropInterval;
	}
/**
 * Determines piece to be dropped based on random number on (0,1) and dropInterval list
 * @param dropInterval : double[] returned from updateDropInterval
 * @param drop : double, a random number on (0,1)
 */
	public int determineType(double[] dropInterval, double drop){
		int type = 0;
		if ( drop <= dropInterval[0] ) {
			type = 0;
		}
		if ( drop > dropInterval[dropInterval.length - 2] ){
			type = dropInterval.length - 1;
		}
		for (int i = 0; i < (dropInterval.length - 2); i++){
			if ( drop > dropInterval[i] && drop <= dropInterval[i+1]){
				type = i + 1;
			}
		}
		return type;
	}

	
/**
 * Generates a tetromino piece by instantiating the subclass of the given type
 * @param xRef: int, x reference position of the piece on the board
 * @param yRef: int, y reference position of the piece on the board
 * @param type: int, from 0-6
 */    	
	
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