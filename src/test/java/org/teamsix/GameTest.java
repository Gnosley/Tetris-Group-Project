package org.teamsix;

import junit.framework.Assert;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class GameTest {
        
        //test for when holding is available
        @Test 
        public void testHoldingPieceAfterCommit () {
            Game g = new Game ("usernam", "easy");
            Assert.assertEquals("Initial state has the hold as unavailable",g.getIsHoldMoveAvailable(), true);
            g.tryMove('w');
            Assert.assertEquals("Hold move was not disabled after a hold",g.getIsHoldMoveAvailable(), false);
            for(int counter = 0; counter<25; counter++) {
                g.tryMove('s');
            }
            Assert.assertEquals("Hold move was not re-enabled after a piece was placed",g.getIsHoldMoveAvailable(), true);
        }
        
        //tests for gameScore updating and file I/O of score
        @Test
        public void testScoringAndFilingMechansim () {
            Game g = new Game ("HIGHSCR", "easy");
            Assert.assertEquals("Score doesn't start at 0",g.getGameScore(),0);
            g.updateGameScore(1);
            Assert.assertEquals("Score doesn't increase by 100 per line when clearing a single line",g.getGameScore(),100);
            g.updateGameScore(4);
            Assert.assertEquals("Score doesn't increase by 200 per line when clearing 4 lines",g.getGameScore(),900);
            g.updateGameScore(2); 
            Assert.assertEquals("Score doesn't increase by 100 per line when clearing 2 lines", g.getGameScore(),1100);
            g.updateGameScore(3);
            Assert.assertEquals("Score doesn't increase by 100 per line when clearing 3 lines", g.getGameScore(),1400);
            for (int counter = 0; counter<100; counter++) {
                g.updateGameScore(4);
            }
            Assert.assertEquals("Score doesn't increase by 200 per line when clearing 4 lines 100 times", g.getGameScore(),81400);
            g.writeScoreToFile();
            Assert.assertEquals("Highscore is not the current highscore", g.getHighScore(), 81400);
            Assert.assertEquals("Username of highscore holder is not the correct username", g.getHighScoreName(), "HIGHSCR");
        }
        
        //tests for rotating pieces
        @Test
        public void testRotation () {
            Game g = new Game ("usernam", "easy");
            Assert.assertEquals("Rotation state doesn't start at 0",g.getCurrentTetromino().getRotation(),0);
            g.tryMove('q');
            Assert.assertEquals("Rotation doesn't change when rotated 90 degrees CCW",g.getCurrentTetromino().getRotation(),-90);
            g.tryMove('e');
            Assert.assertEquals("Rotation state doesn't go back to 0 when rotated back",g.getCurrentTetromino().getRotation(),0);
            for (int counter = 0; counter<102; counter++) {
                g.tryMove('e');
            }
            Assert.assertEquals("Rotation state doesn't accurately track the rotation after multiple full rotations",g.getCurrentTetromino().getRotation(),180);
        }
        
        //tests for making sure the highscore file
        @Test 
        public void testHighscoreFile () {
          
            boolean fileExists = true;
            try {
                Scanner input1 = new Scanner (new File ("Highscore.txt"));
            }
            catch (FileNotFoundException e) {
                try {
                    Scanner input2 = new Scanner (new File ("Highscore.txt"));
                }
                catch (FileNotFoundException ex) {
                    fileExists = false;
                }
            }
            finally {
                Assert.assertEquals("The file does not exists and is not created.", fileExists, true);
            }
        }        
}
