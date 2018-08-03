

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controls all actions in the Tetris window
 */
public class TetrisController {
    @FXML
    //private Rectangle newGame;
    private Rectangle tetrisBlock;

    @FXML
    private AnchorPane playArea;

    @FXML
    private ImageView image;

    @FXML
    private Text scoreText;

    @FXML
    private Text linesClearedText;

    @FXML
    private Text gameOverText;

    @FXML
    private Text startGameText;

    private StackPane currentTetrominoGraphic;
    private StackPane nextTetrominoGraphic;
    private StackPane ghostTetrominoGraphic;

    private long startTime = 0;

    //private Board board;
    private Game game;

//    private Tetromino currentTetromino; // the tetromino currently in play
//    private Tetromino nextTetromino; // the tetromino to be played next
//    private Tetromino ghostTetromino; // the ghost tetromino to drop
//    private static final int startingX = 3;
//    private static final int startingY = 0;

    private boolean gameDone = false;
    private boolean newGame;
    private int currentDifficulty;
    private int dropSpeed = 300;
    private Rectangle[][] blockFXArray;

    private int boardCellWidth = 20;
    private int boardLayoutX = 160;
    private int boardLayoutY = 20;
    private int nextBoxLayoutX = 400;
    private int nextBoxLayoutY = 60;

    /**
     * Initializes values when Tetris window opens
     */
    public void initialize() {
        this.blockFXArray = new Rectangle[24][10];
        Game game = new Game();
        this.game = game;
        newGame = true;
    }

    /**
     * Handles the keyboard controls of the game
     * ENTER: Start Game
     * LEFT: Move Tetromino left
     * RIGHT: Move Tetromino right
     * DOWN: Move Tetromino down
     * UP: Rotates the Tetromino clockwise
     * SPACE: Drop Tetromino
     * ESC: Exit Game
     * @param event
     * @throws IOException
     */
    @FXML
    protected void handleKeyboardAction(KeyEvent event) throws IOException {

        switch (event.getCode()) {
            case LEFT:
                if(!gameDone && !newGame) {
                    tryMove('a', game.getBoard());
                }
                break;
            case RIGHT:
                if(!gameDone && !newGame) {
                    tryMove('d', game.getBoard());
                }
                break;
            case DOWN:
                if(!gameDone && !newGame) {
                    tryMove('s', game.getBoard());
                    gameDone = game.getBoard().isGameDone();
                }
                break;
            case SPACE:
                if(!gameDone && !newGame) {
                    tryMove('f', game.getBoard());
                    gameDone = game.getBoard().isGameDone();
                }
                    break;
            case UP:
                if(!gameDone && !newGame) {
                    tryMove('e', game.getBoard());
                }
                break;
            case ENTER:
                if(newGame) {
                    startGameText.setText(" ");
                    startTime = System.currentTimeMillis();
                    //this.board = game.getBoard();
//                    currentTetromino = game.getCurrentTetromino();
//                    nextTetromino = game.getNextTetromino();
//                    ghostTetromino = game.getGhostTetromino();
                    generateTetromino();
                    dropPieces();
                    newGame = false;
                }

                if(gameDone) {
                    startGameText.setText(" ");
                    Game game = new Game();
                    this.game = game;

                    startTime = System.currentTimeMillis();
                    //this.board = game.getBoard();
                    gameOverText.setText(" ");

                    clearRows();
                    playArea.getChildren().remove(nextTetrominoGraphic);
                    this.blockFXArray = new Rectangle[24][10];

//                    currentTetromino = game.getCurrentTetromino();
//                    nextTetromino = game.getNextTetromino();
//                    ghostTetromino = game.getGhostTetromino();
                    generateTetromino();
                    dropPieces();
                    gameDone = false;
                }
                break;
            case ESCAPE:
                System.exit(0);
                break;
        }

    }

    /**
     * Creates a new timer thread.
     * Tetrominos will drop at a rate determined by the timer
     */
    public void dropPieces() {
        new Timer().schedule(
                new TimerTask() {
                        @Override
                        public void run () {
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    if(!gameDone) {
                                        tryMove('s', game.getBoard());
                                        currentTetrominoGraphic.setLayoutY(game.getCurrentTetromino().getYReference() * boardCellWidth + boardLayoutY);
                                        gameDone = game.getBoard().isGameDone();
                                    } else {
                                        cancel();
                                    }
                                }
                            });
                        }
                }, 0, dropSpeed);
    }

    /**
     * Generates a new Tetromino
     * Loads a FXML file for a new Tetromino and for the next Tetromino then sets their position
     */
    public void generateTetromino() {

        double ghostOpacity = 0.15;

        //String array containing the resource URLs of the Tetromino FXMLs
        String[] tetrominoFXML_URL = new String[]{"Resources/ITetromino.fxml","Resources/OTetromino.fxml","Resources/TTetromino.fxml",
                                            "Resources/STetromino.fxml" ,"Resources/ZTetromino.fxml","Resources/JTetromino.fxml","Resources/LTetromino.fxml"};

        //Get the type of the new and next Tetrominos
        int newTetNum = game.getCurrentTetromino().getType();
        int nextTetNum = game.getNextTetromino().getType();

        //Load the FXML file for the new Tetromino
        try {
            StackPane newTetromino = FXMLLoader.load(TetrisController.class.getResource(tetrominoFXML_URL[newTetNum]));
            playArea.getChildren().add(newTetromino);
            currentTetrominoGraphic = newTetromino;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Load the FXML file for the new Tetromino
        try {
            StackPane ghostTetromino = FXMLLoader.load(TetrisController.class.getResource(tetrominoFXML_URL[newTetNum]));
            playArea.getChildren().add(ghostTetromino);
            ghostTetrominoGraphic = ghostTetromino;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Load the FXML file for the next Tetromino
        try {
            StackPane nextTetromino = FXMLLoader.load(TetrisController.class.getResource(tetrominoFXML_URL[nextTetNum]));
            playArea.getChildren().add(nextTetromino);
            nextTetrominoGraphic = nextTetromino;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Sets the new Tetromino to the starting location
        currentTetrominoGraphic.setLayoutX(game.getCurrentTetromino().getXReference() * boardCellWidth + boardLayoutX);
        currentTetrominoGraphic.setLayoutY(game.getCurrentTetromino().getYReference() * boardCellWidth + boardLayoutY);
        ghostTetrominoGraphic.setLayoutX(game.getGhostTetromino().getXReference() * boardCellWidth + boardLayoutX);
        ghostTetrominoGraphic.setLayoutY(game.getGhostTetromino().getYReference() * boardCellWidth + boardLayoutY);
        ghostTetrominoGraphic.setOpacity(ghostOpacity);

        currentTetrominoGraphic.toBack();
        ghostTetrominoGraphic.toBack();

        //Sets the next Tetromino to the display box location
        switch(nextTetNum) {
            case 0: nextTetrominoGraphic.setLayoutX(nextBoxLayoutX + 20); nextTetrominoGraphic.setLayoutY(nextBoxLayoutY + 60); break;
            case 1: nextTetrominoGraphic.setLayoutX(nextBoxLayoutX + 30); nextTetrominoGraphic.setLayoutY(nextBoxLayoutY + 80); break;
            case 2: nextTetrominoGraphic.setLayoutX(nextBoxLayoutX + 20); nextTetrominoGraphic.setLayoutY(nextBoxLayoutY + 80); break;
            case 3: nextTetrominoGraphic.setLayoutX(nextBoxLayoutX + 10); nextTetrominoGraphic.setLayoutY(nextBoxLayoutY + 70); break;
            case 4: nextTetrominoGraphic.setLayoutX(nextBoxLayoutX + 20); nextTetrominoGraphic.setLayoutY(nextBoxLayoutY + 80); break;
            case 5: nextTetrominoGraphic.setLayoutX(nextBoxLayoutX + 10); nextTetrominoGraphic.setLayoutY(nextBoxLayoutY + 70); break;
            case 6: nextTetrominoGraphic.setLayoutX(nextBoxLayoutX + 10); nextTetrominoGraphic.setLayoutY(nextBoxLayoutY + 70); break;
        }
    }

    /**
     * Adds blocks to the grid when a Tetromino is placed
     * @param layoutX
     * @param layoutY
     * @param blockColor
     */
    private void addBlock(int layoutX, int layoutY, int blockColor) {

        String[] tetrominoColor = new String[]{"1eeaff","fffe21","ff1ee6","1eff5e" ,"ff1e1e","231eff","ff8c1e"};

        Rectangle block = null;
        try {
            block = FXMLLoader.load(getClass().getResource("Resources/Block.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] gridCoords = getGridCoordinates(layoutY, layoutX);


        if (blockFXArray[gridCoords[1]][gridCoords[0]] == null){
            playArea.getChildren().add(block);
            blockFXArray[gridCoords[1]][gridCoords[0]] = block;
        }

        block.setLayoutX(layoutX);
        block.setLayoutY(layoutY);
        block.setFill(Color.web(tetrominoColor[blockColor]));
        block.toBack();
    }


    /**
     * Converts from grid coordinates to screen coordinates
     * @param row
     * @param col
     * @return screenCoords int array
     */
    public int[] getScreenCoordinates(int row, int col) {
        int xCoord = col * boardCellWidth + boardLayoutX;
        int yCoord = row * boardCellWidth + boardLayoutY;
        int[] screenCoords = new int[2];
        screenCoords[0] = xCoord;
        screenCoords[1] = yCoord;
        return screenCoords;
    }

    /**
     * Converts from screen coordinates to grid coordinates
     * @param row
     * @param col
     * @return
     */
    public int[] getGridCoordinates(int row, int col) {
        int xCoord = (col - boardLayoutX)/boardCellWidth;
        int yCoord = (row - boardLayoutY)/boardCellWidth;
        int[] gridCoords = new int[2];
        gridCoords[0] = xCoord;
        gridCoords[1] = yCoord;
        return gridCoords;
    }

    /**
     * Updates all blocks on the grid. Matches blocks with the current board object
     */
    public void updateBoardFX() {
        Block[][] currentBoard = game.getBoard().getCurrentBoard();
        for(int row = 0; row < currentBoard.length; row++){
            for(int col = 0; col < currentBoard[0].length; col++){
                if (currentBoard[row][col] != null) {
                    int blockColor = currentBoard[row][col].getColor();
                    int[] screenCoords = getScreenCoordinates(row,col);
                    addBlock(screenCoords[0], screenCoords[1], blockColor);
                }

            }
        }
    }


    /**
     * Clears rows of blocks and updates blocks on grid whenever a line is cleared.
     * Updates score
     */
    public void clearRows() {
        game.getBoard().getRowsToClear();
        Boolean graphicsDone = false;
        int counter;

        for (int row = 0; row < 24; row++) {
            for (int col = 0; col < 10; col++) {
                playArea.getChildren().remove(blockFXArray[row][col]);
                blockFXArray[row][col] = null;
            }
        }

        //Update score and lines cleared text
        scoreText.setText(Long.toString(game.getGameScore()));
        linesClearedText.setText(Long.toString(game.getLinesCleared()));

        //Update the block graphics on the board
        updateBoardFX();
        game.getBoard().resetRowsToClear();
    }

//    /**
//     * Calls the tryMove function in the Game class. Updates all Tetrominos and board object
//     * Calls a sequence of functions to update the board
//     * Checks if the game is over to display "GAME OVER" text
//     * @param moveType
//     * @param board
//     */
//    public void tryMove(char moveType, Board board) {
//        boolean tetrominoPlaced = game.tryMove(moveType,board);
//
////        this.currentTetromino = game.getCurrentTetromino();
////        this.nextTetromino = game.getNextTetromino();
////        this.ghostTetromino = game.getGhostTetromino();
//        //this.board = game.getBoard();
//
//        gameDone = board.isGameDone();
//
//        if (tetrominoPlaced) {
//            removePlacedTetromino();
//        }
//
//        if (tetrominoPlaced) {
//            if(!gameDone) {
//                generateTetromino();
//            }
//            updateBoardFX();
//            if (game.getBoard().getRowsToClear().size() > 0) {
//                clearRows();
//            }
//        }
//
//        if(gameDone) {
//            gameOverText.setText("Game Over");
//            startGameText.setText("Press ENTER to start new game");
//        }
//
//    }

    /**
     * Calls the tryMove function in the Game class. Updates all Tetrominos and board object
     * Calls a sequence of functions to update the board
     * Checks if the game is over to display "GAME OVER" text
     * @param moveType
     * @param board
     */
    public void tryMove(char moveType, Board board) {
        boolean canMove = game.tryMove(moveType,board);

//        this.currentTetromino = game.getCurrentTetromino();
//        this.nextTetromino = game.getNextTetromino();
//        this.ghostTetromino = game.getGhostTetromino();
        //this.board = game.getBoard();

        gameDone = board.isGameDone();

        if(canMove) {
            moveTetromino();
        }

        if (moveType == 'f' || !canMove) {
            removePlacedTetromino();
        }

        if (moveType == 'f' || !canMove) {
            if(!gameDone) {
                generateTetromino();
            }
            updateBoardFX();
            if (game.getBoard().getRowsToClear().size() > 0) {
                clearRows();
            }
        }

        if(gameDone) {
            gameOverText.setText("Game Over");
            startGameText.setText("Press ENTER to start new game");
        }
    }

    /**
     * Removes the Tetromino StackPane when it is placed
     * Also removes the next Tetromino Stackpane and Ghost Tetromino Stackpane
     */
    public void removePlacedTetromino() {
        //Remove placed Tetromino
        playArea.getChildren().remove(currentTetrominoGraphic);

        //Remove the old Tetromino from the next Tetromino display box
        if(!gameDone) {
            playArea.getChildren().remove(nextTetrominoGraphic);
        }

        //Remove the old ghost Tetromino
        playArea.getChildren().remove(ghostTetrominoGraphic);
    }

    /**
     * Moves current Tetromino and ghost Tetromino graphics to their new locations
     */
    public void moveTetromino() {
        currentTetrominoGraphic.setLayoutX(game.getCurrentTetromino().getXReference() * boardCellWidth + boardLayoutX);
        currentTetrominoGraphic.setLayoutY(game.getCurrentTetromino().getYReference() * boardCellWidth + boardLayoutY);
        currentTetrominoGraphic.setRotate(game.getCurrentTetromino().getRotation());
        ghostTetrominoGraphic.setRotate(game.getCurrentTetromino().getRotation());
        ghostTetrominoGraphic.setLayoutX(game.getGhostTetromino().getXReference() * boardCellWidth + boardLayoutX);
        ghostTetrominoGraphic.setLayoutY(game.getGhostTetromino().getYReference() * boardCellWidth + boardLayoutY);

    }
}
