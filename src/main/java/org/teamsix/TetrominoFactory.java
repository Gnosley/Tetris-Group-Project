package org.teamsix;


import java.util.Random;
import java.util.Arrays;
import java.lang.Math;
import java.util.stream.*;

/**
 * TetrominoFactory provides an interface to the Tetromino subclasses for easy
 * copying and generation of new tetrominos. Provides random generation code
 * with difficulty settings.
 */
public class TetrominoFactory {
    private static Random rand = new Random();
    private final int numPieces = 7;
    private int[] pieceStats = new int[numPieces];
    private int defaultXRef = 0;
    private int defaultYRef = 0;

    private String difficulty = "MEDIUM";

    /**
     * Constructor for TetrominoFactory.
     *
     * @param difficulty The difficulty of the random generation. Distribution
     *                   of tetrominos is altered based on difficulty.
     * @param defaultX   x-coordinate of generated tetrominos' reference
     *                   position.
     * @param defaultY   y-coordinate of generated tetrominos' reference
     *                   position.
     */
    public TetrominoFactory(String difficulty, int defaultX, int defaultY) {
        difficulty = difficulty.toUpperCase();
        if (difficulty.equals("EASY")
                || difficulty.equals("MEDIUM")
                || difficulty.equals("HARD")) {

            this.difficulty = difficulty;
        }

        this.defaultXRef = defaultX;
        this.defaultYRef = defaultY;
    }


    /**
     * Returns a Tetromino of randomly generated type with reference position
     * based on what was provided in constructor.
     *
     * @return Tetromino of random type.
     */
    public Tetromino getTetromino() {
        return getTetromino(this.defaultXRef, this.defaultYRef);
    }

    /**
     * Generate a random tetromino with given reference position.
     *
     * @param xRef The x coordinate of the tetromnio's reference position.
     * @param yRef The y coordinate of the tetromino's reference position.
     * @return Tetromino of random type at given reference position.
     */
    public Tetromino getTetromino(int xRef, int yRef) {
        int type = generateType();
        pieceStats[type]++; // increment the counter for chosen type.
        return getTetromino(xRef, yRef, type, false);
    }


    /**
     * Get a Tetromino at default position with the given type.
     *
     * @param type The type of Tetromino to return.
     * @return Tetromino of given type.
     */
    public Tetromino getTetromino(int type) {
        return getTetromino(this.defaultXRef, this.defaultYRef, type, false);
    }

    /**
     * Get a Tetromino of given type at default position.
     *
     * @param type    The type of tetromino to return.
     * @param isGhost Whether the returned tetromino should be a ghost or not.
     * @return A Tetromino of given type.
     */
    public Tetromino getTetromino(int type, boolean isGhost) {
        return getTetromino(this.defaultXRef, this.defaultYRef, type, isGhost);
    }

    /**
     * Get a Tetromino of given type at given reference position.
     *
     * @param xRef    The x coordinate of the reference position.
     * @param yRef    The y coordinate of the reference position.
     * @param type    The type of Tetromino to generate.
     * @param isGhost Whether the returned Tetromino is a ghost or not.
     * @return A Tetromino of given type at given position.
     */
    public Tetromino getTetromino(int xRef, int yRef, int type, boolean isGhost) {
        Tetromino tetromino = null;
        switch (type) {
            case 0:
                tetromino = new ITetromino(xRef, yRef, isGhost);
                break;
            case 1:
                tetromino = new OTetromino(xRef, yRef, isGhost);
                break;
            case 2:
                tetromino = new TTetromino(xRef, yRef, isGhost);
                break;
            case 3:
                tetromino = new STetromino(xRef, yRef, isGhost);
                break;
            case 4:
                tetromino = new ZTetromino(xRef, yRef, isGhost);
                break;
            case 5:
                tetromino = new JTetromino(xRef, yRef, isGhost);
                break;
            case 6:
                tetromino = new LTetromino(xRef, yRef, isGhost);
                break;
        }
        return tetromino;
    }

    /**
     * Copy method for a Tetromino.
     *
     * @param tetrominoToCopy The Tetromino to get a copy of.
     * @return A copy of the given Tetromino.
     */
    public Tetromino getTetrominoCopy(Tetromino tetrominoToCopy) {
        return getTetrominoCopy(tetrominoToCopy, tetrominoToCopy.getIsGhost());
    }

    /**
     * Copy method for a Tetromino with option for it to become a ghost.
     *
     * @param tetrominoToCopy The Tetromnino to copy and potentially switch to
     *                        being a ghost.
     * @param convertToGhost  Whether the copy should be a ghost or not.
     * @return A (potentially ghost) copy of the given Tetromino.
     */
    public Tetromino getTetrominoCopy(Tetromino tetrominoToCopy, boolean convertToGhost) {
        int type = tetrominoToCopy.getType();
        Tetromino tetromino = null;
        switch (type) {
            case 0:
                tetromino = new ITetromino(tetrominoToCopy, convertToGhost);
                break;
            case 1:
                tetromino = new OTetromino(tetrominoToCopy, convertToGhost);
                break;
            case 2:
                tetromino = new TTetromino(tetrominoToCopy, convertToGhost);
                break;
            case 3:
                tetromino = new STetromino(tetrominoToCopy, convertToGhost);
                break;
            case 4:
                tetromino = new ZTetromino(tetrominoToCopy, convertToGhost);
                break;
            case 5:
                tetromino = new JTetromino(tetrominoToCopy, convertToGhost);
                break;
            case 6:
                tetromino = new LTetromino(tetrominoToCopy, convertToGhost);
                break;
        }
        return tetromino;

    }


    /**
     * Get the generators current difficulty setting.
     *
     * @return String with the difficulty.
     */
    public String getDifficulty() {
        return this.difficulty;
    }

    /**
     * Method to determine type generation based on difficulty.
     *
     * @return An int with the type of tetromino to generate.
     */
    private int generateType() {
        int type = 0;
        switch (this.difficulty) {
            case "EASY":
                type = genEasyType();
                break;
            case "MEDIUM":
                type = genMediumType();
                break;
            case "HARD":
                type = genHardType();
                break;
        }
        return type;
    }

    /**
     * Generates a type with the distribution for easy difficulty.
     *
     * @return Randomly generated tetromino type.
     */
    private int genEasyType() {
        int type;
        int total = IntStream.of(pieceStats).sum();

        // Generate from skewed distribution when more than 10 pieces played.
        if (total >= 10) {

            double drop = Math.random(); // Generate a random number on (0,1)
            // Set the dropInterval list with the skewed distribution Depending
            // on the value of the random number drop, select the proper piece
            // type
            double[] dropRates = updateDropRates();
            double[] dropInterval = updateDropInterval(dropRates);
            type = determineType(dropInterval, drop);
        } else type = rand.nextInt(numPieces);
        return type;
    }

    /**
     * Generates a type with the distribution for medium difficulty.
     *
     * @return Randomly generated tetromino type.
     */
    private int genMediumType() {
        return rand.nextInt(numPieces); // standard random gneration
    }

    /**
     * Generates a type with the distribution for hard difficulty.
     *
     * @return Randomly generated tetromino type.
     */
    private int genHardType() {
        // Set the distribution
        double[] hardDistribution = { (2.5 / 28.0), (5.0 / 28.0),
                                      (8.0 / 28.0), (13.0 / 28.0),
                                      (18.0 / 28.0), (23.0 / 28.0),
                                      (28.0 / 28.0) };


        // Reduced drop rates for I,O,T, increased for S,Z,J,L
        double drop = Math.random(); // Generate a random number on (0,1)
        // Depending on the value of the random number drop, select the proper piece type
        return determineType(hardDistribution, drop);
    }


    /**
     * Update list holding the chance to drop each type in "EASY" difficulty
     *
     * @return An array containing the percentage for each tetrominos drop rate
     */
    private double[] updateDropRates() {
        double[] dropRates = new double[numPieces];
        int lowest = pieceStats[0];
        int lowestIndex = 0;
        for (int i = 0; i < pieceStats.length; i++) {
            if (pieceStats[i] < lowest) { // determine the least frequently seen piece
                lowest = pieceStats[i];
                lowestIndex = i;
            }
        }
        dropRates[lowestIndex] = 0.35; // Give the least seen piece a 35% drop rate
        for (int i = 0; i < dropRates.length; i++) {
            if (i != lowestIndex) {
                dropRates[i] = 0.65 / 6.0; // Give the other pieces a 65/6 % drop rate
            }
        }
        return dropRates;
    }

    /**
     * List holding threshold values based on which a random number on (0,1)
     * will determine the type of piece to be dropped
     *
     * @param dropRates: double[dropRates] is returned from updateDropRates()
     */
    private double[] updateDropInterval(double[] dropRates) {
        double[] dropInterval = new double[numPieces];
        for (int i = 0; i < dropRates.length; i++) {
            double sum = 0;
            for (int j = 0; j <= i; j++) {
                sum += dropRates[j];
            }
            dropInterval[i] = sum;
        }
        return dropInterval;
    }

    /**
     * Determines piece to be dropped based on random number on (0,1) and
     * dropInterval list
     *
     * @param dropInterval : double[] returned from updateDropInterval
     * @param drop         : double, a random number on (0,1)
     */
    private int determineType(double[] dropInterval, double drop) {
        int type = 0;
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
        return type;
    }

    /**
     * Getter method for how many tetrominos have been generated of each type.
     *
     * @return Array of integers representing how many tetrominos have been
     * generated.
     */
    public int[] getPieceStats() {
        return pieceStats.clone();
    }


}
