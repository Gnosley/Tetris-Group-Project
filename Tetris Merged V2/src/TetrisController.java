

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

//TODO
//Need to create a rectangle array that stores [Rectangle, row] when the blocks are placed.
//Need to create a ScreenCoordinate method
//Clear blocks method

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

    private StackPane currentTetrominoGraphic;
    private StackPane nextTetrominoGraphic;
    private StackPane ghostTetrominoGraphic;

    private double currentRotate;

    //public TetrisController controlTest;

    private long startTime = 0;

    private boolean pieceFalling = true;
    private Board board;
    private Game game;

    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private Tetromino ghostTetromino; // the ghost tetromino to drop
    private static final int startingX = 3;
    private static final int startingY = 0;
    private int gameScore = 0;
    private int linesCleared = 0;
    private boolean gameDone = false;
    private int currentDifficulty;
    private int dropSpeed;
    private Rectangle[][] blockFXArray;

    public void initialize() {
        this.blockFXArray = new Rectangle[24][10];
        Game game = new Game();
        this.game = game;
    }

    /**
     * Handles the keyboard controls of the game
     * ENTER: Start Game
     * LEFT: Move Tetromino left
     * RIGHT: Move Tetromino right
     * DOWN: Move Tetromino down
     * UP: Rotates the Tetromino clockwise
     * @param event
     * @throws IOException
     */
    @FXML
    protected void handleKeyboardAction(KeyEvent event) throws IOException {

        switch (event.getCode()) {
            case LEFT:
                if(!gameDone) {
                    tryMove('a', board);
                    currentTetrominoGraphic.setLayoutX(currentTetromino.getXReference() * 20 + 160);
                    ghostTetrominoGraphic.setLayoutX(ghostTetromino.getXReference() * 20 + 160);
                    ghostTetrominoGraphic.setLayoutY(ghostTetromino.getYReference() * 20 + 20);
                }
                    break;
            case RIGHT:
                if(!gameDone) {
                    tryMove('d', board);
                    currentTetrominoGraphic.setLayoutX(currentTetromino.getXReference() * 20 + 160);
                    ghostTetrominoGraphic.setLayoutX(ghostTetromino.getXReference() * 20 + 160);
                    ghostTetrominoGraphic.setLayoutY(ghostTetromino.getYReference() * 20 + 20);
                }
                    break;
            case DOWN:
                if(!gameDone) {
                    tryMove('s', board);
                    currentTetrominoGraphic.setLayoutY(currentTetromino.getYReference() * 20 + 20);
                    gameDone = board.isGameDone();
                }
                break;
            case SPACE:
                if(!gameDone) {
                    currentTetrominoGraphic.setLayoutY(game.getGhostTetromino().getYReference() * 20 + 20);
                    tryMove('f', board);
                    gameDone = board.isGameDone();
                }
                    break;
            case UP:
                if(!gameDone) {
                    tryMove('e', board);
                    currentTetrominoGraphic.setRotate(currentTetromino.getRotation());
                    ghostTetrominoGraphic.setRotate(currentTetromino.getRotation());
                    ghostTetrominoGraphic.setLayoutX(ghostTetromino.getXReference() * 20 + 160);
                    ghostTetrominoGraphic.setLayoutY(ghostTetromino.getYReference() * 20 + 20);
                }
                break;
            case ENTER:
                if(!gameDone) {
                    startTime = System.currentTimeMillis();
                    this.board = game.getBoard();
                    currentTetromino = game.getCurrentTetromino();
                    nextTetromino = game.getNextTetromino();
                    ghostTetromino = game.getGhostTetromino();
                    generateTetromino();
                    dropPieces();
                }

                if(gameDone) {
                    Game game = new Game();
                    this.game = game;


                    startTime = System.currentTimeMillis();
                    this.board = game.getBoard();
                    gameOverText.setText(" ");

                    clearRows();
                    playArea.getChildren().remove(nextTetrominoGraphic);
                    this.blockFXArray = new Rectangle[24][10];

                    currentTetromino = game.getCurrentTetromino();
                    nextTetromino = game.getNextTetromino();
                    ghostTetromino = game.getGhostTetromino();
                    generateTetromino();
                    dropPieces();
                    gameDone = false;
                }
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
                                        tryMove('s', board);
                                        currentTetrominoGraphic.setLayoutY(currentTetromino.getYReference() * 20 + 20);
                                        gameDone = board.isGameDone();
                                    } else {
                                        cancel();
                                    }
                                }
                            });
                        }
                }, 0, 300);
    }

    /**
     * Generates a new Tetromino
     * Loads a FXML file for a new Tetromino and for the next Tetromino then sets their position
     */
    public void generateTetromino() {

        //String array containing the resource URLs of the Tetromino FXMLs
        String[] tetrominoFXML_URL = new String[]{"Resources/ITetromino.fxml","Resources/OTetromino.fxml","Resources/TTetromino.fxml",
                                            "Resources/STetromino.fxml" ,"Resources/ZTetromino.fxml","Resources/JTetromino.fxml","Resources/LTetromino.fxml"};

        //Get the type of the new and next Tetrominos
        int newTetNum = currentTetromino.getType();
        int nextTetNum = nextTetromino.getType();


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
        currentTetrominoGraphic.setLayoutX(currentTetromino.getXReference() * 20 + 160);
        currentTetrominoGraphic.setLayoutY(currentTetromino.getYReference() * 20 + 20);
        ghostTetrominoGraphic.setLayoutX(ghostTetromino.getXReference() * 20 + 160);
        ghostTetrominoGraphic.setLayoutY(ghostTetromino.getYReference() * 20 + 20);
        ghostTetrominoGraphic.setOpacity(0.15);

        //Sets the next Tetromino to the display box location
        switch(nextTetNum) {
            case 0: nextTetrominoGraphic.setLayoutX(420); nextTetrominoGraphic.setLayoutY(120); break;
            case 1: nextTetrominoGraphic.setLayoutX(430); nextTetrominoGraphic.setLayoutY(140); break;
            case 2: nextTetrominoGraphic.setLayoutX(420); nextTetrominoGraphic.setLayoutY(140); break;
            case 3: nextTetrominoGraphic.setLayoutX(410); nextTetrominoGraphic.setLayoutY(130); break;
            case 4: nextTetrominoGraphic.setLayoutX(420); nextTetrominoGraphic.setLayoutY(140); break;
            case 5: nextTetrominoGraphic.setLayoutX(410); nextTetrominoGraphic.setLayoutY(130); break;
            case 6: nextTetrominoGraphic.setLayoutX(410); nextTetrominoGraphic.setLayoutY(130); break;
        }
    }

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
    }



    public int[] getScreenCoordinates(int row, int col) {
        int xCoord = col * 20 + 160;
        int yCoord = row * 20 + 20;
        int[] screenCoords = new int[2];
        screenCoords[0] = xCoord;
        screenCoords[1] = yCoord;
        return screenCoords;
    }

    public int[] getGridCoordinates(int row, int col) {
        int xCoord = (col - 160)/20;
        int yCoord = (row - 20)/20;
        int[] gridCoords = new int[2];
        gridCoords[0] = xCoord;
        gridCoords[1] = yCoord;
        return gridCoords;
    }

    public void updateBoardFX() {
        Block[][] currentBoard = board.getCurrentBoard();
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

    public void clearRows() {
        board.getRowsToClear();
        Boolean graphicsDone = false;
        int counter;


        //for(int flash = 0; flash < 10; flash++) {
        for (int row = 0; row < 24; row++) {
            //for (Integer row : board.getRowsToClear()) {
                for (int col = 0; col < 10; col++) {
                    //if (flash % 2 == 0) {
                        //blockFXArray[row][col].setFill(Color.web("FF0000"));
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        blockFXArray[row][col].setFill(Color.web("00FF00"));
//                        try {
//                            Thread.sleep(50);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        playArea.getChildren().remove(blockFXArray[row][col]);
                        blockFXArray[row][col] = null;
                    //}

                }
            }

        //}

        //Update score and lines cleared text
        scoreText.setText(Long.toString(game.getGameScore()));
        linesClearedText.setText(Long.toString(game.getLinesCleared()));

        //Update the block graphics on the board
        updateBoardFX();
        board.resetRowsToClear();
    }

    public void tryMove(char moveType, Board board) {
        boolean tetrominoPlaced = game.tryMove(moveType,board);

        this.currentTetromino = game.getCurrentTetromino();
        this.nextTetromino = game.getNextTetromino();
        this.ghostTetromino = game.getGhostTetromino();
        this.board = game.getBoard();

        gameDone = board.isGameDone();

        if (tetrominoPlaced) {
            removePlacedTetromino();
        }

        if (tetrominoPlaced) {
            if(!gameDone) {
                generateTetromino();
            }
            updateBoardFX();
            if (board.getRowsToClear().size() > 0) {
                clearRows();
            }
        }

        if(gameDone) {
            gameOverText.setText("Game Over");
        }

    }

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

//    public void dropTetromino() {
//
//    }
//
//    public long getGameScore(){
//        return this.gameScore;
//    }
//    public void updateGameScore(){
//        this.linesCleared += 100;
//    }
//    public int getLinesCleared(){
//        return this.gameScore;
//    }
//    public void updateLinesCleared(){
//        this.linesCleared += 1;
//    }
//
//    public Tetromino getCurrentTetromino() {
//        return this.currentTetromino;
//    }
//
//    public Tetromino getNextTetromino() {
//        return this.nextTetromino;
//    }
//
//    public void setCurrentTetromino(Tetromino currentTetromino) {
//        this.currentTetromino = currentTetromino;
//    }
//
//    public void setNextTetromino(Tetromino nextTetromino) {
//        this.currentTetromino = currentTetromino;
//    }

}
