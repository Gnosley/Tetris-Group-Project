package TeamSix.logic;

import junit.framework.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * JUnit tests for the Game class
 **/
public class GameTest {
        
        //test for when holding is available
        @Test 
        public void testHoldingPieceAfterCommit () {
            Game g = new Game ("usernam", "easy");
            Assert.assertEquals("Initial state has the hold as unavailable", true, g.getIsHoldMoveAvailable());
            g.tryMove('w');
            Assert.assertEquals("Hold move was not disabled after a hold", false, g.getIsHoldMoveAvailable());
            for(int counter = 0; counter<25; counter++) {
                g.tryMove('s');
            }
            Assert.assertEquals("Hold move was not re-enabled after a piece was placed", true, g.getIsHoldMoveAvailable());
        }
        
        //tests for gameScore updating and file I/O of score
        @Test
        public void testScoringAndFilingMechansim () {
            Game g = new Game ("HIGHSCR", "easy");
            Assert.assertEquals("Score doesn't start at 0", 0, g.getGameScore());
            g.updateGameScore(1);
            Assert.assertEquals("Score doesn't increase by 100 per line when clearing a single line", 100, g.getGameScore());
            g.updateGameScore(4);
            Assert.assertEquals("Score doesn't increase by 200 per line when clearing 4 lines", 900, g.getGameScore());
            g.updateGameScore(2); 
            Assert.assertEquals("Score doesn't increase by 100 per line when clearing 2 lines", 1100, g.getGameScore());
            g.updateGameScore(3);
            Assert.assertEquals("Score doesn't increase by 100 per line when clearing 3 lines", 1400, g.getGameScore());
            for (int counter = 0; counter<100; counter++) {
                g.updateGameScore(4);
            }
            Assert.assertEquals("Score doesn't increase by 200 per line when clearing 4 lines 100 times", 81400, g.getGameScore());
            g.writeScoreToFile();
            Assert.assertEquals("Highscore is not the current highscore", 81400, g.getHighScore());
            Assert.assertEquals("Username of highscore holder is not the correct username", "HIGHSCR", g.getHighScoreName());
        }
        
        //tests for rotating pieces
        @Test
        public void testRotation () {
            Game g = new Game ("usernam", "easy");
            Assert.assertEquals("Rotation state doesn't start at 0", 0, g.getCurrentTetromino().getRotation());
            g.tryMove('q');
            Assert.assertEquals("Rotation doesn't change when rotated 90 degrees CCW", -90, g.getCurrentTetromino().getRotation());
            g.tryMove('e');
            Assert.assertEquals("Rotation state doesn't go back to 0 when rotated back", 0, g.getCurrentTetromino().getRotation());
            for (int counter = 0; counter<102; counter++) {
                g.tryMove('e');
            }
            Assert.assertEquals("Rotation state doesn't accurately track the rotation after multiple full rotations", 180, g.getCurrentTetromino().getRotation());
        }
        
        //tests for making sure the highscore file
        @Test 
        public void testHighscoreFile () {
          
            boolean fileExists = true;
            try {
                Scanner input1 = new Scanner (new File ("HighScore.txt"));
            }
            catch (FileNotFoundException e) {
                try {
                    Scanner input2 = new Scanner (new File ("HighScore.txt"));
                }
                catch (FileNotFoundException ex) {
                    fileExists = false;
                }
            }
            finally {
                Assert.assertEquals("The file does not exists and is not created.", true, fileExists);
            }
        }        
}
