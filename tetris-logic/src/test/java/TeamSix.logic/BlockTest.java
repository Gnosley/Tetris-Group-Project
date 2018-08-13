package TeamSix.logic;

import junit.framework.Assert;
import org.junit.Test;

public class BlockTest {

    //tests for correct y block position
    @Test
    public void testGetBlockYPositionGetsBlockYPosition() {
        Block b1 = new Block(0, 0, 3, false);
        Assert.assertEquals("getYPosition does not return yPosition", 3, b1.getYPosition());
        Block b2 = new Block(0, 0, 20, false);
        Assert.assertEquals("getYPosition does not return yPosition", 20, b2.getYPosition());
    }

    //test for the correct x block position
    @Test
    public void testGetBlockXPositionGetsBlockXPosition() {
        Block b1 = new Block(0, 0, 0, false);
        Assert.assertEquals("getXPosition does not return xPosition", 0, b1.getXPosition());
        Block b2 = new Block(0, 4, 0, false);
        Assert.assertEquals("getXPosition does not return xPosition", 4, b2.getXPosition());
    }

    //tests for the correct colour of the block
    @Test
    public void testGetBlockColorGetsBlockColor() {
        Block b1 = new Block(2, 0, 0, false);
        Assert.assertEquals("getColor does not return color", 2, b1.getColor());
        Block b2 = new Block(6, 0, 0, false);
        Assert.assertEquals("getColor does not return color", 6, b2.getColor());
    }

    //tests for the correct ghost state
    @Test
    public void testGetBlockIsGhostGetsGhostBool() {
        Block b1 = new Block(0, 0, 0, false);
        Assert.assertEquals("Get ghost does not return isGhost", false, b1.getIsGhost());
        Block b2 = new Block(0, 0, 0, true);
        Assert.assertEquals("Get ghost does not return isGhost", true, b2.getIsGhost());
    }

    //tests for the correct copying through the copy constructor
    @Test
    public void testBlockConstructorConstructsBlock() {
        Block b1 = new Block(0, 0, 0, false);
        Assert.assertEquals("Color is not properly set in constructor.", 0, b1.getColor());
        Assert.assertEquals("xPosition is not properly set in constructor.", 0, b1.getXPosition());
        Assert.assertEquals("yPosition is not properly set in constructor.", 0, b1.getYPosition());
        Assert.assertEquals("isGhost is not properly set in constructor.", false, b1.getIsGhost());


        Block b2 = new Block(6, 6, 6, true);
        Assert.assertEquals("Color is not properly set in constructor.",6, b2.getColor());
        Assert.assertEquals("xPosition is not properly set in constructor.", 6, b2.getXPosition());
        Assert.assertEquals("yPosition is not properly set in constructor.", 6, b2.getYPosition());
        Assert.assertEquals("isGhost is not properly set in constructor.", true, b2.getIsGhost());

    }

    //tests for the correct setting of colour after construction
    @Test
    public void testSetColorSetsColor() {
        Block b1 = new Block(3, 7, 2, false);
        Block b2 = new Block(3, 7, 2, false);
        b1.setColor(4);
        b2.setColor(0);

        Assert.assertEquals("Block color is not set properly.", 4, b1.getColor());
        Assert.assertEquals("Block color is not set properly.", 0, b2.getColor());

        b2 = new Block(3, 7, 2, false);
        b2.setColor(7);
        Assert.assertEquals("Block initialized with color 3, setColor() accepts color > 6", 3, b2.getColor());

        b2 = new Block(-1, 7, 2, false);
        Assert.assertEquals("Block initialized with negative color. Color should not be negative.", 0, b2.getColor());
        b2.setColor(-5);
        Assert.assertEquals("Block initialized with color 3, setColor() accepts color < 0", 0, b2.getColor());
        b2.setColor(6);
        Assert.assertEquals("Block color is not set properly.", 6, b2.getColor());
    }

    //tests for the correct setting of ghost state after construction
    @Test
    public void testSetIsGhostSetsIsGhost() {
        Block b1 = new Block(3, 7, 2, false);
        Block b2 = new Block(3, 7, 2, true);
        b1.setIsGhost(true);
        b2.setIsGhost(false);

        Assert.assertEquals("Block isGhost is not set properly.", true, b1.getIsGhost());
        Assert.assertEquals("Block isGhost is not set properly.", false, b2.getIsGhost());
        b2.setIsGhost(true);
        Assert.assertEquals("Block isGhost is not set properly.", true, b2.getIsGhost());
    }

    @Test
    public void testSetXPositionSetsXPosition() {
        Block b1 = new Block(3, 7, 2, false);
        Block b2 = new Block(3, 7, 2, true);
        b1.setXPosition(10);
        b2.setXPosition(0);

        Assert.assertEquals("Block xPosition is not set properly.", b1.getXPosition(), 10);
        Assert.assertEquals("Block xPosition is not set properly.", b2.getXPosition(), 0);
        b2.setXPosition(5);
        Assert.assertEquals("Block xPosition is not set properly.", b2.getXPosition(), 5);
    }

    @Test
    public void testSetYPositionSetsYPosition() {
        Block b1 = new Block(3, 7, 2, false);
        Block b2 = new Block(3, 7, 2, true);
        b1.setYPosition(10);
        b2.setYPosition(0);

        Assert.assertEquals("Block yPosition is not set properly.", 10, b1.getYPosition());
        Assert.assertEquals("Block yPosition is not set properly.", 0, b2.getYPosition());
        b2.setYPosition(5);
        Assert.assertEquals("Block yPosition is not set properly.", 5, b2.getYPosition());
    }

    //tests the return functionality of a copied block
    @Test
    public void testCopyConstructorReturnsCopy() {

        Block b1 = new Block(3, 3, 7, false);
        Block b2 = new Block(8, 1, 3, true);
        Block b1Copy = new Block(b1);
        Block b2Copy = new Block(b2);

        // testing block 1 and copy
        Assert.assertEquals("Block color does not match.",b1.getColor(), b1Copy.getColor());
        Assert.assertEquals("xPosition does not match.",b1.getXPosition(), b1Copy.getXPosition());
        Assert.assertEquals("yPosition does not match.",b1.getYPosition(), b1Copy.getYPosition());
        Assert.assertEquals("isGhost does not match.",b1.getIsGhost(), b1Copy.getIsGhost());

        // testing block 2 and copy
        Assert.assertEquals("Block color does not match.",b2.getColor(), b2Copy.getColor());
        Assert.assertEquals("xPosition does not match.",b2.getXPosition(), b2Copy.getXPosition());
        Assert.assertEquals("yPosition does not match.",b2.getYPosition(), b2Copy.getYPosition());
        Assert.assertEquals("isGhost does not match.",b2.getIsGhost(), b2Copy.getIsGhost());

        Assert.assertNotSame("Block copy constructor returns a reference instead of a deep copy.", b1, b1Copy);
        Assert.assertNotSame("Block copy constructor returns a reference instead of a deep copy.", b2, b2Copy);

    }

}
