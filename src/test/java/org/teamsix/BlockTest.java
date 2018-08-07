package org.teamsix;

import junit.framework.Assert;
import org.junit.Test;

public class BlockTest {

    @Test
    public void testGetBlockYPositionGetsBlockYPosition() {
        Block b1 = new Block(0, 0, 3, false);
        Assert.assertEquals("getYPosition does not return yPosition",b1.getYPosition(), 3);
        Block b2 = new Block(0, 0, 20, false);
        Assert.assertEquals("getYPosition does not return yPosition",b2.getYPosition(), 20);
    }

    @Test
    public void testGetBlockXPositionGetsBlockXPosition() {
        Block b1 = new Block(0, 0, 0, false);
        Assert.assertEquals("getXPosition does not return xPosition",b1.getXPosition(), 0);
        Block b2 = new Block(0, 4, 0, false);
        Assert.assertEquals("getXPosition does not return xPosition",b2.getXPosition(), 4);
    }

    @Test
    public void testGetBlockColorGetsBlockColor() {
        Block b1 = new Block(2, 0, 0, false);
        Assert.assertEquals("getColor does not return color",b1.getColor(), 2);
        Block b2 = new Block(6, 0, 0, false);
        Assert.assertEquals("getColor does not return color",b2.getColor(), 6);
    }

    @Test
    public void testGetBlockIsGhostGetsGhostBool() {
        Block b1 = new Block(0, 0, 0, false);
        Assert.assertEquals("Get ghost does not return isGhost",b1.getIsGhost(), false);
        Block b2 = new Block(0, 0, 0, true);
        Assert.assertEquals("Get ghost does not return isGhost",b2.getIsGhost(), true);
    }

    @Test
    public void testBlockConstructorConstructsBlock() {
        Block b1 = new Block(0, 0, 0, false);
        Assert.assertEquals("Color is not properly set in constructor.",b1.getColor(), 0);
        Assert.assertEquals("xPosition is not properly set in constructor.",b1.getXPosition(), 0);
        Assert.assertEquals("yPosition is not properly set in constructor.",b1.getYPosition(), 0);
        Assert.assertEquals("isGhost is not properly set in constructor.",b1.getIsGhost(), false);


        Block b2 = new Block(7, 7, 7, true);
        Assert.assertEquals("Color is not properly set in constructor.",b2.getColor(), 7);
        Assert.assertEquals("xPosition is not properly set in constructor.",b2.getXPosition(), 7);
        Assert.assertEquals("yPosition is not properly set in constructor.",b2.getYPosition(), 7);
        Assert.assertEquals("isGhost is not properly set in constructor.",b2.getIsGhost(), true);

    }

    @Test
    public void testSetColorSetsColor() {
        Block b1 = new Block(3, 7, 2, false);
        Block b2 = new Block(3, 7, 2, false);
        b1.setColor(4);
        b2.setColor(0);

        Assert.assertEquals("Block color is not set properly.", b1.getColor(), 4);
        Assert.assertEquals("Block color is not set properly.", b2.getColor(), 0);
        b2.setColor(6);
        Assert.assertEquals("Block color is not set properly.", b2.getColor(), 6);
    }

    @Test
    public void testSetIsGhostSetsIsGhost() {
        Block b1 = new Block(3, 7, 2, false);
        Block b2 = new Block(3, 7, 2, true);
        b1.setIsGhost(true);
        b2.setIsGhost(false);

        Assert.assertEquals("Block isGhost is not set properly.", b1.getIsGhost(), true);
        Assert.assertEquals("Block isGhost is not set properly.", b2.getIsGhost(), false);
        b2.setIsGhost(true);
        Assert.assertEquals("Block isGhost is not set properly.", b2.getIsGhost(), true);
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

        Assert.assertEquals("Block yPosition is not set properly.", b1.getYPosition(), 10);
        Assert.assertEquals("Block yPosition is not set properly.", b2.getYPosition(), 0);
        b2.setYPosition(5);
        Assert.assertEquals("Block yPosition is not set properly.", b2.getYPosition(), 5);
    }


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
