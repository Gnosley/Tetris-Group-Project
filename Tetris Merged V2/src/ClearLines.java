
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ClearLines implements Runnable {

    private AnchorPane playArea;

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
    }

    /**
     * Executes the clear lines animation
     */
    public void run() {
        Platform.runLater(new Runnable() {
            public void run() {

                TetrisController tetController = new TetrisController();

                //Removes all blocks in the rows to be cleared
                for (int row : rowsToClear) {
                    for (int col = 0; col < 10; col++) {
                        if (blockFXArray[row][col] != null) {
                            playArea.getChildren().remove(blockFXArray[row][col]);
                            blockFXArray[row][col] = null;
                        }
                    }
                }

                //Fills each of the entire rows to be cleared with the proper blocks
                for (int row : rowsToClear) {
                    for (int col = 0; col < 10; col++) {
                        int[] screenCoords = tetController.getScreenCoordinates(row, col);
                        String[] tetrominoColor = new String[]{"1eeaff", "fffe21", "ff1ee6", "1eff5e", "ff1e1e", "231eff", "ff8c1e"};

                        Rectangle block = null;
                        try {
                            block = FXMLLoader.load(getClass().getResource("Resources/Block.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (blockFXArray[row][col] == null) {
                            playArea.getChildren().add(block);
                            blockFXArray[row][col] = block;
                        }

                        block.setLayoutX(screenCoords[0]);
                        block.setLayoutY(screenCoords[1]);
                        block.setFill(Color.web(tetrominoColor[0]));
                        block.toBack();
                    }
                }

                //Sets the clear color and glow of the blocks in the rows to be cleared
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

        //Briefly pauses the animation with the clear color and glow
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Creates the block shrinking animation
        for (int count = 0; count < 8; count++) {
            for(int row : rowsToClear) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        for (int col = 0; col < 10; col++) {
                            //Make block size slightly smaller and adjust position each loop
                            blockFXArray[row][col].setWidth(blockFXArray[row][col].getWidth() - 2);
                            blockFXArray[row][col].setHeight(blockFXArray[row][col].getHeight() - 2);
                            blockFXArray[row][col].setLayoutX(blockFXArray[row][col].getLayoutX() + 1);
                            blockFXArray[row][col].setLayoutY(blockFXArray[row][col].getLayoutY() + 1);
                        }

                    }
                });


            }
            //Briefly pauses the shrinking animation each loop
            try {
                TimeUnit.MILLISECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Clears all the blocks and re-updates the grid with the proper blocks. This update will have the rows dropped
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
                //Changes observable boolean value to resume the game after the clear
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
