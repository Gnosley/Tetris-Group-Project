import java.util.Random;
import java.util.Arrays;
import java.lang.Math;
import java.util.stream.*;


/**
 * Used to randomly generate new tetrominos from the piece subclasses to be
 * placed on the board
 */

public class GenerateTetromino {

    private static Random rand = new Random();

    private Tetromino tetromino;

    private final int numPieces = 7;

    private final double[] hardDistribution = { (2.5 / 28.0), (5.0 / 28.0),
            (8.0 / 28.0), (13.0 / 28.0), (18.0 / 28.0), (23.0 / 28.0),
            (28.0 / 28.0) };

    private int[] pieceStats = new int[numPieces];

    String difficulty = "HARD";

    public GenerateTetromino() {
    }

    /**
     * Constructor which randomly determines type based on difficulty, then
     * calls the generate method to generate the piece
     * 
     * @param xRef: int, x reference position of the piece on the board
     * @param yRef: int, y reference position of the piece on the board
     */

    public Tetromino newTetromino(int xRef, int yRef) {
        int total = IntStream.of(pieceStats).sum();
        int type = 0;
        if (difficulty.equals("EASY")) {

            if (total < 10) { // Generate from even distribution when less than
                              // 15 pieces played.
                type = rand.nextInt(numPieces);

                generate(xRef, yRef, type);
                pieceStats[type] += 1;
                return tetromino;
            }

            if (total >= 10) { // Generate from skewed distribution when more
                               // than 15 pieces played.

                double[] dropRates = new double[numPieces];
                double[] dropInterval = new double[numPieces];
                int lowest = pieceStats[0];
                int lowestIndex = 0;
                for (int i = 0; i < pieceStats.length; i++) {
                    if (pieceStats[i] < lowest) { // determine the least
                                                  // frequently seen piece
                        lowest = pieceStats[i];
                        lowestIndex = i;
                    }
                }
                dropRates[lowestIndex] = 0.35; // Give the least seen piece a
                                               // 35% drop rate
                for (int i = 0; i < dropRates.length; i++) {
                    if (i != lowestIndex) {
                        dropRates[i] = 0.65 / 6.0; // Give the other pieces a
                                                   // 65/6 % drop rate
                    }
                }

                double drop = Math.random(); // Generate a random number on
                                             // (0,1)
                // Set the dropInterval list with the skewed distribution
                for (int i = 0; i < dropRates.length; i++) {
                    double sum = 0;
                    for (int j = 0; j <= i; j++) {
                        sum += dropRates[j];
                    }
                    dropInterval[i] = sum;
                }
                // Depending on the value of the random number drop, select the
                // proper piece type
                if (drop <= dropInterval[0]) {
                    type = 0;
                }
                if (drop > dropInterval[dropInterval.length - 2]) {
                    type = dropInterval.length - 1;
                }
                for (int i = 0; i < (dropInterval.length - 2); i++) {
                    if (drop > dropInterval[i] && drop <= dropInterval[i + 1]) {
                        type = i + 1;
                    }
                }

                generate(xRef, yRef, type);
                pieceStats[type] += 1;
                return tetromino;
            }
        }
        if (difficulty.equals("MEDIUM")) { // Standard random generation
            type = rand.nextInt(numPieces);

            generate(xRef, yRef, type);
            pieceStats[type] += 1;
            return tetromino;
        }
        if (difficulty.equals("HARD")) { // Reduced drop rates for I,O,T,
                                         // increased for S,Z,J,L
            double drop = Math.random(); // Generate a random number on (0,1)

            // Depending on the value of the random number drop, select the
            // proper piece type
            if (drop <= hardDistribution[0]) {
                type = 0;
            }
            if (drop > hardDistribution[hardDistribution.length - 2]) {
                type = hardDistribution.length - 1;
            }
            for (int i = 0; i < (hardDistribution.length - 2); i++) {
                if (drop > hardDistribution[i]
                        && drop <= hardDistribution[i + 1]) {
                    type = i + 1;
                }
            }

            generate(xRef, yRef, type);
            pieceStats[type] += 1;
            return tetromino;
        }
        return null;

    }

    /**
     * Copy constructor, generate a tetromino of a particular type at an (x,y)
     * location
     * 
     * @param xRef: int, x reference position of the piece on the board
     * @param yRef: int, y reference position of the piece on the board
     * @param type: int, from 0-6
     */
    public Tetromino oldTetromino(int xRef, int yRef, int type) {
        generate(xRef, yRef, type);
        return tetromino;
    }

    /**
     * Generates a tetromino piece by instantiating the subclass of the given
     * type
     * 
     * @param xRef: int, x reference position of the piece on the board
     * @param yRef: int, y reference position of the piece on the board
     * @param type: int, from 0-6
     */

    public Tetromino generate(int xReference, int yReference, int type) {
        switch(type) {
            case 0:
                tetromino = new ITetromino(xReference, yReference);
                break;
            case 1:
                tetromino = new OTetromino(xReference, yReference);
                break;
            case 2:
                tetromino = new TTetromino(xReference, yReference);
                break;
            case 3:
                tetromino = new STetromino(xReference, yReference);
                break;
            case 4:
                tetromino = new ZTetromino(xReference, yReference);
                break;
            case 5:
                tetromino = new JTetromino(xReference, yReference);
                break;
            case 6:
                tetromino = new LTetromino(xReference, yReference);
                break;
        }
        return tetromino;
    }

}
