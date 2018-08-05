package org.teamsix;

import java.util.Random;
import java.util.Arrays;
import java.lang.Math;
import java.util.stream.*;

public class Tetromino {
    // {color, size} {block1} {block 2} {block 3} {block 4}
    private final int[][][] tetrominoData = {
        // I-Tetromino
        {
            {0, 4}, {1, 0}, {1, 1}, {1, 2}, {1, 3}
        },

        // O-Tetromino
        {
            {1, 2}, {0, 0}, {0, 1}, {1, 0}, {1, 1}
        },

        // T-Tetromino
        {
            {2, 3}, {1, 0}, {1, 1}, {0, 1}, {2, 1}
        },

        // S-Tetromino
        {
            {3, 3}, {1, 0}, {1, 1}, {2, 1}, {2, 2}
        },

        // Z-Tetromino
        {
            {4, 3}, {0, 0}, {1, 0}, {1, 1}, {2, 1}
        },

        // J-Tetromino
        {
            {5, 3}, {2, 0}, {1, 0}, {1, 1}, {1, 2}
        },

        // L-Tetromino
        {
            {6, 3}, {2, 2}, {1, 0}, {1, 1}, {1, 2}
        }
    };
	private final int numPieces = 7;
    private static Random rand = new Random();
    private Block[] tetrominoArray = new Block[4];
    private int xReferencePosition; // x coordinate in relation to the board
    private int yReferencePosition; // y coordinate in relation to the board
    private int size; // size of grid needed to hold tetromino
    private int type; //the type of tetromino
    private int rotation;
	private int[] pieceStats = new int[numPieces];

    /**
     * Constructor for a new Tetromino when one is called for. A randomized type of
     * Tetromino is formed for the first 15 game pieces, after that the distribution
	 * is altered to increase drop chances of the least frequently seen (so far) pieces.
     *
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */

    public Tetromino(int xRef, int yRef) {
		int total = IntStream.of(pieceStats).sum();
		xReferencePosition = xRef;
		yReferencePosition = yRef;
		if ( total < 15 ){ // Generate from even distribution when less than 15 pieces played.
			type = rand.nextInt(numPieces);
			size = tetrominoData[type][0][1];						
			tetrominoArray = generateTetrominoArray(type);
			pieceStats[type] += 1;
		}
		
		if ( total >= 15) { // Generate from skewed distribution when more than 15 pieces played.
			double[] dropRates = new double[numPieces];
			double[] dropInterval = new double[numPieces];
			int lowest = pieceStats[0];
			int lowestIndex = 0;
			for (int i = 0; i < pieceStats.length; i++){
				if ( pieceStats[i] < lowest ){ // determine the least frequently seen piece
					lowest = pieceStats[i];
					lowestIndex = i;
				}
			}
			dropRates[lowestIndex] = 0.35; // Give the least seen piece a 35% drop rate
			for (int i = 0; i < dropRates.length; i++){
				if ( i != lowestIndex ) {
					dropRates[i] = 0.65/6.0; // Give the other pieces a 65/6 % drop rate
				}
			}
			
			double drop = Math.random(); // Generate a random number on (0,1)
			// Set the dropInterval list with the skewed distribution
			for ( int i = 0; i < dropRates.length; i++){
				double sum = 0;
				for ( int j = 0; j <= i; j++){
					sum += dropRates[j] ;
				}
				dropInterval[i] = sum;
			}
			// Depending on the value of the random number drop, select the proper piece type
			if ( drop <= dropInterval[0] ) {
				type = 0;
			}
			if ( drop > dropInterval[dropInterval.length - 2] ){
				type = dropInterval.length - 1;
			}
			for (int i = 0; i < (dropInterval.length - 2); i++){
				if ( drop > dropInterval[i] && drop <= dropInterval[i+1]){
					type = i + 1;
				}
			}
			size = tetrominoData[type][0][1];						
			tetrominoArray = generateTetrominoArray(type);
			pieceStats[type] += 1;
		}
		
    }
	
    /**
     * Constructor for setting an old tetromino back at the start after storing
     * @param xRef:
     * 			x-coordinate of reference position
     * @param yRef:
     * 			y-coordinate of reference position
     * @param tetType
     * 			type of tetromino that was stored
     */
	public Tetromino (int xRef, int yRef, int tetType) {
		type = tetType;
		size = tetrominoData[tetType][0][1];
        xReferencePosition = xRef;
        yReferencePosition = yRef;
        tetrominoArray = generateTetrominoArray(tetType);
	}

    /**
     * Copy constructor after each movement or movement check
     *
     * @param tetromino:
     *            old Tetromino that needs to be copied
     */
    public Tetromino(Tetromino tetromino) {
        this.type = tetromino.getType();
        this.xReferencePosition = tetromino.getXReference();
        this.yReferencePosition = tetromino.getYReference();
        this.tetrominoArray = tetromino.getBlockArray();
        this.size = tetromino.getSize();
        this.rotation = tetromino.rotation;
    }

    public Tetromino(Tetromino tetromino, boolean isGhost) {
        this.type = tetromino.getType();
        this.xReferencePosition = tetromino.getXReference();
        this.yReferencePosition = tetromino.getYReference();
        this.tetrominoArray = tetromino.getBlockArray();
        this.size = tetromino.getSize();
        this.convertGhostType(isGhost);
        this.rotation = tetromino.rotation;
    }

    private void convertGhostType(boolean toGhost) {
        for(Block block:this.tetrominoArray) {
            if(block != null){
                block.setIsGhost(toGhost);
            }
        }
    }

    private Block[] generateTetrominoArray(int type) {
        return generateTetrominoArray(type, false);
    }

    /**
     * Generate the new tetromino thorough data from the tetrominoData array
     *
     * @param type:
     *            int of what type the tetromino is (i,o,t,s,z,j,l)
     */
    private Block[] generateTetrominoArray(int type, boolean isGhost) {
        if (type < 0 || type > 6) type = 0; // only 7 tetromino types

        int color = tetrominoData[type][0][0];
        Block[] tetrominoArray = new Block[4];

        // start iteration at 1 so that we skip color/size info
        for (int i=1; i< tetrominoData[type].length; i++) {
            int x = tetrominoData[type][i][0];
            int y = tetrominoData[type][i][1];

            Block block = new Block(color,
                                    xReferencePosition + x,
                                    yReferencePosition + y,
                                    isGhost);
            tetrominoArray[i-1] = block;
        }
        return tetrominoArray;
    }

    /**
	 * Changes the rotation of the block
	 *
	 * @param direction:
	 *            char, either 'q' or 'e' to be CCW or CW rotation
	 */
    private void rotate(char direction) {
        // We can rotate counter clockwise by {x, y} -> {y, size - 1 - x}
        // We can rotate clockwise by {x, y} -> {size - 1 - y, x}
        int rotSize = size - 1; // for the function to rotate we need size - 1
        for(Block block:tetrominoArray) {
            int x = block.getXPosition();
            int y = block.getYPosition();
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
            newX += xReferencePosition;
            newY += yReferencePosition;
            block.setXPosition(newX);
            block.setYPosition(newY);
        }
    }

    /**
	 * Decides what translation is being asked to do via a character switch.
	 * Performs the method via setting the block position to the old position + the movement
	 *
	 * @param moveType:
	 *            char that indicates the direction of translation
	 */
    private void move(char direction) {
        int xMovement = 0;
        int yMovement = 0;

        switch(direction) {
            case 'a': xMovement = -1; break;    //change is moving the x coordinate 1 left
            case 's': yMovement = 1; break;     //change is moving the x coordinate 1 right
            case 'd': xMovement = 1; break;     //change is moving the y coordinate 1 down
        }

        xReferencePosition += xMovement;        //apply changes to the reference
        yReferencePosition += yMovement;

        for(Block block:tetrominoArray) {       //set position of each block
            block.setXPosition(block.getXPosition() + xMovement);
            block.setYPosition(block.getYPosition() + yMovement);
        }
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
        switch(moveType) {
            case 'a':   //left indication
            case 's':   //down indication
            case 'd':   //right indication
                        movedTetromino.move(moveType); break;
            case 'q':   //CCW rotation indication
            case 'e':   //CW rotation indiciation
                        movedTetromino.rotate(moveType); break;
        }
        return movedTetromino;
    }

    /**
	 * Method to copy information of the private tetrominoArray
	 *
	 * @return copy: Block[] copy constructor to extract information without a privacy leak
	 */
    public Block[] getBlockArray() {
        Block[] copy = new Block[tetrominoArray.length];
        for(int i=0; i < tetrominoArray.length; i++) {
            copy[i] = new Block(tetrominoArray[i]);
        }
        return copy;
    }

    /**
	 * Accesses the private variable "xReferencePosition"
	 *
	 * @return xReferencePosition: int of x index position on board
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
    *Accesses the private variable "size"
    *
    *@return size: int of block array size
    */
    public int getSize() {
        return size;
    }

    /**
     * Accesses the private variable "type"
     * @return type: int of tetromino type
     */
	public int getType() {
		return type;
	}

    public int getRotation() {
        return this.rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }


}