package TeamSix.logic;


import junit.framework.Assert;
import org.junit.Test;

public class TetrominoFactoryTest {
	@Test
	public void testGetTetrominoEasy(){
		TetrominoFactory generator = new TetrominoFactory("EASY", 0, 0); // Difficulty set to EASY for getTetromino method
		for (int i = 0; i <= 5005; i++){ // Large sample of tetrominos
			generator.getTetromino(0,0);
		}
		int[] pieceStats = generator.getPieceStats();
		for (int j = 0; j < pieceStats.length; j++){
			boolean expected = ((pieceStats[j] < 720) && (pieceStats[j] > 710)); // Because difficulty EASY, expect each piece within 5 of 715 (5005/7) times
			Assert.assertEquals("Number of pieces of a type not within specified range.",expected,true);
		}
	}
	@Test
	public void testGetTetromino(){
		TetrominoFactory generator = new TetrominoFactory("EASY", 0, 0);
		boolean expected = false;
		for (int i = 0; i < 7; i++){
			Tetromino t1 = generator.getTetromino(i);
			switch (i) {
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
			Assert.assertEquals("Unexpected type of tetromino generated.",expected,true);
		}
	}
	
	@Test
	public void testGetTetrominoMedium(){
		
		double stdDevSum = 0.0;
		for (int j = 0; j <= 500; j++){ // 500 samples of pieceStats
			TetrominoFactory generator = new TetrominoFactory("MEDIUM", 0, 0); // set difficulty to MEDIUM
			for (int i = 0; i<= 5005; i++){ // 5005 Tetrominos per sample
				generator.getTetromino(0,0); // generate random tetromino from MEDIUM distribution
			}
			double sumSquares = 0.0;
			int[] pieceStats = generator.getPieceStats();
			for (int i = 0; i< pieceStats.length; i++){ // calculate average standard deviation of the 500 samples
				sumSquares += (pieceStats[i] - 715)*(pieceStats[i] - 715);
			}
			sumSquares = sumSquares/(pieceStats.length -1);
			double stdDev = Math.sqrt(sumSquares);
			
			stdDevSum += stdDev;
		}
		double stdDevAvg = stdDevSum/500.0;
		boolean expected = (stdDevAvg < 27.0 && stdDevAvg > 25.0); // check if standard deviation within expected range
		Assert.assertEquals("Standard deviation not within expected range.",expected,true);
	}

	
	@Test
	public void testGetTetrominoHard(){
		double[] hardDistribution = { (2.5 / 28.0), (5.0 / 28.0),
										(8.0 / 28.0), (13.0 / 28.0),
										(18.0 / 28.0), (23.0 / 28.0),
										(28.0 / 28.0) };
										
		double stdDevSum = 0.0;							
		for (int j = 0; j <= 500; j++){ // 500 total samples of pieceStats
			TetrominoFactory generator = new TetrominoFactory("HARD", 0, 0); // set difficulty to HARD
			for (int i = 0; i<= 5005; i++){ // 5005 Tetrominos per sample
				generator.getTetromino(0,0); //generate random tetromino from HARD distribution
			}
			double sumSquares = 0.0;
			int[] pieceStats = generator.getPieceStats();
			for (int i = 0; i< pieceStats.length; i++){ //calculate average standard deviation of the 500 samples
				sumSquares += (pieceStats[i] - 715)*(pieceStats[i] - 715);
			}
			sumSquares = sumSquares/(pieceStats.length -1);
			double stdDev = Math.sqrt(sumSquares);
			
			stdDevSum += stdDev;
		}
		double stdDevAvg = stdDevSum/500.0;
		boolean expected = (stdDevAvg > 225.0 && stdDevAvg < 228.0); // check if standard deviation within expected range
		Assert.assertEquals("Standard deviation not within expected range.",expected,true);
	}
		
}
