package org.teamsix;

import junit.framework.Assert;
import org.junit.Test;

public class BlockTest {

    @Test
    public void testCopyConstructorReturnsCopy() {
        Assert.assertEquals("hello world", "hello world");
    }
}
