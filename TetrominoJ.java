public class TetrominoJ extends Tetromino{
	private static final int rows = 4;
	private static final int color = 8;

	private Block b1 = new Block(color,0,1);
	private Block b2 = new Block(color,1,1);
	private Block b3 = new Block(color,2,1);
	private Block b4 = new Block(color,2,2);
	private Block b5 = new Block(color,1,0);
	private Block b6 = new Block(color,1,1);
	private Block b7 = new Block(color,1,2);
	private Block b8 = new Block(color,0,2);
	private Block b9 = new Block(color,0,0);
	private Block b10 = new Block(color,0,1);
	private Block b11 = new Block(color,1,1);
	private Block b12 = new Block(color,2,1);
	private Block b13 = new Block(color,2,0);
	private Block b14 = new Block(color,1,0);
	private Block b15 = new Block(color,1,1);
	private Block b16 = new Block(color,1,2);
	
	private Block[] tetrominoJ = new Block[rows];
	private Block[]tetrominoJcw = new Block[rows];
	private Block[] tetrominoJ180 = new Block[rows];
	private Block[]tetrominoJccw = new Block[rows];
	
	private Block[][] stateList = new Block[rows][];
	
	private TetrominoJ(){
		tetrominoJ[0] = b1;
		tetrominoJ[1] = b2;
		tetrominoJ[2] = b3;
		tetrominoJ[3] = b4;
		
		tetrominoJcw[0] = b5;
		tetrominoJcw[1] = b6;
		tetrominoJcw[2] = b7;
		tetrominoJcw[3] = b8;
		
		tetrominoJ180[0] = b9;
		tetrominoJ180[1] = b10;
		tetrominoJ180[2] = b11;
		tetrominoJ180[3] = b12;
		
		tetrominoJccw[0] = b13;
		tetrominoJccw[1] = b14;
		tetrominoJccw[2] = b15;
		tetrominoJccw[3] = b16;
		
		stateList[0] = tetrominoJ;
		stateList[1] = tetrominoJcw;
		stateList[2] = tetrominoJ180;
		stateList[3] = tetrominoJccw;
	}
}