package TeamSix.logic;

import junit.framework.Assert;
import org.junit.Test;


/**
 * Provides tests for the generation of tetrominos via TetrominoFactory
 */
public class TetrominoFactoryTest {

    @Test
    // test the generation distribution of easy difficulty.
    public void testGetTetrominoEasy() {

        // Difficulty set to EASY for getTetromino method
        TetrominoFactory generator = new TetrominoFactory("EASY", 0, 0);

        // Large sample of tetrominos
        for (int i = 0; i < 7000; i++) {
            generator.getTetromino(0, 0);
        }

        int[] pieceStats = generator.getPieceStats();

        for (int j = 0; j < pieceStats.length; j++) {
            // Because difficulty EASY, expect each piece within 10 of 1000
            // (7000/7) times
            boolean expected = ((pieceStats[j] < 1010) && (pieceStats[j] > 990));
            Assert.assertEquals(
                    "Number of pieces of a type not within specified"
                            + " range. This may be a very unlucky run. "
                            + "Please run the test again to confirm.",
                    true, expected);
        }
    }

    @Test
    // test the factory returns correct type of Tetromino.
    public void testGetTetromino() {
        TetrominoFactory generator = new TetrominoFactory("EASY", 0, 0);
        boolean expected = false;
        for (int i = 0; i < 7; i++) {
            Tetromino t1 = generator.getTetromino(i);
            switch(i) {
                case 0:
                    expected = (t1 instanceof ITetromino);
                    break;
                case 1:
                    expected = (t1 instanceof OTetromino);
                    break;
                case 2:
                    expected = (t1 instanceof TTetromino);
                    break;
                case 3:
                    expected = (t1 instanceof STetromino);
                    break;
                case 4:
                    expected = (t1 instanceof ZTetromino);
                    break;
                case 5:
                    expected = (t1 instanceof JTetromino);
                    break;
                case 6:
                    expected = (t1 instanceof LTetromino);
                    break;
            }
            Assert.assertEquals("Unexpected type of tetromino generated.", true,
                    expected);
        }
    }

    @Test
    // Test the distribution for medium generation.
    public void testGetTetrominoMedium() {

        double stdDevSum = 0.0;
        for (int j = 0; j <= 500; j++) { // 500 samples of pieceStats

            // set difficulty to MEDIUM
            TetrominoFactory generator = new TetrominoFactory("MEDIUM", 0, 0);

            for (int i = 0; i <= 5005; i++) { // 5005 Tetrominos per sample
                // generate random tetromino from MEDIUM distribution
                generator.getTetromino(0, 0);
            }

            double sumSquares = 0.0;
            int[] pieceStats = generator.getPieceStats();

            // calculate average standard deviation of the 500 samples
            for (int i = 0; i < pieceStats.length; i++) {
                sumSquares += (pieceStats[i] - 715) * (pieceStats[i] - 715);
            }
            sumSquares = sumSquares / (pieceStats.length - 1);
            double stdDev = Math.sqrt(sumSquares);

            stdDevSum += stdDev;
        }
        double stdDevAvg = stdDevSum / 500.0;

        // check if standard deviation within expected range
        boolean expected = (stdDevAvg < 27.0 && stdDevAvg > 25.0);
        Assert.assertEquals(
                "Standard deviation not within expected range. "
                        + "This may be a rare occurrence. "
                        + "Please run the test again to be sure.",
                true, expected);
    }

    @Test
    // Test the distribution for hard generation.
    public void testGetTetrominoHard() {
        double[] hardDistribution = { (2.5 / 28.0), (5.0 / 28.0), (8.0 / 28.0),
                (13.0 / 28.0), (18.0 / 28.0), (23.0 / 28.0), (28.0 / 28.0) };

        double stdDevSum = 0.0;

        for (int j = 0; j <= 500; j++) { // 500 total samples of pieceStats

            // set difficulty to HARD
            TetrominoFactory generator = new TetrominoFactory("HARD", 0, 0);

            for (int i = 0; i <= 5005; i++) { // 5005 Tetrominos per sample
                // generate random tetromino from HARD distribution
                generator.getTetromino(0, 0);
            }

            double sumSquares = 0.0;
            int[] pieceStats = generator.getPieceStats();

            // calculate average standard deviation of the 500 samples
            for (int i = 0; i < pieceStats.length; i++) {
                sumSquares += (pieceStats[i] - 715) * (pieceStats[i] - 715);
            }

            sumSquares = sumSquares / (pieceStats.length - 1);
            double stdDev = Math.sqrt(sumSquares);

            stdDevSum += stdDev;
        }
        double stdDevAvg = stdDevSum / 500.0;
        // check if standard deviation within expected range
        boolean expected = (stdDevAvg > 225.0 && stdDevAvg < 228.0);
        Assert.assertEquals(
                "Standard deviation not within expected range. "
                        + "This may be a rare occurrence. "
                        + "Please run the test again to be sure.",
                true, expected);
    }

}
