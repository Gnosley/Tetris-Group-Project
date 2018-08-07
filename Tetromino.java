
import java.util.Arrays;
import java.lang.Math;
import java.util.stream.*;

public class Tetromino {
	private final int numPieces = 7;
    
    protected int[][] tetrominoData;
    
    protected Block[] tetrominoArray = new Block[4];
    protected int xReferencePosition; // x coordinate in relation to the board
    protected int yReferencePosition; // y coordinate in relation to the board
    protected int size; // size of grid needed to hold tetromino
    protected int type; //the type of tetromino
    protected int rotation;

    /**
     * Constructor for a new Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */

    public Tetromino(int xRef, int yRef) {
		xReferencePosition = xRef;
		yReferencePosition = yRef;
    }

     /**
     * Generate the new tetromino thorough individulized Tetromino data
     *
     * @param isGhost:
     *            boolean of if the tetromino is a ghost type or not
     */
    public Block[] generateTetrominoArray(boolean isGhost) {
        int color = getType();
        Block[] tetrominoArray = new Block[4];

        // start iteration at 0 
        for (int i=0; i< tetrominoData.length; i++) {
            int x = tetrominoData[i][0];
            int y = tetrominoData[i][1];

            Block block = new Block(color,
                                    xReferencePosition + x,
                                    yReferencePosition + y,
                                    isGhost);
            tetrominoArray[i] = block;
        }
        return tetrominoArray;
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

    /**
     * Copy constructor for the tetromino after each ghosting
     * @param tetromino: Tetromino 
     * @param isGhost: boolean, either is or isn't a ghost block
     */
    public Tetromino(Tetromino tetromino, boolean isGhost) {
        this.type = tetromino.getType();
        this.xReferencePosition = tetromino.getXReference();
        this.yReferencePosition = tetromino.getYReference();
        this.tetrominoArray = tetromino.getBlockArray();
        this.size = tetromino.getSize();
        this.convertGhostType(isGhost);
        this.rotation = tetromino.rotation;
    }

    /**
     * Changes a tetrominoArray completely into ghost blocks or out of 
     * ghost blocks
     * @param toGhost: boolean, either is or is not a ghost block
     */
    protected void convertGhostType(boolean toGhost) {
        for(Block block:this.tetrominoArray) {
            if(block != null){
                block.setIsGhost(toGhost);
            }
        }
    }
    
    /**
	 * Changes the rotation of the block
	 *
	 * @param direction:
	 *            char, either 'q' or 'e' to be CCW or CW rotation
	 */
    protected void rotate(char direction) {
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
    protected void move(char direction) {
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
	 * Method to copy information of the protected tetrominoArray
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
     * Sets the tetrominoArray from the subclasses
     * @param transferArray: Block []
     */
    public void setBlockArray(Block[] transferArray) {
        for (int i =0; i<transferArray.length; i++) {
            tetrominoArray[i] = new Block(transferArray[i]);
        }
    }

    /**
	 * Accesses the protected variable "xReferencePosition"
	 *
	 * @return xReferencePosition: int of x index position on board
	 */
    public int getXReference() {
        return xReferencePosition;
    }

    /**
	 * Accesses the protected variable "yReferencePosition"
	 *
	 * @return yReferencePosition: int of y index position on board
	 */
    public int getYReference() {
        return yReferencePosition;
    }

    /**
    *Accesses the protected variable "size"
    *
    *@return size: int of block array size
    */
    public int getSize() {
        return size;
    }
     
    /**
     * Accesses the protected variable "type"
     * @return type: int of tetromino type
     */
	public int getType() {
		return type;
	}
 
	/**
	 * Accesses the protected variable "rotation"
	 * @return rotation: int of rotation state
	 */
    public int getRotation() {
        return this.rotation;
    }

    /**
     * Sets the rotation state of the tetromino
     * @param rotation: int of wanted rotation state
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }


}

		