public class TetrominoZ extends Tetromino{
	private static final int rows = 4;
	private static final int color = 7;

	private Block b1 = new Block(color,0,0);
	private Block b2 = new Block(color,1,0);
	private Block b3 = new Block(color,1,1);
	private Block b4 = new Block(color,2,1);
	private Block b5 = new Block(color,2,0);
	private Block b6 = new Block(color,2,1);
	private Block b7 = new Block(color,1,1);
	private Block b8 = new Block(color,1,2);
	private Block b9 = new Block(color,0,0);
	private Block b10 = new Block(color,1,0);
	private Block b11 = new Block(color,1,1);
	private Block b12 = new Block(color,2,1);
	private Block b13 = new Block(color,2,0);
	private Block b14 = new Block(color,2,1);
	private Block b15 = new Block(color,1,1);
	private Block b16 = new Block(color,1,2);
	
	private Block[] tetrominoZ = new Block[rows];
	private Block[]tetrominoZcw = new Block[rows];
	private Block[] tetrominoZ180 = new Block[rows];
	private Block[]tetrominoZccw = new Block[rows];
	
	private Block[][] stateList = new Block[rows][];
	
	private TetrominoZ(){
		tetrominoZ[0] = b1;
		tetrominoZ[1] = b2;
		tetrominoZ[2] = b3;
		tetrominoZ[3] = b4;
		
		tetrominoZcw[0] = b5;
		tetrominoZcw[1] = b6;
		tetrominoZcw[2] = b7;
		tetrominoZcw[3] = b8;
		
		tetrominoZ180[0] = b9;
		tetrominoZ180[1] = b10;
		tetrominoZ180[2] = b11;
		tetrominoZ180[3] = b12;
		
		tetrominoZccw[0] = b13;
		tetrominoZccw[1] = b14;
		tetrominoZccw[2] = b15;
		tetrominoZccw[3] = b16;
		
		stateList[0] = tetrominoZ;
		stateList[1] = tetrominoZcw;
		stateList[2] = tetrominoZ180;
		stateList[3] = tetrominoZccw;
	}
}