

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TetrisController {
    @FXML
    //private Rectangle newGame;
    private Rectangle tetrisBlock;

    @FXML
    private AnchorPane playArea;

    @FXML
    private ImageView image;

    private StackPane currentTetrominoGraphic;
    private StackPane nextTetrominoGraphic;

    private double currentRotate;

    public TetrisController controlTest;

    private long startTime = 0;

    private boolean pieceFalling = true;
    private Board board;
    private Game game;

    private Tetromino currentTetromino; // the tetromino currently in play
    private Tetromino nextTetromino; // the tetromino to be played next
    private static final int startingX = 3;
    private static final int startingY = 0;
    private int gameScore = 0;
    private int linesCleared = 0;
    private boolean gameDone = false;

    @FXML
    protected void handleNewGameButtonAction(MouseEvent event) {
        //newGame.setFill(Color.web("11EFDD"));
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
        if(!gameDone) {
            switch (event.getCode()) {
                case LEFT:
                    tryMove('a', board);
                    currentTetrominoGraphic.setLayoutX(currentTetromino.getXReference() * 20 + 160);

                    break;
                case RIGHT:
                    tryMove('d', board);
                    currentTetrominoGraphic.setLayoutX(currentTetromino.getXReference() * 20 + 160);
                    break;
                case DOWN:
                    tryMove('s', board);
                    currentTetrominoGraphic.setLayoutY(currentTetromino.getYReference() * 20 + 20);
                    gameDone = board.isGameDone();
                    break;
                case UP:
                    tryMove('e', board);
                    System.out.println(currentTetromino.getRotation());
                    currentTetrominoGraphic.setRotate(currentTetromino.getRotation());
                    break;
                case ENTER:
                    startTime = System.currentTimeMillis();
                    Game game = new Game();
                    this.board = game.getBoard();
                    currentTetromino = game.getCurrentTetromino();
                    nextTetromino = game.getNextTetromino();
                    generateTetromino();
                    dropPieces();
                    break;
    //            case R:replaceI();
    //                break;
    //            case Q:
    //                Image img = new Image("Resources/onepunch.jpg");
    //                ImagePattern imgP = new ImagePattern(img);
    //                image.setImage(img);
    //                break;
            }
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

                                    }
                                }
                            });
                        }
                }, 0, 200);
    }

    public void generateTetromino() {
//        System.out.println(game.getCurrentTetromino().getType());
        String[] tetrominoFXML_URL = new String[]{"Resources/ITetromino.fxml","Resources/OTetromino.fxml","Resources/TTetromino.fxml",
                                            "Resources/STetromino.fxml" ,"Resources/ZTetromino.fxml","Resources/JTetromino.fxml","Resources/LTetromino.fxml"};

        playArea.getChildren().remove(nextTetrominoGraphic);

        int newTetNum = currentTetromino.getType();
        int nextTetNum = nextTetromino.getType();

        try {
            StackPane newTetromino = FXMLLoader.load(TetrisController.class.getResource(tetrominoFXML_URL[newTetNum]));
            playArea.getChildren().add(newTetromino);
            currentTetrominoGraphic = newTetromino;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            StackPane nextTetromino = FXMLLoader.load(TetrisController.class.getResource(tetrominoFXML_URL[nextTetNum]));
            playArea.getChildren().add(nextTetromino);
            nextTetrominoGraphic = nextTetromino;
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentTetrominoGraphic.setLayoutX(currentTetromino.getXReference()*20+160);
        currentTetrominoGraphic.setLayoutY(currentTetromino.getYReference()*20+20);

        nextTetrominoGraphic.setLayoutX(420);
        nextTetrominoGraphic.setLayoutY(120);
    }

//    private void replaceI() throws IOException {
//        Rectangle block = FXMLLoader.load(getClass().getResource("Block.fxml"));
//        Rectangle block2 = FXMLLoader.load(getClass().getResource("Block.fxml"));
//        Rectangle block3 = FXMLLoader.load(getClass().getResource("Block.fxml"));
//        Rectangle block4 = FXMLLoader.load(getClass().getResource("Block.fxml"));
//        playArea.getChildren().add(block);
//        playArea.getChildren().add(block2);
//        playArea.getChildren().add(block3);
//        playArea.getChildren().add(block4);
//        block.setLayoutX(iPiece.getLayoutX());
//        block.setLayoutY(iPiece.getLayoutY());
//        block2.setLayoutX(iPiece.getLayoutX());
//        block2.setLayoutY(iPiece.getLayoutY()+20);
//        block3.setLayoutX(iPiece.getLayoutX());
//        block3.setLayoutY(iPiece.getLayoutY()+40);
//        block4.setLayoutX(iPiece.getLayoutX());
//        block4.setLayoutY(iPiece.getLayoutY()+60);
////        block.setLayoutX(100);
////        block.setLayoutY(0);
//        block.setFill(Color.web("000000"));
//        playArea.getChildren().remove(iPiece);
//    }

    public void tryMove(char moveType, Board board) {
        // Possible chars are q, e, a, s, d.
        // q rotates counter-clockwise.
        // e rotates clockwise.
        // a moves the block left.
        // s moves the block down.
        // d moves the block right.
        switch(moveType) {
            case 'q': case 'e': case 'a': case 's': case 'd': break;
            default: return;
        }

        // Tetromino.doMove() should return a NEW tetromino with the move applied
        Tetromino movedTetromino = currentTetromino.doMove(moveType);

        // board.checkBoard() returns true if no blocks are currently in the way and
        // no blocks in the given tetromino are out of board bounds.
        boolean canMove = board.checkMove(movedTetromino);
        if (canMove) {
            currentTetromino = movedTetromino;
        }
        else if (moveType == 's' && !canMove) {
            board.updateBoard(currentTetromino); // if moving down causes it to hit a
            // block or go out of bounds, add
            // the current blocks in the
            // tetromino to the board.

            currentTetromino = nextTetromino;
            nextTetromino = new Tetromino(startingX, startingY); // initialize a new random Tetromino
            generateTetromino();
        }
    }
    public long getGameScore(){
        return this.gameScore;
    }
    public void updateGameScore(){
        this.linesCleared += 100;
    }
    public int getLinesCleared(){
        return this.gameScore;
    }
    public void updateLinesCleared(){
        this.linesCleared += 1;
    }

    public Tetromino getCurrentTetromino() {
        return this.currentTetromino;
    }

    public Tetromino getNextTetromino() {
        return this.nextTetromino;
    }

}
