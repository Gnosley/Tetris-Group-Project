public class TetrominoO extends Tetromino{
	private static final int rows = 4;
	private static final int color = 4;

	private Block b1 = new Block(color,1,1);
	private Block b2 = new Block(color,2,1);
	private Block b3 = new Block(color,1,2);
	private Block b4 = new Block(color,2,2);
	private Block b5 = new Block(color,1,1);
	private Block b6 = new Block(color,2,1);
	private Block b7 = new Block(color,1,2);
	private Block b8 = new Block(color,2,2);
	private Block b9 = new Block(color,1,1);
	private Block b10 = new Block(color,2,1);
	private Block b11 = new Block(color,1,2);
	private Block b12 = new Block(color,2,2);
	private Block b13 = new Block(color,1,1);
	private Block b14 = new Block(color,2,1);
	private Block b15 = new Block(color,1,2);
	private Block b16 = new Block(color,2,2);
	
	private Block[] tetrominoO = new Block[rows];
	private Block[]tetrominoOcw = new Block[rows];
	private Block[] tetrominoO180 = new Block[rows];
	private Block[]tetrominoOccw = new Block[rows];
	
	private Block[][] stateList = new Block[rows][];
	
	private TetrominoO(){
		tetrominoO[0] = b1;
		tetrominoO[1] = b2;
		tetrominoO[2] = b3;
		tetrominoO[3] = b4;
		
		tetrominoOcw[0] = b5;
		tetrominoOcw[1] = b6;
		tetrominoOcw[2] = b7;
		tetrominoOcw[3] = b8;
		
		tetrominoO180[0] = b9;
		tetrominoO180[1] = b10;
		tetrominoO180[2] = b11;
		tetrominoO180[3] = b12;
		
		tetrominoOccw[0] = b13;
		tetrominoOccw[1] = b14;
		tetrominoOccw[2] = b15;
		tetrominoOccw[3] = b16;
		
		stateList[0] = tetrominoO;
		stateList[1] = tetrominoOcw;
		stateList[2] = tetrominoO180;
		stateList[3] = tetrominoOccw;
	}
}