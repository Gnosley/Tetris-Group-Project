
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static javafx.scene.input.KeyCode.Q;

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
    private StackPane storedTetrominoGraphic;

    private long startTime = 0;

    //private Board board;
    private Game game;
    private Timer timer;
    private static GameSettings gameSettings;

//    private Tetromino currentTetromino; // the tetromino currently in play
//    private Tetromino nextTetromino; // the tetromino to be played next
//    private Tetromino ghostTetromino; // the ghost tetromino to drop
//    private static final int startingX = 3;
//    private static final int startingY = 0;

    private boolean gameDone = false;
    private boolean newGame;
    private boolean clearingRows = false;
    private static BooleanProperty clearingRowsObs = new SimpleBooleanProperty();

    private int currentDifficulty;
    private int dropSpeed = 300;
    private Rectangle[][] blockFXArray;

    private int boardCellWidth = 20;
    private int boardLayoutX = 160;
    private int boardLayoutY = 20;
    private int nextBoxLayoutX = 400;
    private int nextBoxLayoutY = 60;
    private int storedBoxLayoutX = 20;
    private int storedBoxLayoutY = 60;

    /**
     * Initializes values when Tetris window opens
     */
    public void initialize() {
        this.blockFXArray = new Rectangle[24][10];
        Game game = new Game();
        this.game = game;
        newGame = true;
        Timer timer = new Timer();
        this.timer = timer;

//        GameSettings gameSettings = new GameSettings();
//        this.gameSettings = gameSettings;
//        clearingRowsObs.bind(clearingRows);
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
    //TODO convert this to a couple if statements
//        switch (event.getCode()) {
//            //Move Tetromino left
//            case gameSettings.getControls()[0]: if(!gameDone && !newGame) { tryMove('a', game.getBoard());
//                System.out.println(gameSettings.getDifficulty());} break;
//            //Move Tetromino right
//            case RIGHT: if(!gameDone && !newGame) { tryMove('d', game.getBoard()); } break;
//            //Rotate Tetromino clockwise
//            case UP: if(!gameDone && !newGame) { tryMove('e', game.getBoard()); } break;
//            //Rotate Tetromino counter clockwise
//            case X: if(!gameDone && !newGame) { tryMove('q', game.getBoard()); } break;
//            //Move Tetromino down once (Soft Drop)
//            case DOWN:
//                if(!gameDone && !newGame) {
//                    tryMove('s', game.getBoard());
//                    gameDone = game.getBoard().isGameDone();
//                }
//                break;
//            //Drop Tetromino to the bottom (Hard Drop)
//            case SPACE: if(!gameDone && !newGame) {
//                tryMove('f', game.getBoard());
//                gameDone = game.getBoard().isGameDone();
//            }
//                break;
//            //Hold Tetromino
//            case C:
//                if(!gameDone && !newGame) {
//                    tryMove('w', game.getBoard());
//                }
//                break;
//            //New Game
//            case ENTER:
//                System.out.println(gameSettings.getDifficulty());
//                if(newGame) {
//                    startGameText.setText(" ");
//                    startTime = System.currentTimeMillis();
//                    //this.board = game.getBoard();
////                    currentTetromino = game.getCurrentTetromino();
////                    nextTetromino = game.getNextTetromino();
////                    ghostTetromino = game.getGhostTetromino();
//                    generateTetromino();
//                    dropPieces();
//                    newGame = false;
//                }
//
//                if(gameDone) {
//                    startGameText.setText(" ");
//                    Game game = new Game();
//                    this.game = game;
//
//                    startTime = System.currentTimeMillis();
//                    //this.board = game.getBoard();
//                    gameOverText.setText(" ");
//
//                    clearRows();
//                    playArea.getChildren().remove(nextTetrominoGraphic);
//                    this.blockFXArray = new Rectangle[24][10];
//
////                    currentTetromino = game.getCurrentTetromino();
////                    nextTetromino = game.getNextTetromino();
////                    ghostTetromino = game.getGhostTetromino();
//                    generateTetromino();
//                    dropPieces();
//                    gameDone = false;
//                }
//                break;
//            //Escape application
//            case ESCAPE: System.exit(0); break;
//        }
        if(!clearingRows) {
            //Move Tetromino left
            if (event.getCode().equals(gameSettings.getControls()[0])) {
                tryMove('a', game.getBoard());
            }
            //Move Tetromino right
            if (event.getCode().equals(gameSettings.getControls()[1])) {
                tryMove('d', game.getBoard());
            }
            //Rotate Tetromino clockwise
            if (event.getCode().equals(gameSettings.getControls()[2])) {
                tryMove('e', game.getBoard());
            }
            //Rotate Tetromino counter clockwise
            if (event.getCode().equals(gameSettings.getControls()[3])) {
                tryMove('q', game.getBoard());
            }
            //Move Tetromino down once (Soft Drop)
            if (event.getCode().equals(gameSettings.getControls()[4])) {
                if (!gameDone && !newGame) {
                    tryMove('s', game.getBoard());
                    gameDone = game.getBoard().isGameDone();
                }
            }
            //Drop Tetromino to the bottom (Hard Drop)
            if (event.getCode().equals(gameSettings.getControls()[5])) {
                if (!gameDone && !newGame) {
                    tryMove('f', game.getBoard());
                    gameDone = game.getBoard().isGameDone();
                }
            }
            //Hold Tetromino
            if (event.getCode().equals(gameSettings.getControls()[6])) {
                if (!gameDone && !newGame) {
                    tryMove('w', game.getBoard());
                }
            }
            //New Game
            if (event.getCode().equals(gameSettings.getControls()[7])) {
                if (newGame) {
                    clearingRowsObs.setValue(false);
                    clearingRowsObs.addListener(new ChangeListener<Boolean>() {
                        public void changed(ObservableValue ov, Boolean old_val, Boolean new_val) {
                            if (new_val == true) {
                                clearingRowsObs.setValue(false);
                                clearingRows = false;
                                generateTetromino();
                                dropPieces();
                            }
                        }
                    });
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

                if (gameDone) {
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
            }
        }
        //Escape application
        if(event.getCode().equals(gameSettings.getControls()[8])) {
            System.exit(0);
        }
        if(event.getCode().equals(Q)) {
            this.clearingRowsObs.setValue(true);
        }
    }

    /**
     * Creates a new timer thread.
     * Tetrominos will drop at a rate determined by the timer
     */
    public void dropPieces() {
//        new Timer("droptimer").schedule(
//                new TimerTask() {
//                        @Override
//                        public void run () {
//                            Platform.runLater(new Runnable() {
//                                public void run() {
//                                    System.out.println(clearingRows);
//                                    if(clearingRows) {
//                                        System.out.println("Timer Cancelled");
//                                        cancel();
//                                    }
//                                    if(!gameDone) {
//                                        tryMove('s', game.getBoard());
//                                        moveTetromino();
//                                        gameDone = game.getBoard().isGameDone();
//                                    } else {
//                                        System.out.println("Timer Cancelled");
//                                        cancel();
//                                    }
//                                }
//                            });
//                        }
//                }, 0, gameSettings.getDropSpeed());

        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run () {
                Platform.runLater(new Runnable() {
                    public void run() {
//                        System.out.println(clearingRows);
//                        if(clearingRows) {
//                            System.out.println("Timer Cancelled");
//                            //cancel();
//                        }
                        if(!gameDone) {
                            tryMove('s', game.getBoard());
                            moveTetromino();
                            gameDone = game.getBoard().isGameDone();
                        } else {
                            System.out.println("Timer Cancelled");
                            cancel();
                        }
                    }
                });
            }
        };

        timer.schedule(task, 0, gameSettings.getDropSpeed());
    }

    /**
     * Calls the tryMove function in the Game class. Updates all Tetrominos and board object
     * Calls a sequence of functions to update the board
     * Checks if the game is over to display "GAME OVER" text
     * @param moveType
     * @param board
     */
    public void tryMove(char moveType, Board board) {
        boolean canMove = game.tryMove(moveType);
        boolean removeStored = false;

        Block[][] oldBoard = game.getBoard().getPreClearedBoard();

        if(game.getBoard().getRowsToClear().size() > 0) {
            System.out.println("cmon clearing rows");
            clearingRows = true;
            timer.cancel();
        }

//        this.currentTetromino = game.getCurrentTetromino();
//        this.nextTetromino = game.getNextTetromino();
//        this.ghostTetromino = game.getGhostTetromino();
        //this.board = game.getBoard();

        gameDone = board.isGameDone();

        //Move the Tetromino if canMove is true
        if (canMove) {
            moveTetromino();
        }

        //Remove the existing Tetromino graphics if the move was a drop, a hold, or if canMove is false
        if (moveType == 'f' || moveType == 'w' || !canMove) {
            //Remove the stored Tetromino graphic only if a hold move was input
            if(moveType == 'w') {
                removeStored = true;
            }
            removePlacedTetrominos(removeStored);
        }

        //Calls the storeTetromino method when a hold move is input
        if (moveType == 'w') {
            storeTetromino();
        }

        //generate new Tetromino graphics and update the board if the move was a drop, a hold, or if canMove is false
        if (moveType == 'f' || moveType == 'w' || !canMove) {
//            if(!gameDone) {
//                //generates new Tetromino graphics
//                generateTetromino();
//            }
            //Clears block graphics from a row
            if (game.getBoard().getRowsToClear().size() > 0) {
                updateBoardFX(oldBoard);
                clearRows(oldBoard);
            } else {
                //Updates the board block graphics
                updateBoardFX();
            }

            if(!gameDone && !clearingRows) {
                //generates new Tetromino graphics
                generateTetromino();
            }
        }

        //Displays Game Over text if the game has ended
        if(gameDone) {
            gameOverText.setText("Game Over");
            gameOverText.toFront();
            startGameText.setText("Press ENTER to start new game");
            startGameText.toFront();
        }
    }

    /**
     * Moves current Tetromino and ghost Tetromino graphics to their new locations and rotations
     */
    public void moveTetromino() {
        currentTetrominoGraphic.setLayoutX(game.getCurrentTetromino().getXReference() * boardCellWidth + boardLayoutX);
        currentTetrominoGraphic.setLayoutY(game.getCurrentTetromino().getYReference() * boardCellWidth + boardLayoutY);
        currentTetrominoGraphic.setRotate(game.getCurrentTetromino().getRotation());
        ghostTetrominoGraphic.setRotate(game.getCurrentTetromino().getRotation());
        ghostTetrominoGraphic.setLayoutX(game.getGhostTetromino().getXReference() * boardCellWidth + boardLayoutX);
        ghostTetrominoGraphic.setLayoutY(game.getGhostTetromino().getYReference() * boardCellWidth + boardLayoutY);
    }

    /**
     * Removes the Tetromino StackPane when it is placed
     * Also removes the next Tetromino Stackpane and Ghost Tetromino Stackpane
     */
    public void removePlacedTetrominos(boolean removeStored) {
        //Remove placed Tetromino
        playArea.getChildren().remove(currentTetrominoGraphic);

        //Remove the old Tetromino from the next Tetromino display box
        if(!gameDone) {
            playArea.getChildren().remove(nextTetrominoGraphic);
        }

        //Remove the old Tetromino from the stored Tetromino display box
        if(!gameDone && removeStored) {
            playArea.getChildren().remove(storedTetrominoGraphic);
        }

        //Remove the old ghost Tetromino
        playArea.getChildren().remove(ghostTetrominoGraphic);
    }

    public void storeTetromino() {
        int storedTetNum = game.getStoredTetromino().getType();

        //String array containing the resource URLs of the Tetromino FXMLs
        String[] tetrominoFXML_URL = new String[]{"Resources/ITetromino.fxml","Resources/OTetromino.fxml","Resources/TTetromino.fxml",
                "Resources/STetromino.fxml" ,"Resources/ZTetromino.fxml","Resources/JTetromino.fxml","Resources/LTetromino.fxml"};

        //Load the FXML file for the stored Tetromino
        try {
            StackPane storedTetromino = FXMLLoader.load(TetrisController.class.getResource(tetrominoFXML_URL[storedTetNum]));
            playArea.getChildren().add(storedTetromino);
            storedTetrominoGraphic = storedTetromino;
        } catch (IOException e) {
            e.printStackTrace();
        }

        placeStoredTetrominoGraphic(storedTetNum);

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

        //Load the FXML file for the ghost Tetromino
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
        moveTetromino();


        ghostTetrominoGraphic.setOpacity(ghostOpacity);
        currentTetrominoGraphic.toBack();
        ghostTetrominoGraphic.toBack();

        placeNextTetrominoGraphic(nextTetNum);
    }


    /**
     * Sets the next Tetromino to the display box location
     * @param nextTetNum
     */
    public void placeNextTetrominoGraphic(int nextTetNum) {
        int[][] nextOffsetArray = new int[][]{{20,60},{30,80},{20,80},{10,70},{20,80},{10,70},{10,70}};
        nextTetrominoGraphic.setLayoutX(nextBoxLayoutX + nextOffsetArray[nextTetNum][0]);
        nextTetrominoGraphic.setLayoutY(nextBoxLayoutY + nextOffsetArray[nextTetNum][1]);
    }

    /**
     * Sets the stored Tetromino to the display box location
     * @param storedTetNum
     */
    public void placeStoredTetrominoGraphic(int storedTetNum) {
        int[][] storedOffsetArray = new int[][]{{20,60},{30,80},{20,80},{10,70},{20,80},{10,70},{10,70}};
        storedTetrominoGraphic.setLayoutX(storedBoxLayoutX + storedOffsetArray[storedTetNum][0]);
        storedTetrominoGraphic.setLayoutY(storedBoxLayoutY + storedOffsetArray[storedTetNum][1]);
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
     * Updates all blocks on the grid to match the board before a line was cleared.
     * Matches blocks with the previous board object
     */
    public void updateBoardFX(Block[][] oldBoard) {
        for(int row = 0; row < oldBoard.length; row++){
            for(int col = 0; col < oldBoard[0].length; col++){
                if (oldBoard[row][col] != null) {
                    int blockColor = oldBoard[row][col].getColor();
                    int[] screenCoords = getScreenCoordinates(row,col);
                    addBlock(screenCoords[0], screenCoords[1], blockColor);
                }
            }
        }
    }

    /**
     * Updates all blocks on the grid. Matches blocks with the current board object
     */
    public void updateBoardFX(Block[][] currentBoard, Rectangle[][] blockFXArray, AnchorPane playArea) {
//        Block[][] currentBoard = game.getBoard().getCurrentBoard();
        for(int row = 0; row < currentBoard.length; row++){
            for(int col = 0; col < currentBoard[0].length; col++){
                if (currentBoard[row][col] != null) {
                    int blockColor = currentBoard[row][col].getColor();
                    int[] screenCoords = getScreenCoordinates(row,col);
                    addBlock(screenCoords[0], screenCoords[1], blockColor, blockFXArray, playArea);
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

        System.out.println(game.getBoard().getRowsToClear().toString());

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

    /**
     * Clears rows of blocks and updates blocks on grid whenever a line is cleared.
     * Updates score
     */
    public void clearRows(Block[][] oldBoard) {
        game.getBoard().getRowsToClear();
        Boolean graphicsDone = false;
        int counter;

        ClearLines C1 = new ClearLines(game.getBoard().getCurrentBoard(), oldBoard, game.getBoard().getRowsToClear(), blockFXArray, playArea);
        C1.start();

//        for (int row = 0; row < 24; row++) {
//            for (int col = 0; col < 10; col++) {
//                playArea.getChildren().remove(blockFXArray[row][col]);
//                blockFXArray[row][col] = null;
//            }
//        }

        //Update score and lines cleared text
        scoreText.setText(Long.toString(game.getGameScore()));
        linesClearedText.setText(Long.toString(game.getLinesCleared()));

        //Update the block graphics on the board
        //updateBoardFX();
        game.getBoard().resetRowsToClear();
    }

    /**
     * Adds blocks to the grid when a Tetromino is placed
     * @param layoutX
     * @param layoutY
     * @param blockColor
     */
    public void addBlock(int layoutX, int layoutY, int blockColor) {

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
     * Adds blocks to the grid when a Tetromino is placed
     * @param layoutX
     * @param layoutY
     * @param blockColor
     */
    public void addBlock(int layoutX, int layoutY, int blockColor, Rectangle[][] blockFXArray, AnchorPane playArea) {

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

    public void setGameSettings(GameSettings gameSettings) { // Setting the client-object in ClientViewController
        this.gameSettings = gameSettings;
    }

    public Game getGame() {
        return this.game;
    }

    public static void setClearingRowsTrue() {
        System.out.println("Set to true");
        clearingRowsObs.setValue(true);
    }


    /**
     * Returns to main menu when the "Main Menu" button is clicked
     * @param event
     */
    @FXML
    private void loadMainMenu(MouseEvent event) throws IOException {
        Parent tetrisRoot = FXMLLoader.load(getClass().getResource("Resources/TetrisMenu.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(tetrisRoot, 580 ,510));
        stage.setTitle("Tetris Main Menu");
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}