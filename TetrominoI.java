public class TetrominoI extends Tetromino{
	private static final int rows = 4;
	private static final int color = 3;

	private Block b1 = new Block(color,0,1);
	private Block b2 = new Block(color,1,1);
	private Block b3 = new Block(color,2,1);
	private Block b4 = new Block(color,3,1);
	private Block b5 = new Block(color,2,0);
	private Block b6 = new Block(color,2,1);
	private Block b7 = new Block(color,2,2);
	private Block b8 = new Block(color,2,3);
	private Block b9 = new Block(color,0,2);
	private Block b10 = new Block(color,1,2);
	private Block b11 = new Block(color,2,2);
	private Block b12 = new Block(color,2,3);
	private Block b13 = new Block(color,1,0);
	private Block b14 = new Block(color,1,1);
	private Block b15 = new Block(color,1,2);
	private Block b16 = new Block(color,1,3);
	
	private Block[] tetrominoI = new Block[rows];
	private Block[]tetrominoIcw = new Block[rows];
	private Block[] tetrominoI180 = new Block[rows];
	private Block[]tetrominoIccw = new Block[rows];
	
	private Block[][] stateList = new Block[rows][];
	
	private TetrominoI(){
		tetrominoI[0] = b1;
		tetrominoI[1] = b2;
		tetrominoI[2] = b3;
		tetrominoI[3] = b4;
		
		tetrominoIcw[0] = b5;
		tetrominoIcw[1] = b6;
		tetrominoIcw[2] = b7;
		tetrominoIcw[3] = b8;
		
		tetrominoI180[0] = b9;
		tetrominoI180[1] = b10;
		tetrominoI180[2] = b11;
		tetrominoI180[3] = b12;
		
		tetrominoIccw[0] = b13;
		tetrominoIccw[1] = b14;
		tetrominoIccw[2] = b15;
		tetrominoIccw[3] = b16;
		
		
		stateList[0] = tetrominoI;
		stateList[1] = tetrominoIcw;
		stateList[2] = tetrominoI180;
		stateList[3] = tetrominoIccw;
	}
}