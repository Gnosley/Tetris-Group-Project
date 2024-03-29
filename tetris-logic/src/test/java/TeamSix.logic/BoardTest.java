package TeamSix.logic;

import junit.framework.Assert;
import org.junit.Test;

/**
 * JUnit tests for the Board class
 **/
public class BoardTest {
    
    //tests the updating functionality of Board
    @Test
    public void testBoardUpdateMethod(){
        Board b1 = new Board();
        TetrominoFactory tetrominoFactory = new TetrominoFactory("EASY", 0, 0);
        Tetromino t1 = tetrominoFactory.getTetromino(5,5,1,false);
        b1.updateBoard(t1);
        Board b2 = new Board();
        Assert.assertNotSame("board not being updated with tetromino",
            b1.getCurrentBoard()[5][5], b2.getCurrentBoard()[5][5]);
    }

    //tests the getter method of Board as to prevent privacy leaks
    @Test
    public void testBoardGetterMethodPrivacy(){
        Board b1 = new Board();
        Block[][] ba1 = b1.getCurrentBoard();
        TetrominoFactory tetrominoFactory = new TetrominoFactory("EASY", 0, 0);
        Tetromino t1 = tetrominoFactory.getTetromino(5,10,1,false);
        b1.updateBoard(t1);
        Block[][] ba2 = b1.getCurrentBoard();
        Assert.assertNotSame("Privacy leak", ba1[10][5], ba2[10][5]);
    }

    //tests the ability to check for a proper movement of tetrominos on the Board
    @Test
    public void testBoardCheckMoveMethod(){
        Board b1 = new Board();
        TetrominoFactory tetrominoFactory = new TetrominoFactory("EASY", 0, 0);
        Tetromino t1 = tetrominoFactory.getTetromino(5,10,1,false);
        boolean clearCheck1 = b1.checkMove(t1);
        Assert.assertSame("Move should be valid", true, clearCheck1);
        b1.updateBoard(t1);
        Tetromino t2 = tetrominoFactory.getTetrominoCopy(t1);
        boolean clearCheck2 = b1.checkMove(t2);
        Assert.assertSame("Move should be invalid", false, clearCheck2);

    }

    //tests when the game is done and how the Board responds
    @Test
    public void testBoardIsGameDoneMethod(){
        Board b1 = new Board();
        TetrominoFactory tetrominoFactory = new TetrominoFactory("EASY", 0, 0);
        Tetromino t1 = tetrominoFactory.getTetromino(5,3,1,false);
        boolean overCheck1 = b1.isGameDone();
        Assert.assertSame("Board is playable - game is not over", false, overCheck1);
        b1.updateBoard(t1);
        boolean overCheck2 = b1.isGameDone();
        Assert.assertSame("Board no longer playable - Game is over", true, overCheck2);

    }
}
