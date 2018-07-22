import java.util.Random;
import java.util.Arrays;

public class Tetromino {
	private static Random rand = new Random();
	private int xReferencePosition;
	private int yReferencePosition;
	private Block[] tetrominoArray = new Block[4];
	private int size;
	private final int[][][] tetrominoData = {
			// {color, size} {block1 X, block1 Y} {block 2} {block 3} {block 4}
			
			// I tetromino
			{
				{0,4}, {0,1}, {1,1}, {2,1}, {3,1} 
			}
			// 0 tetromino
			{
				{1,4}, {1,1}, {1,2}, {2,1}, {2,2}
			}
			// T tetromino
			{
				{2,3}, {0,1}, {1,1}, {2,1}, {1,2}
			}
			// S tetromino
			{
				{3,3}, {0,1}, {1,1}, {1,0}, {2,0}
			}
			// Z tetromino
			{
				{4,3}, {0,0}, {1,0}, {1,1}, {2,1}
			}
			// J tetromino
			{
				{5,3}, {0,1}, {1,1}, {2,1}, {2,2}
			}
			// L tetromino
			{
				{6,3}, {0,2}, {0,1}, {1,1}, {2,1}
			}
		}

	/**
	 * Constructor for a new Tetromino when one is place. A randomized type of
	 * Tetromino is formed
	 * 
	 * @param x:
	 *            xcoordinate of reference position
	 * @param y:
	 *            ycoordinate of reference position
	 */
	public Tetromino(int x, int y) {

		type = rand.nextInt(7);
		size = tetrominoData[type][0][1];
		xReferencePosition = x;
		yReferencePosition = y;
		generateTetromino(type);

	}

	/**
	 * Copy constructor after each movement or movement check
	 * 
	 * @param tetro:
	 *            old Tetromino that needs to be copied
	 */
	public Tetromino(Tetromino tetro) {
		xReferencePosition = tetro.getXReference();
		yReferencePosition = tetro.getYReference();
		size = tetro.getSize();
		tetrominoData = tetro.tetrominoData;
		tetrominoArray = tetro.getBlockArray();

	}

	/**
	 * Generate the new tetromino thorough data from the tetrominoData array
	 * 
	 * @param type:
	 *            int of what type the tetromino is (i,o,t,s,z,j,l)
	 */
	public void generateTetromino(int type) {
		if (num < 0 || num > 6)
			num = 0;
		for (int i = 0; i < 4; i++) { // for loop to run through the 4 blocks within the tetromino
			tetrominoArray[i] = new Block(type, tetrominoData[type][i][0] + xReferencePosition,
					tetrominoData[type][i][1] + yReferencePosition);
		}
	}

	/**
	 * Accesses the private variable "xReferencePosition"
	 * 
	 * @return yReferencePosition: int of x index position on board
	 */
	public int getXReference() {
		return xReferencePosition;
	}

	/**
	 * Accesses the private variable "yReferencePosition"
	 * 
	 * @return yReferencePosition: int of y index position on board
	 */
	public int getYReference() {
		return yReferencePosition;
	}

	/**
	 * Accesses the private variable "size"
	 * 
	 * @return size: int of block array size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Decides what move is being asked to do via a character switch. Performs the
	 * method.
	 * 
	 * @param moveType:
	 *            char that indicates the action asked
	 */
	public Tetromino doMove(char moveType) {
		Tetromino movedTetromino = new Tetromino(this);
		switch (moveType) {
		case 'a': // right indication
		case 's': // down indication
		case 'd': // left indication
			movedTetromino.translate(moveType);
			break;
		case 'q': // CCW rotation indication
		case 'e': // CW rotation indication
			movedTetromino.rotate(moveType);
			break;
		}
		return movedTetromino;
	}

	/**
	 * Decides what translation is being asked to do via a character switch.
	 * Performs the method.
	 * 
	 * @param moveType:
	 *            char that indicates the direction of translation
	 */
	public void translate(char moveType) {
		switch (moveType) {
		case ('a'):
			xReferencePosition--;
			// move reference 1 left in the board array index
			break;
		case ('d'):
			xReferencePosition++;
			// move reference 1 right in the board array index
			break;
		case ('s'):
			yReferencePosition++;
			// move reference 1 down in the board array index
			break;
		}
	}

	/**
	 * Changes the rotation of the block
	 * 
	 * @param direction:
	 *            char, either 'q' or 'e' to be CCW or CW rotation
	 * @param tetromino:
	 *            Block[], information of current tetromino
	 * @return tetro.stateList[]: Block[] of new orientation
	 */
	private void rotate(char direction) {
		// Store tetrominos in 2x2, 3x3, and 4x4 with the block being rotated around in
		// the center.
		// then Int size = 2, 3, or 4 respectively
		// We can rotate counter clockwise by {x, y} -> {y, size - 1 - x}
		// We can rotate clockwise by {x, y} -> {size - 1 - y, x}
		int rotSize = tetro[type - 3][0][1] - 1; // for the function to rotate we need size - 1
		// Block[] tetrominoArray = {block, block, block, block};
		// then we can iterate through the blocks.
		for (Block block : tetrominoArray) {
			// get the x,y positions
			int x = block.getXPosition();
			int y = block.getYPosition();

			// get their position in the size x size matrix
			int newX = y - yReferencePosition; // subtract reference positions
			int newY = x - xReferencePosition; // for rotation maths to work.

			// counter clockwise
			if (direction == 'q') {
				newY = rotSize - newY;
			}

			// clockwise
			else if (direction == 'e') {
				newX = rotSize - newX;
			}

			// add back the reference position after the rotation
			newX += xReferencePosition;
			newY += yReferencePosition;

			// update the blocks position to the rotated positon
			block.setXPosition(newX);
			block.setYPosition(newY);
		}
	}

	/**
	 * Method to copy information of the private tetrominoArray
	 * 
	 * @return copy: Block[] used to extract information without a privacy leak
	 */
	public Block[] getBlockArray() {
		Block[] copy = Arrays.copyOf(tetrominoArray, tetrominoArray.length);
		return copy;
	}
}
