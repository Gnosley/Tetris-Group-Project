public class TetrominoT extends Tetromino{
	private static final int rows = 4;
	private static final int color = 5;

	private Block b1 = new Block(color,0,1);
	private Block b2 = new Block(color,1,1);
	private Block b3 = new Block(color,2,1);
	private Block b4 = new Block(color,1,2);
	private Block b5 = new Block(color,1,0);
	private Block b6 = new Block(color,1,1);
	private Block b7 = new Block(color,1,2);
	private Block b8 = new Block(color,0,1);
	private Block b9 = new Block(color,0,1);
	private Block b10 = new Block(color,1,1);
	private Block b11 = new Block(color,2,1);
	private Block b12 = new Block(color,1,0);
	private Block b13 = new Block(color,1,0);
	private Block b14 = new Block(color,1,1);
	private Block b15 = new Block(color,1,2);
	private Block b16 = new Block(color,2,1);
	
	private Block[] tetrominoT = new Block[rows];
	private Block[]tetrominoTcw = new Block[rows];
	private Block[] tetrominoT180 = new Block[rows];
	private Block[]tetrominoTccw = new Block[rows];
	
	private Block[][] stateList = new Block[rows][];
	
	private TetrominoT(){
		tetrominoT[0] = b1;
		tetrominoT[1] = b2;
		tetrominoT[2] = b3;
		tetrominoT[3] = b4;
		
		tetrominoTcw[0] = b5;
		tetrominoTcw[1] = b6;
		tetrominoTcw[2] = b7;
		tetrominoTcw[3] = b8;
		
		tetrominoT180[0] = b9;
		tetrominoT180[1] = b10;
		tetrominoT180[2] = b11;
		tetrominoT180[3] = b12;
		
		tetrominoTccw[0] = b13;
		tetrominoTccw[1] = b14;
		tetrominoTccw[2] = b15;
		tetrominoTccw[3] = b16;
		
		stateList[0] = tetrominoT;
		stateList[1] = tetrominoTcw;
		stateList[2] = tetrominoT180;
		stateList[3] = tetrominoTccw;
	}
}