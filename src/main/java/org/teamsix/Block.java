package org.teamsix;

/** Block class is the blocks that make up the game pieces. Blocks store their color,
	xPosition and yPosition all as integers. Get/set methods for all 3 instance variables	*/
public class Block{
    private int color = 0;
    private int xPosition;
    private int yPosition;
    private boolean isGhost;

/** Three parameter constructor for color, xPosition and yPosition.
	@param color, @param xPosition, @param yPosition	*/
    public Block(int color, int x, int y, boolean isGhost) {
        this.setXPosition(x);
        this.setYPosition(y);
        this.setColor(color);
        this.setIsGhost(isGhost);
    }

/** Block copy constructor
	@param block	*/
    public Block(Block block) {
        this.color = block.getColor();
        this.xPosition = block.getXPosition();
        this.yPosition = block.getYPosition();
        this.isGhost = block.getIsGhost();
    }
    /** Set method for color
      @param color	*/
    public void setColor(int color) {
        if (color >= 0 && color < 7) {
            this.color = color;
        }
    }
    /** Get method for color
        @return boolean representing if it is a ghost block or not*/
    public boolean getIsGhost() {
        return this.isGhost;
    }

    public void setIsGhost(boolean isGhost) {
        this.isGhost = isGhost;
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
