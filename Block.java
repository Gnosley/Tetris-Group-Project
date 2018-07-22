/** Block class is the blocks that make up the game pieces. Blocks store their color,
	xPosition and yPosition all as integers. Get/set methods for all 3 instance variables	*/
public class Block{
    private int color = 0;
    private int xPosition;
    private int yPosition;

/** Three parameter constructor for color, xPosition and yPosition.
	@param color, @param xPosition, @param yPosition	*/
    public Block(int color, int x, int y) {
        xPosition = x;
        yPosition = y;
        this.color = color;
    }

/** Block copy constructor
	@param block	*/
    public Block(Block block) {
        this.color = block.getColor();
        this.xPosition = block.getXPosition();
        this.yPosition = block.getYPosition();
    }
/** Set method for color
	@param color	*/
    public void setColor(int color) {
        this.color = color;
    }
/** Get method for color
	@return color*/
    public int getColor() {
        return this.color;
    }
/** Set method for xPosition
	@param x New xPosition	*/
    public void setXPosition(int x) {
        xPosition = x;
    }
/** Set method for yPosition
	@param y New yPosition	*/
    public void setYPosition(int y) {
        yPosition = y;
    }
/** Get method for xPosition
	@return xPosition	*/
    public int getXPosition() {
        return xPosition;
    }
/** Get method for yPosition
	@return yPosition	*/
    public int getYPosition() {
        return yPosition;
    }
}
