package TeamSix.logic;

// imports for high score handling
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * This class provides the game object. Holds the current state of the game. Can
 * use its public methods to play a game of Tetris.
 */
public class Game {
    private static final int startingX = 3; // where a tetromino should start on
    private static final int startingY = 0; // the board
    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private Tetromino ghostTetromino; //  the ghost of the current tetromino
    private Tetromino storedTetromino = null; // the held tetromino
    private TetrominoFactory tetrominoFactory;
    private long gameScore = 0;
    private long linesCleared = 0;

    // boolean to indicate if a piece is already being held
    private boolean isHoldOccupied = false;

    // boolean to indicate if hold functionality is available
    private boolean isHoldMoveAvailable = true;

    private Board board = new Board();
    private String username = "USER   ";

    /**
     * Default constructor. Initializes a game of Tetris with a username of USER
     * and medium difficulty.
     */
    public Game() {
        this("USER    ", "MEDIUM");
    }

    /**
     * Constructor which initializes a game with a given username and
     * difficulty.
     *
     * @param username   The current player's username
     * @param difficulty The difficulty of the game. One of "easy", "medium", or
     *                   "hard".
     */
    public Game(String username, String difficulty) {
        tetrominoFactory = new TetrominoFactory(difficulty,
                                                startingX, startingY);
        // generate three new tetrominos at the start of the game
        currentTetromino = tetrominoFactory.getTetromino();
        nextTetromino = tetrominoFactory.getTetromino();
        ghostTetromino = tetrominoFactory.getTetrominoCopy(currentTetromino, true);

        // Initial positioning ghost version of current tetromino
        ghostTetromino = positionGhost(board);
        this.username = username;

    }


    /**
     * Getter for the current tetromino instance variable
     *
     * @return tetromino: Tetromino, copy of the currentTetromino
     */
    public Tetromino getCurrentTetromino() {
        return (tetrominoFactory.getTetrominoCopy(this.currentTetromino));
    }

    /**
     * Getter for the next tetromino instance variable
     *
     * @return tetromino: Tetromino, copy of the nextTetromino
     */
    public Tetromino getNextTetromino() {
        return (tetrominoFactory.getTetrominoCopy(this.nextTetromino));
    }

    /**
     * Getter for the ghost tetromino instance variable
     *
     * @return tetromino: Tetromino, copy of the ghostTetromino
     */
    public Tetromino getGhostTetromino() {
        return (tetrominoFactory.getTetrominoCopy(this.ghostTetromino));
    }

    /**
     * Getter for the stored tetromino instance variable
     *
     * @return tetromino: Tetromino, copy of the storedTetromino
     */
    public Tetromino getStoredTetromino() {
        if (this.storedTetromino == null) {
            return null;
        }
        return (tetrominoFactory.getTetrominoCopy(this.storedTetromino));
    }

    /**
     * Getter method for the board instance variable
     *
     * @return block[][]: gameBoard instance variable of block object, the
     * current board
     */
    public Block[][] getBoard() {
        return (board.getCurrentBoard());
    }


    /**
     * Getter method of the score achieved through clearing rows
     *
     * @return gameScore: long, the score achieved
     */
    public long getGameScore() {
        return this.gameScore;
    }

    /**
     * Getter of how many lines were cleared
     *
     * @return linesCleared: long, keeps track of number of lines cleared
     */
    public long getLinesCleared() {
        return this.linesCleared;
    }

    /**
     * Method to get the tetromino counts
     *
     * @return pieceStats directly from the tetrominoFactory.
     */
    public int[] getPieceStats() {
        return tetrominoFactory.getPieceStats();
    }

    /**
     * Retrives isGameDone boolean from board.
     *
     * @return isGameDone: boolean, indicates if game is done.
     */
    public boolean isGameDone() {
        return (board.isGameDone());
    }

    /**
     * Retrives preClearedBoard board from board.
     *
     * @return preClearedBoard: block[][].
     */
    public Block[][] getPreClearedBoard() {
        return (board.getPreClearedBoard());
    }

    /**
     * Retrives getRowsToClear from board.
     *
     * @return getRowsToClear: ArrayList<Integer>.
     */
    public ArrayList<Integer> getRowsToClear() {
        return (board.getRowsToClear());
    }

    /**
     * Resets getRowsToClear from board.
     */
    public void resetRowsToClear() {
        board.resetRowsToClear();
    }


    /**
     * Getter method for whether hold move is available
     *
     * @return isHoldMoveAvailable: boolean, inidcates if hold move is available
     */
    public boolean getIsHoldMoveAvailable() {
        return this.isHoldMoveAvailable;
    }


    /**
     * Positioning method for the ghost tetromino. Takes position of current
     * tetromino and sets the ghost position to the "floored" version if it
     *
     * @param board current board
     * @return ghost tetromino (re-positioned)
     */
    private Tetromino positionGhost(Board board) {
        boolean canMove = true;
        Tetromino ghostTetromino = tetrominoFactory.getTetrominoCopy(currentTetromino, true);
        while (canMove) {
            Tetromino movedGhost = tetrominoFactory.getTetrominoCopy(ghostTetromino);
            movedGhost.doMove('s');
            canMove = board.checkMove(movedGhost);
            if (canMove) {
                ghostTetromino = movedGhost;
            }
        }
        return ghostTetromino;
    }


    /**
     * Attempt to make a move, must be checked for possibility within limits of
     * game
     *
     * @param moveType: char, letter input of I/O
     */
    public boolean tryMove(int moveType) {
        // Possible chars are q, e, a, s, d, w or TAB, f or SPACE.
        // q rotates counter-clockwise.
        // e rotates clockwise.
        // a moves the block left.
        // s moves the block down.
        // d moves the block right.
        // w or TAB holds the current block.
        // f or SPACE drops the current block.
        boolean canMove = false;
        Tetromino movedTetromino = null;
        switch (moveType) {
            case 'q':
            case 'e':
                int testNum = 0;
                while (!canMove && testNum < 5) {
                    movedTetromino =
                            tetrominoFactory.getTetrominoCopy(currentTetromino);
                    movedTetromino.doMove((char) moveType, testNum);
                    canMove = board.checkMove(movedTetromino);
                    testNum++;
                }
                if (canMove) {
                    currentTetromino = tetrominoFactory.getTetrominoCopy(movedTetromino);
                }
                break;
            case 'a':
            case 's':
            case 'd':
                // Tetromino.doMove() should return a NEW tetromino with the move applied
                movedTetromino = tetrominoFactory.getTetrominoCopy(currentTetromino);
                movedTetromino.doMove((char) moveType);

                // board.checkBoard() returns true if no blocks are currently in the way and
                // no blocks in the given tetromino are out of board bounds.
                canMove = board.checkMove(movedTetromino);

                if (canMove) {
                    currentTetromino = tetrominoFactory.getTetrominoCopy(movedTetromino);
                } else if (moveType == 's' && !canMove) {
                    // if moving down causes it to hit a block or go out of bounds
                    // (aka hit the bottom of the board), add the current blocks in
                    // the tetromino to the board.
                    board.updateBoard(currentTetromino);
                    commitTetrominoSequence(board);
                }
                break;

            case 'f':
            case 32:
                this.dropMove(board);
                break;

            case 'w':
            case 9:
                this.holdMove();
                break;

            default:
                return false;
        }
        this.ghostTetromino = positionGhost(board);
        return canMove;
    }

    /**
     * Method for holding tetromino. if the hold character is pressed, it will
     * switch out the current tetromino for the held one
     */
    private void holdMove() {
        if (isHoldMoveAvailable) {
            if (!isHoldOccupied) {
                // no tetromino is held yet, so it grabs a new one
                storedTetromino = tetrominoFactory.getTetrominoCopy(currentTetromino);
                currentTetromino = tetrominoFactory.getTetrominoCopy(nextTetromino);
                nextTetromino = tetrominoFactory.getTetromino();
                isHoldOccupied = true;
            } else {
                // a tetromino is already held, so it replaces current with that one
                Tetromino proxyTetromino; // proxy space for switching tetrominos
                proxyTetromino = tetrominoFactory.getTetrominoCopy(currentTetromino);
                currentTetromino = tetrominoFactory.getTetromino(storedTetromino.getType());
                storedTetromino = tetrominoFactory.getTetrominoCopy(proxyTetromino);
            }
            isHoldMoveAvailable = false; // a tetromino has already been held for this drop
        }
    }

    /**
     * Method for the drop move
     *
     * @param board Current game board
     */
    private void dropMove(Board board) {
        currentTetromino = tetrominoFactory.getTetrominoCopy(ghostTetromino, false);
        board.updateBoard(currentTetromino);
        commitTetrominoSequence(board);
    }

    /**
     * Sequence of events which occur after a piece 'played' (locked into the
     * board). Game statistics get updated to console, current tetromino is
     * updated with the next piece, next tetromino is created, and hold move
     * ability is reset.
     *
     * @param board current game board
     */
    private void commitTetrominoSequence(Board board) {
        long numLinesCleared = board.getNumLinesCleared();
        this.updateLinesCleared(numLinesCleared);
        this.updateGameScore(numLinesCleared);
        board.resetNumLinesCleared();

        if(!board.isGameDone()){    //checks to see if game is over
        this.currentTetromino = this.nextTetromino;
        this.nextTetromino = tetrominoFactory.getTetromino(); // initialize a new random Tetromino
        this.ghostTetromino = tetrominoFactory.getTetrominoCopy(currentTetromino, true);
        this.ghostTetromino = positionGhost(board);
        isHoldMoveAvailable = true; //resets ability to hold piece
        }
    }

    /**
     * Keeping track of score through addition of lines cleared as lines clear
     *
     * @param linesCleared: long, keeps track of number of lines cleared
     */
    private void updateLinesCleared(long linesCleared) {
        this.linesCleared += linesCleared;
    }

    /**
     * Setter of the score achieved through clearing rows
     *
     * @param numLinesCleared: long, the number of lines cleared in a turn.
     */
    public void updateGameScore(long numLinesCleared) {
        if (numLinesCleared == 4) {
            this.gameScore += (numLinesCleared * 200);
        } else {
            this.gameScore += (numLinesCleared * 100);
        }
    }

    /**
     * Getter method for difficulty.
     *
     * @return The current tetromino generation difficulty setting.
     */
    public String getDifficulty() {
        return this.tetrominoFactory.getDifficulty();
    }

    /**
     * Getter method for username attached to current game.
     *
     * @return String with the user's name.
     */
    public String getUsername() {
        return this.username;
    }

    /*
     * HIGH SCORE FILE METHODS
     */

    /**
     * Sets the highscore by reading and writing from a text file
     */
    public void writeScoreToFile() {

        try {
            Scanner input = new Scanner(new File("HighScore.txt"));
            List<String> userAndScore = new ArrayList<String>();

            String usernameToWrite = username;
            while (usernameToWrite.length() < 7) {
                usernameToWrite += " ";
            }
            if (usernameToWrite.length() > 7) {
                usernameToWrite = usernameToWrite.substring(0, 7);
            }
            //this is the string that needs to be written to the text file
            String newUserScore = username + Long.toString(gameScore);


            while (input.hasNextLine()) {
                //run through text file and transfer to a String ArrayList
                userAndScore.add(input.nextLine());
            }

            input.close();

            //String ArrayList for username storage, long ArrayList for score storage
            List<String> user = new ArrayList<String>();
            List<Long> scores = new ArrayList<Long>();

            for (int i = 0; i < userAndScore.size(); i++) {
                long newLongScore;
                //newScore is just the score
                if (userAndScore.get(i).length() > 7) {
                    String newScore = userAndScore.get(i).substring(7);
                    newLongScore = Long.parseLong(newScore);
                } else newLongScore = 0;

                scores.add(newLongScore);
            }

            int placement = 0;
            //find what the placement of the new score is compared to the rest
            for (int a = 0; a < scores.size(); a++) {
                if (scores.get(a) > gameScore) {
                    placement = a + 1;
                }
            }

            PrintWriter output = new PrintWriter("HighScore.txt");

            userAndScore.add(null);

            //place the old data back onto the ArrayList in the new order
            if (placement != userAndScore.size() - 1) {
                for (int b = userAndScore.size() - 1; b > placement; b--) {
                    userAndScore.set(b, userAndScore.get(b - 1));
                }
            }
            userAndScore.set(placement, newUserScore);

            //print out onto the document
            for (int c = 0; c < userAndScore.size(); c++) {
                output.println(userAndScore.get(c));
            }

            output.close();
        } catch (FileNotFoundException e) {
            generateNewHighScore();
            writeScoreToFile();
        }

    }

    /**
     * Gets the highscore by reading from a text file
     *
     * @return highscore, long
     */
    public long getHighScore() {
        long highscore = 0;
        try {
            Scanner input = new Scanner(new File("HighScore.txt"));
            highscore = Long.parseLong(input.nextLine().substring(7));

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return highscore;

    }

    /**
     * Gets the username by reading from a text file
     *
     * @return highscoreName, String
     */
    public String getHighScoreName() {
        String highscoreName = "NoFILE";
        try {
            Scanner input = new Scanner(new File("HighScore.txt"));
            highscoreName = input.nextLine().substring(0, 7);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return highscoreName;
    }

    /**
     * If no Highscore.txt exists, or if a deletion of the current highscore
     * file is requested, a new base file is written.
     */
    public void generateNewHighScore() {
        String insert = "USERNAM000";
        byte[] insertBytes = insert.getBytes();

        try {
            OutputStream output = new FileOutputStream("HighScore.txt");
            output.write(insertBytes);
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}
