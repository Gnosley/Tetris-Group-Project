public class TetrominoL extends Tetromino{
	private static final int rows = 4;
	private static final int color = 9;

	private Block b1 = new Block(color,0,2);
	private Block b2 = new Block(color,0,1);
	private Block b3 = new Block(color,1,1);
	private Block b4 = new Block(color,2,1);
	private Block b5 = new Block(color,0,0);
	private Block b6 = new Block(color,1,0);
	private Block b7 = new Block(color,1,1);
	private Block b8 = new Block(color,1,2);
	private Block b9 = new Block(color,2,0);
	private Block b10 = new Block(color,2,1);
	private Block b11 = new Block(color,1,1);
	private Block b12 = new Block(color,0,1);
	private Block b13 = new Block(color,1,0);
	private Block b14 = new Block(color,1,1);
	private Block b15 = new Block(color,1,2);
	private Block b16 = new Block(color,2,2);
	
	private Block[] tetrominoL = new Block[rows];
	private Block[]tetrominoLcw = new Block[rows];
	private Block[] tetrominoL180 = new Block[rows];
	private Block[]tetrominoLccw = new Block[rows];
	
	private Block[][] stateList = new Block[rows][];
	
	private TetrominoL(){
		tetrominoL[0] = b1;
		tetrominoL[1] = b2;
		tetrominoL[2] = b3;
		tetrominoL[3] = b4;
		
		tetrominoLcw[0] = b5;
		tetrominoLcw[1] = b6;
		tetrominoLcw[2] = b7;
		tetrominoLcw[3] = b8;
		
		tetrominoL180[0] = b9;
		tetrominoL180[1] = b10;
		tetrominoL180[2] = b11;
		tetrominoL180[3] = b12;
		
		tetrominoLccw[0] = b13;
		tetrominoLccw[1] = b14;
		tetrominoLccw[2] = b15;
		tetrominoLccw[3] = b16;
		
		stateList[0] = tetrominoL;
		stateList[1] = tetrominoLcw;
		stateList[2] = tetrominoL180;
		stateList[3] = tetrominoLccw;
	}
}