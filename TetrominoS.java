public class TetrominoS extends Tetromino{
	private static final int rows = 4;
	private static final int color = 6;

	private Block b1 = new Block(color,0,1);
	private Block b2 = new Block(color,1,1);
	private Block b3 = new Block(color,1,0);
	private Block b4 = new Block(color,2,0);
	private Block b5 = new Block(color,0,0);
	private Block b6 = new Block(color,0,1);
	private Block b7 = new Block(color,1,1);
	private Block b8 = new Block(color,1,2);
	private Block b9 = new Block(color,0,1);
	private Block b10 = new Block(color,1,1);
	private Block b11 = new Block(color,1,0);
	private Block b12 = new Block(color,2,0);
	private Block b13 = new Block(color,0,0);
	private Block b14 = new Block(color,0,1);
	private Block b15 = new Block(color,1,1);
	private Block b16 = new Block(color,1,2);
	
	private Block[] tetrominoS = new Block[rows];
	private Block[]tetrominoScw = new Block[rows];
	private Block[] tetrominoS180 = new Block[rows];
	private Block[]tetrominoSccw = new Block[rows];
	
	private Block[][] stateList = new Block[rows][];
	
	private TetrominoS(){
		tetrominoS[0] = b1;
		tetrominoS[1] = b2;
		tetrominoS[2] = b3;
		tetrominoS[3] = b4;
		
		tetrominoScw[0] = b5;
		tetrominoScw[1] = b6;
		tetrominoScw[2] = b7;
		tetrominoScw[3] = b8;
		
		tetrominoS180[0] = b9;
		tetrominoS180[1] = b10;
		tetrominoS180[2] = b11;
		tetrominoS180[3] = b12;
		
		tetrominoSccw[0] = b13;
		tetrominoSccw[1] = b14;
		tetrominoSccw[2] = b15;
		tetrominoSccw[3] = b16;
		
		stateList[0] = tetrominoS;
		stateList[1] = tetrominoScw;
		stateList[2] = tetrominoS180;
		stateList[3] = tetrominoSccw;
	}
}