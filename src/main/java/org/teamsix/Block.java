package org.teamsix;

/**
 * Block class is the blocks that make up the game pieces. Blocks store their
 * color, xPosition and yPosition all as integers. Get/set methods for all 3
 * instance variables
 */
public class Block {

    private int color = 0;
    private int xPosition;
    private int yPosition;
    private boolean isGhost;

    /**
     * Three parameter constructor for color, xPosition and yPosition.
     *
     * @param color, @param xPosition, @param yPosition
     */
    public Block(int color, int x, int y, boolean isGhost) {
        this.setXPosition(x);
        this.setYPosition(y);
        this.setColor(color);
        this.setIsGhost(isGhost);
    }

    /**
     * Block copy constructor
     *
     * @param block The block to be copied.
     */
    public Block(Block block) {
        this.color = block.getColor();
        this.xPosition = block.getXPosition();
        this.yPosition = block.getYPosition();
        this.isGhost = block.getIsGhost();
    }

    /**
     * Get method for color
     *
     * @return boolean representing if it is a ghost block or not
     */
    public boolean getIsGhost() {
        return this.isGhost;
    }

    /**
     * Setter method for whether a block is a ghost.
     *
     * @param isGhost Boolean indicating whether block is ghost or not.
     */
    public void setIsGhost(boolean isGhost) {
        this.isGhost = isGhost;
    }

    /**
     * Get method for color
     *
     * @return color
     */
    public int getColor() {
        return this.color;
    }

    /**
     * Set method for color
     *
     * @param color The color to set the block to. Should be in 0 to 6
     *              inclusive
     */
    public void setColor(int color) {
        if (color >= 0 && color < 7) {
            this.color = color;
        }
    }

    /**
     * Get method for xPosition
     *
     * @return xPosition
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Set method for xPosition
     *
     * @param x New xPosition
     */
    public void setXPosition(int x) {
        xPosition = x;
    }

    /**
     * Get method for yPosition
     *
     * @return yPosition
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Set method for yPosition
     *
     * @param y New yPosition
     */
    public void setYPosition(int y) {
        yPosition = y;
    }
}
