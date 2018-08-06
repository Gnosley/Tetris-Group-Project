import java.util.Arrays;
import java.lang.Math;
import java.util.stream.*;

public abstract class Tetromino {
	private final int numPieces = 7;

    protected int[][] tetrominoData;

    protected Block[] tetrominoArray = new Block[4];
    protected int xReferencePosition; // x coordinate in relation to the board
    protected int yReferencePosition; // y coordinate in relation to the board
    protected int size; // size of grid needed to hold tetromino
    protected int type; //the type of tetromino
    protected int rotation;
    protected boolean isGhost = false;

    // http://tetris.wikia.com/wiki/SRS#Wall_Kicks
    protected int[][][] wallKickData = {
        { {0, 0}, {-1, 0}, {-1, 1}, {0, -2}, {-1, -2} },
        { {0, 0}, {1, 0}, {1, -1}, {0, 2}, {1, 2} },
        { {0, 0}, {1, 0}, {1, 1}, {0, -2}, {1, -2} },
        { {0, 0}, {-1, 0}, {-1, -1}, {0, 2}, {-1, 2} }
    };

    /**
     * Constructor for a new Tetromino when one is called for.
     * @param xRef:
     *            x-coordinate of reference position
     * @param yRef:
     *            y-coordinate of reference position
     */
    public Tetromino(int xRef, int yRef, boolean isGhost) {
        xReferencePosition = xRef;
        yReferencePosition = yRef;
        this.isGhost = isGhost;

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
        this(tetromino, tetromino.getIsGhost());
    }

    /**
     * Copy constructor for the tetromino after each ghosting
     * @param tetromino: Tetromino
     * @param isGhost: boolean, either is or isn't a ghost block
     */
    public Tetromino(Tetromino tetromino, boolean convertToGhost) {
        this.type = tetromino.getType();
        this.xReferencePosition = tetromino.getXReference();
        this.yReferencePosition = tetromino.getYReference();
        this.tetrominoArray = tetromino.getBlockArray();
        this.size = tetromino.getSize();
        this.isGhost = convertToGhost;
        // Don't need to convert if it already is already wanted type.
        if (tetromino.getIsGhost() != convertToGhost) {
            this.convertGhostType(this.isGhost);
        }
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
        // update tetromino rotatation
        if(direction == 'e') {
            rotation += 90;
        }
        else if(direction == 'q') {
            rotation -= 90;
        }

        // limit rotation
        if(rotation == 360 || rotation == -360) {

            rotation = 0;
        }
    }

    protected void move(char direction) {
        move(direction, 1);
    }

    /**
	 * Decides what translation is being asked to do via a character switch.
	 * Performs the method via setting the block position to the old position + the movement
	 *
	 * @param moveType:
	 *            char that indicates the direction of translation
	 */
    protected void move(char direction, int distance) {
        int xMovement = 0;
        int yMovement = 0;

        switch(direction) {
            case 'a': xMovement = -distance; break;    //change is moving the x coordinate 1 left
            case 's': yMovement = distance; break;     //change is moving the x coordinate 1 right
            case 'd': xMovement = distance; break;     //change is moving the y coordinate 1 down
        }

        xReferencePosition += xMovement;        //apply changes to the reference
        yReferencePosition += yMovement;

        for(Block block:tetrominoArray) {       //set position of each block
            block.setXPosition(block.getXPosition() + xMovement);
            block.setYPosition(block.getYPosition() + yMovement);
        }
    }

    public void doMove(char moveType) {
        doMove(moveType, 0); // do default move.
    }

    /**
	 * Decides what move is being asked to do via a character switch. Performs the
	 * method.
	 *
	 * @param moveType:
	 *            char that indicates the action asked
     */
    public void doMove(char moveType, int testNum) {
        switch(moveType) {
            case 'a':   //left indication
            case 's':   //down indication
            case 'd':   //right indication
                        this.move(moveType); return;
            case 'q':   //CCW rotation indication
            case 'e':   //CW rotation indiciation
                        this.rotate(moveType, testNum); break;
        }
    }

    // http://tetris.wikia.com/wiki/SRS#Wall_Kicks
    protected void rotate(char moveType, int testNum) {
        int preR = rotationToState(rotation);
        rotate(moveType);
        int postR = rotationToState(rotation);

        int xMovement;
        int yMovement;

        switch(testNum) {
        default: return;
        case 1:
        case 2:
        case 3:
        case 4: break;
        }


        int rowNum = 0;
        if ((preR == 1 && postR == 0) || (preR == 0 && postR == 1)) {
            rowNum = 0;
        }
        else if ((preR == 1 && postR == 2) || (preR == 2 && postR == 1)) {
            rowNum = 1;
        }
        else if ((preR == 2 && postR == 3) || (preR == 3 && postR == 2)) {
            rowNum = 2;
        }
        else if ((preR == 3 && postR == 0) || (preR == 0 && postR == 3)) {
            rowNum = 3;
        }

        xMovement = this.wallKickData[rowNum][testNum][0];
        yMovement = this.wallKickData[rowNum][testNum][1];

        if (moveType == 'q') {
            xMovement *= -1;
            yMovement *= -1;
        }

        move('d', xMovement);
        move('s', -yMovement);

    }

    private int rotationToState(int rotation) {
        switch(rotation) {
        case 0: return 0;
        case 90: return 1;
        case 180: return 2;
        case 270: return 3;
        case -270: return 1;
        case -180: return 2;
        case -90: return 3;
        }
        return -1;
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

    public boolean getIsGhost() {
        return this.isGhost;
    }


}
