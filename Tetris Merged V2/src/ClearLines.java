import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ClearLines implements Runnable {

    private AnchorPane playArea;

    private int name;
    private Thread t;
    private ArrayList<Integer> rowsToClear = new ArrayList<>();
    private Rectangle[][] blockFXArray;
    private Block[][] currentBoard;
    private Block[][] oldBoard;

    ClearLines(Block[][] currentBoard, Block[][] oldBoard, ArrayList<Integer> rowsToClear, Rectangle[][] blockFXArray, AnchorPane playArea) {
        this.rowsToClear = new ArrayList<>(rowsToClear);
        this.blockFXArray = blockFXArray;
        this.playArea = playArea;
        this.currentBoard = currentBoard;

        System.out.println(rowsToClear);

    }

    public void run() {
        Platform.runLater(new Runnable() {
            public void run() {
        //        System.out.println("Running " +  threadName );
        //        try {
        //            for(int i = 4; i > 0; i--) {
        //                System.out.println("Thread: " + threadName + ", " + i);
        //                // Let the thread sleep for a while.
        //                Thread.sleep(2000);
        //            }
        //        } catch (InterruptedException e) {
        //            System.out.println("Thread " +  threadName + " interrupted.");
        //        }
        //        System.out.println("Thread " +  threadName + " exiting.");

                TetrisController tetController = new TetrisController();
                Boolean graphicsDone = false;
                int counter;

                for (int row : rowsToClear) {
                    for (int col = 0; col < 10; col++) {
                        if (blockFXArray[row][col] != null) {
                            playArea.getChildren().remove(blockFXArray[row][col]);
                            blockFXArray[row][col] = null;
                        }
                    }
                }

                for (int row : rowsToClear) {
                    for (int col = 0; col < 10; col++) {
                        int[] screenCoords = tetController.getScreenCoordinates(row,col);
                        String[] tetrominoColor = new String[]{"1eeaff","fffe21","ff1ee6","1eff5e" ,"ff1e1e","231eff","ff8c1e"};

                        Rectangle block = null;
                        try {
                            block = FXMLLoader.load(getClass().getResource("Resources/Block.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                        if (blockFXArray[row][col] == null){
                            playArea.getChildren().add(block);
                            blockFXArray[row][col] = block;
                        }

                        block.setLayoutX(screenCoords[0]);
                        block.setLayoutY(screenCoords[1]);
                        block.setFill(Color.web(tetrominoColor[0]));
                        block.toBack();
                    }
                }

                Glow glow = new Glow();
                glow.setLevel(1.0);
                for (int row : rowsToClear) {
                    for (int col = 0; col < 10; col++) {
                        if (blockFXArray[row][col] != null) {
                            blockFXArray[row][col].setFill(Color.web("fa8270"));
                            blockFXArray[row][col].setEffect(glow);
                        }
                    }
                }
            }
        });

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(new Runnable() {
            public void run() {


                for (int row = 0; row < 24; row++) {
                    for (int col = 0; col < 10; col++) {
                        playArea.getChildren().remove(blockFXArray[row][col]);
                        blockFXArray[row][col] = null;
                    }
                }

                TetrisController tetController = new TetrisController();

                //Update the block graphics on the board
                tetController.updateBoardFX(currentBoard, blockFXArray, playArea);
                TetrisController.setClearingRowsTrue();
            }
        });
    }

    public void start () {
        if (t == null) {
            t = new Thread (this);
            t.start ();
        }
    }
}
