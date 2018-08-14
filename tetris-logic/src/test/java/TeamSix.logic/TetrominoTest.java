package TeamSix.logic;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Provides tests for Tetromino rotation
 */
public class TetrominoTest {

    @Test
    /**
     * Test if the I piece's counter clockwise wall kick from default position properly functions.
     */
    public void testITetrominoCCWWallKickDefaultPos() {
        TetrominoFactory tf = new TetrominoFactory("EASY", 0, 0);
        Tetromino iPiece1 = tf.getTetromino(0);
        Tetromino iPiece2 = tf.getTetromino(0);

        // compare rotation without wallkick to that with wallkick
        iPiece1.rotate('q', 0);
        iPiece2.rotate('q', 1);
        Block[] iPiece1Array = iPiece1.getBlockArray();
        Block[] iPiece2Array = iPiece2.getBlockArray();

        for (int i=0; i<iPiece1Array.length; i++) {
            Assert.assertEquals("Block is not in correct x position",
                                iPiece1Array[i].getXPosition() - 1,
                                iPiece2Array[i].getXPosition());
            Assert.assertEquals("Block is not in correct y position",
                                iPiece1Array[i].getYPosition(),
                                iPiece2Array[i].getYPosition());
        }
    }

    @Test
    /**
     * Test the I piece's counter clockwise wall kick from state 3 to state 2.
     */
    public void testITetrominoCCWWallKick3To2() {
        TetrominoFactory tf = new TetrominoFactory("EASY", 0, 0);
        Tetromino iPiece1 = tf.getTetromino(0);
        Tetromino iPiece2 = tf.getTetromino(0);

        // compare rotation without wallkick to that with wallkick
        iPiece1.rotate('q', 0);
        iPiece1.rotate('q', 0);
        iPiece2.rotate('q', 0);
        iPiece2.rotate('q', 1);
        Block[] iPiece1Array = iPiece1.getBlockArray();
        Block[] iPiece2Array = iPiece2.getBlockArray();

        for (int i=0; i<iPiece1Array.length; i++) {
            Assert.assertEquals("Block is not in correct x position",
                                iPiece1Array[i].getXPosition() - 2,
                                iPiece2Array[i].getXPosition());
            Assert.assertEquals("Block is not in correct y position",
                                iPiece1Array[i].getYPosition(),
                                iPiece2Array[i].getYPosition());
        }
    }

    @Test
    /**
     * Test the I piece's counter clockwise wall kick from state 2 to state 1.
     */
    public void testITetrominoCCWWallKick2To1() {
        TetrominoFactory tf = new TetrominoFactory("EASY", 0, 0);
        Tetromino iPiece1 = tf.getTetromino(0);
        Tetromino iPiece2 = tf.getTetromino(0);

        // compare rotation without wallkick to that with wallkick
        iPiece1.rotate('q', 0);
        iPiece1.rotate('q', 0);
        iPiece1.rotate('q', 0);
        iPiece2.rotate('q', 0);
        iPiece2.rotate('q', 0);
        iPiece2.rotate('q', 1);
        Block[] iPiece1Array = iPiece1.getBlockArray();
        Block[] iPiece2Array = iPiece2.getBlockArray();

        for (int i=0; i<iPiece1Array.length; i++) {
            Assert.assertEquals("Block is not in correct x position",
                                iPiece1Array[i].getXPosition() + 1,
                                iPiece2Array[i].getXPosition());
            Assert.assertEquals("Block is not in correct y position",
                                iPiece1Array[i].getYPosition(),
                                iPiece2Array[i].getYPosition());
        }
    }

    @Test
    /**
     * Test the I piece's counter clockwise wall kick from state 1 to state 0.
     */
    public void testITetrominoCCWWallKick1To0() {
        TetrominoFactory tf = new TetrominoFactory("EASY", 0, 0);
        Tetromino iPiece1 = tf.getTetromino(0);
        Tetromino iPiece2 = tf.getTetromino(0);

        // compare rotation without wallkick to that with wallkick
        iPiece1.rotate('q', 0);
        iPiece1.rotate('q', 0);
        iPiece1.rotate('q', 0);
        iPiece1.rotate('q', 0);
        iPiece2.rotate('q', 0);
        iPiece2.rotate('q', 0);
        iPiece2.rotate('q', 0);
        iPiece2.rotate('q', 1);
        Block[] iPiece1Array = iPiece1.getBlockArray();
        Block[] iPiece2Array = iPiece2.getBlockArray();

        for (int i=0; i<iPiece1Array.length; i++) {
            Assert.assertEquals("Block is not in correct x position",
                                iPiece1Array[i].getXPosition() + 2,
                                iPiece2Array[i].getXPosition());
            Assert.assertEquals("Block is not in correct y position",
                                iPiece1Array[i].getYPosition(),
                                iPiece2Array[i].getYPosition());
        }
    }


}
