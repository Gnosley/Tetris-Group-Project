
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controls all actions in the Tetris window
 */
public class TetrisController {

    @FXML
    private AnchorPane playArea;

    @FXML
    private ImageView image;

    @FXML
    private Text scoreText;

    @FXML
    private Text linesClearedText;

    @FXML
    private Text gameStats;

    @FXML
    private Text currentDifficultyText;

    @FXML
    private Text currentUser;

    @FXML
    private Text highScoreUser;

    @FXML
    private Text highScore;

    @FXML
    private Label gameOverText;

    @FXML
    private Text startGameText;

    private StackPane currentTetrominoGraphic;
    private StackPane nextTetrominoGraphic;
    private StackPane ghostTetrominoGraphic;
    private StackPane storedTetrominoGraphic;

    private Game game;
    private Timer timer;
    private static GameSettings gameSettings;
    private MediaPlayer themePlayer;

    private boolean gameDone = false;
    private boolean newGame;
    private boolean clearingRows = false;
    private static BooleanProperty clearingRowsObs;

    private Rectangle[][] blockFXArray;

    //Layout values
    private int boardCellWidth = 20;
    private int boardLayoutX = 190;
    private int boardLayoutY = 20;
    private int nextBoxLayoutX = 430;
    private int nextBoxLayoutY = 60;
    private int storedBoxLayoutX = 50;
    private int storedBoxLayoutY = 60;



    /**
     * Initializes values when Tetris window opens
     */
    public void initialize() {
        newGame = true;
        Timer timer = new Timer();
        this.timer = timer;
    }

    /**
     * Handles the keyboard controls of the game
     * --STANDARD CONTROLS--
     * ENTER: Start Game
     * LEFT: Move Tetromino left
     * RIGHT: Move Tetromino right
     * UP: Rotates the Tetromino clockwise
     * X: Rotates the Tetromino counter clockwise
     * DOWN: Move Tetromino down (soft drop)
     * SPACE: Drop Tetromino (hard drop)
     * C: Holds the current Tetromino
     * ENTER: Starts new game when available
     * ESC: Exit Application
     * @param event
     * @throws IOException
     */
    @FXML
    protected void handleKeyboardAction(KeyEvent event) throws IOException {

        if(!clearingRows) {

            //Left: Move Tetromino left
            if (event.getCode().equals(gameSettings.getControls()[0])) {
                tryMove('a');
            }

            //Right: Move Tetromino right
            if (event.getCode().equals(gameSettings.getControls()[1])) {
                tryMove('d');
            }

            //Rotate CW: Rotate Tetromino clockwise
            if (event.getCode().equals(gameSettings.getControls()[2])) {
                tryMove('e');
            }

            //Rotate CCW: Rotate Tetromino counter clockwise
            if (event.getCode().equals(gameSettings.getControls()[3])) {
                tryMove('q');
            }

            //Down: Move Tetromino down once (Soft Drop)
            if (event.getCode().equals(gameSettings.getControls()[4])) {
                if (!gameDone && !newGame) {
                    tryMove('s');
                    gameDone = game.isGameDone();
                }
            }

            //Drop: Drops Tetromino to the bottom (Hard Drop)
            if (event.getCode().equals(gameSettings.getControls()[5])) {
                if (!gameDone && !newGame) {
                    tryMove('f');
                    gameDone = game.isGameDone();
                }
            }

            //Hold: Holds the current Tetromino
            if (event.getCode().equals(gameSettings.getControls()[6])) {
                if (!gameDone && !newGame) {
                    tryMove('w');
                }
            }

            //Enter: New Game
            if (event.getCode().equals(gameSettings.getControls()[7])) {
                //Sequence of code that runs when a new game is started for first time or after a gameover
                if(newGame || gameDone) {
                    Game game = new Game(gameSettings.getUser(), gameSettings.getLevel());
                    this.game = game;
                    startGameText.setText(" ");

                    //Sequence of code that runs when a new game is started for first time
                    if (newGame) {
                        currentDifficultyText.setText(gameSettings.getLevel());
                        currentUser.setText(gameSettings.getUser());

                        String musicFile = "src/TetrisTheme.wav";
                        Media tetrisTheme = new Media(new File(musicFile).toURI().toString());
                        this.themePlayer = new MediaPlayer(tetrisTheme);
                        // Play the media at the desired rate on a loop
                        themePlayer.setRate(0.85 + (gameSettings.getDropSpeedLevel() / 10.0) * 0.3);
                        themePlayer.setCycleCount(MediaPlayer.INDEFINITE);
                        themePlayer.play();

                        highScoreUser.setText(game.getHighScoreName());
                        highScore.setText(": " + game.getHighScore());

                        clearingRowsObs = new SimpleBooleanProperty();

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
                        newGame = false;
                    }

                    //Sequence of code that runs when a new game is started after a gameover
                    if (gameDone) {
//                        gameOverText.setText(" ");
                        gameOverText.setVisible(false);
                        clearRows();
                        playArea.getChildren().remove(nextTetrominoGraphic);
                        gameDone = false;
                    }

                    //Sequence of code that runs when a new game is started for first time or after a gameover
                    this.blockFXArray = new Rectangle[24][10];
                    generateTetromino();
                    dropPieces();
                }
            }
        }

        //ESC: Escape application
        if(event.getCode().equals(gameSettings.getControls()[8])) {
            System.exit(0);
        }
    }

    /**
     * Creates a new timer thread.
     * Tetrominos will drop at a rate determined by the timer
     */
    public void dropPieces() {

        timer = new Timer();

        //TimerTask to set the soft drop
        TimerTask task = new TimerTask() {
            @Override
            public void run () {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //Continuously performs soft drop until the game ends
                        if(!gameDone) {
                            tryMove('s');
                            moveTetromino();
                            gameDone = game.isGameDone();
                        } else {
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
     */
    public void tryMove(char moveType) {
        boolean canMove = game.tryMove(moveType);
        boolean removeStored = false;

        //Checks if a row needs to be cleared. Sets boolean value and cancels the current timer
        if(game.getRowsToClear().size() > 0) {
            clearingRows = true;
            timer.cancel();
        }

        gameDone = game.isGameDone();

        //Move the Tetromino if canMove is true
        if (canMove) {
            moveTetromino();
        }

        //Remove the existing Tetromino graphics if the move was a drop, a hold, or if canMove is false
        if (moveType == 'f' || moveType == 'w' || (!canMove && moveType == 's')) {
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
        if (moveType == 'f' || moveType == 'w' || (!canMove && moveType == 's')) {
            gameStats.setText(game.getPieceStats()[0] + "\n" + game.getPieceStats()[1] + "\n"
                    + game.getPieceStats()[2] + "\n" + game.getPieceStats()[3] + "\n" + game.getPieceStats()[4]
                    + "\n" + game.getPieceStats()[5] + "\n" + game.getPieceStats()[6]);

            //Clears block graphics from a row
            if (game.getRowsToClear().size() > 0) {
                updateBoardFX(game.getPreClearedBoard());
                clearRows(game.getPreClearedBoard());
            } else {
                //Updates the board block graphics
                updateBoardFX(game.getBoard());
            }

            if(!gameDone && !clearingRows) {
                //generates new Tetromino graphics
                generateTetromino();
            }
        }

        //Displays Game Over text if the game has ended
        if(gameDone) {
            game.writeScoreToFile();
//            gameOverText.setText("Game Over");
            gameOverText.setVisible(true);
            gameOverText.toFront();
            startGameText.setLayoutX(43);
            startGameText.setLayoutY(35);
            startGameText.setWrappingWidth(300);
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

    /**
     * Method to load the stored Tetromino FXML and place it in the window
     */
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

        //Calls method to place the stored Tetromino graphic in the window
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

        //Calls method to place the next Tetromino graphic in the window
        placeNextTetrominoGraphic(nextTetNum);
    }


    /**
     * Sets the next Tetromino to the display box location. Uses offset values to align properly.
     * @param nextTetNum
     */
    public void placeNextTetrominoGraphic(int nextTetNum) {
        //I,O,T,S,Z,J,L
        double[][] nextOffsetArray = new double[][]{{10,62.5},{30,70},{20,70},{20,70},{20,70},{20,70},{20,70}};
        nextTetrominoGraphic.setLayoutX(nextBoxLayoutX + nextOffsetArray[nextTetNum][0]);
        nextTetrominoGraphic.setLayoutY(nextBoxLayoutY + nextOffsetArray[nextTetNum][1]);
    }

    /**
     * Sets the stored Tetromino to the display box location. Uses offset values to align properly.
     * @param storedTetNum
     */
    public void placeStoredTetrominoGraphic(int storedTetNum) {
        //I,O,T,S,Z,J,L
        double[][] storedOffsetArray = new double[][]{{10,62.5},{30,70},{20,70},{20,70},{20,70},{20,70},{20,70}};
        storedTetrominoGraphic.setLayoutX(storedBoxLayoutX + storedOffsetArray[storedTetNum][0]);
        storedTetrominoGraphic.setLayoutY(storedBoxLayoutY + storedOffsetArray[storedTetNum][1]);
    }

    /**
     * Updates all blocks on the grid to match the board before a line was cleared.
     * Matches blocks with the previous board object
     */
    public void updateBoardFX(Block[][] board) {
        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                if (board[row][col] != null) {
                    int blockColor = board[row][col].getColor();
                    int[] screenCoords = getScreenCoordinates(row,col);
                    addBlock(screenCoords[0], screenCoords[1], blockColor);
                }
            }
        }
    }

    /**
     * Updates all blocks on the grid. Matches blocks with the current board object
     * This overloaded method can be called from the ClearLines thread
     */
    public void updateBoardFX(Block[][] currentBoard, Rectangle[][] blockFXArray, AnchorPane playArea) {
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
        game.getRowsToClear();

        //Removes all blocks from the grid
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
        updateBoardFX(game.getBoard());
        //Resets the array of rows to clear
        game.resetRowsToClear();
    }

    /**
     * Clears rows of blocks and updates blocks on grid whenever a line is cleared.
     * Updates score
     */
    public void clearRows(Block[][] oldBoard) {
        game.getRowsToClear();

        //Creates new thread to perform the ClearLines Class animation
        ClearLines C1 = new ClearLines(game.getBoard(), game.getRowsToClear(), blockFXArray, playArea);
        C1.start();

        //Update score and lines cleared text
        scoreText.setText(Long.toString(game.getGameScore()));
        linesClearedText.setText(Long.toString(game.getLinesCleared()));

        //Resets the array of rows to clear
        game.resetRowsToClear();
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
     * This overloaded method can be called from the ClearLines thread
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
     * Converts from grid coordinates to screen coordinates (pixels)
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
     * Converts from screen coordinates (pixels) to grid coordinates
     * @param row
     * @param col
     * @return gridCoords int array
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
     * Setter method for the game settings
     * @param gameSettings
     */
    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    /**
     * Used to set Observable value to true after line clear animation
     */
    public static void setClearingRowsTrue() {
        clearingRowsObs.setValue(true);
    }

    /**
     * Returns to main menu when the "Main Menu" button is clicked
     * @param event
     */
    @FXML
    private void loadMainMenu(MouseEvent event) throws IOException {

        timer.cancel();

        //Load the menu from FXML
        Parent tetrisRoot = FXMLLoader.load(getClass().getResource("Resources/TetrisMenu.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(tetrisRoot, 580 ,510));
        stage.setTitle("Tetris Main Menu");
        stage.setResizable(false);
        stage.show();

        //Close application gracefully when user presses the close button on the window
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        if(themePlayer != null) {
            themePlayer.stop();
        }

        //Closes previous window
        final Node source = (Node) event.getSource();
        final Stage previousStage = (Stage) source.getScene().getWindow();
        previousStage.close();
    }
}