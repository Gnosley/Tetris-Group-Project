package org.teamsix;

import junit.framework.Assert;
import org.junit.Test;

public class BlockTest {

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
