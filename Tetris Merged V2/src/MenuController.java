import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Controls all actions in the Menu Window
 */
public class MenuController {
    @FXML
    private Rectangle dropSpeedButton1;

    @FXML
    private Rectangle EASY;

    @FXML
    private Rectangle controlsButtonStandard;

    @FXML
    private Rectangle newGame;

    @FXML
    private Text controlsText1;

    @FXML
    private Text controlsText2;

    private Rectangle currentDropSpeedButton = dropSpeedButton1;
    private Rectangle currentLevelButton = EASY;
    private Rectangle currentControlsButton = controlsButtonStandard;
    //private int currentDifficulty;
    private GameSettings gameSettings;
    private int dropSpeed;

    /**
     * Initializes values when Menu window opens
     */
    public void initialize() {
//        this.difficultyButton1.setFill(Color.web("ff481e"));
        currentDropSpeedButton = dropSpeedButton1;
        currentLevelButton = EASY;
        currentControlsButton = controlsButtonStandard;
        //currentDifficulty = 1;
        currentDropSpeedButton.setFill(Color.web("ff481e"));
        currentLevelButton.setFill(Color.web("ff481e"));
        currentControlsButton.setFill(Color.web("ff481e"));
        gameSettings = new GameSettings();
    }

    /**
     * Calls the load game function when "New Game" button is clicked
     * @param event
     */
    @FXML
    protected void handleNewGameButtonAction(MouseEvent event) {
        newGame.setFill(Color.web("ff481e"));
        try {
            loadGame(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes difficulty button color when mouse enters
     * @param event
     */
    @FXML
    protected void mouseOverButton(MouseEvent event) {
        if(!((Rectangle)event.getTarget()).getFill().equals(Color.web("ff481e"))) {
            ((Rectangle) event.getTarget()).setFill(Color.web("155982"));
        }
    }

    /**
     * Resets difficulty button color when mouse exits
     * @param event
     */
    @FXML
    protected void mouseExitButton(MouseEvent event) {
        if(!((Rectangle)event.getTarget()).getFill().equals(Color.web("ff481e"))) {
            ((Rectangle) event.getTarget()).setFill(Color.web("1e90ff"));
        }
    }

    /**
     * Changes color of difficulty button to red and calls setDifficulty function
     * @param event
     */
    @FXML
    protected void mouseClickDropSpeed(MouseEvent event) {
        currentDropSpeedButton.setFill(Color.web("1e90ff"));
        ((Rectangle)event.getTarget()).setFill(Color.web("ff481e"));
        currentDropSpeedButton = (Rectangle)event.getTarget();
        setDropSpeed(((Rectangle) event.getTarget()).getId());
    }

    /**
     * Sets difficulty based on button selected
     * @param difficulty
     */
    private void setDropSpeed(String difficulty) {
        switch (difficulty) {
            case "dropSpeedButton1": gameSettings.setDropSpeed(1); break;
            case "dropSpeedButton2": gameSettings.setDropSpeed(2); break;
            case "dropSpeedButton3": gameSettings.setDropSpeed(3); break;
            case "dropSpeedButton4": gameSettings.setDropSpeed(4); break;
            case "dropSpeedButton5": gameSettings.setDropSpeed(5); break;
            case "dropSpeedButton6": gameSettings.setDropSpeed(6); break;
            case "dropSpeedButton7": gameSettings.setDropSpeed(7); break;
            case "dropSpeedButton8": gameSettings.setDropSpeed(8); break;
            case "dropSpeedButton9": gameSettings.setDropSpeed(9); break;
            case "dropSpeedButton10": gameSettings.setDropSpeed(10); break;

        }
    }

    /**
     * Changes color of level button to red and calls setLevel function
     * @param event
     */
    @FXML
    protected void mouseClickLevel(MouseEvent event) {
        currentLevelButton.setFill(Color.web("1e90ff"));
        ((Rectangle)event.getTarget()).setFill(Color.web("ff481e"));
        currentLevelButton = (Rectangle)event.getTarget();
        setLevel(((Rectangle) event.getTarget()).getId());
    }

    /**
     * Sets level based on button selected
     * @param level
     */
    private void setLevel(String level) {
        switch (level) {
            case "EASY": gameSettings.setLevel("EASY"); break;
            case "MEDIUM": gameSettings.setLevel("MEDIUM"); break;
            case "HARD": gameSettings.setLevel("HARD"); break;
        }
    }

    /**
     * Changes color of level button to red and calls setLevel function
     * @param event
     */
    @FXML
    protected void mouseClickControls(MouseEvent event) {
        currentControlsButton.setFill(Color.web("1e90ff"));
        ((Rectangle)event.getTarget()).setFill(Color.web("ff481e"));
        currentControlsButton = (Rectangle)event.getTarget();
        gameSettings.setCurrentControls(((Rectangle) event.getTarget()).getId());
        if(((Rectangle) event.getTarget()).getId().equals("controlsButtonStandard")) {
            controlsText1.setText("LEFT: Left RIGHT: Right UP: Rotate CW X: Rotate CCW");
            controlsText2.setText("DOWN: Soft Drop SPACE: Hard Drop C: Hold");
            controlsText1.setWrappingWidth(100);
            controlsText2.setWrappingWidth(117);
        }
        if(((Rectangle) event.getTarget()).getId().equals("controlsButtonWASD")) {
            controlsText1.setText("A: Left\nD: Right\nE: Rotate CW\nQ: Rotate CCW");
            controlsText2.setText("S: Soft Drop\nF: Hard Drop\nW: Hold");
            controlsText1.setWrappingWidth(95);
            controlsText2.setWrappingWidth(85);
        }
    }

    /**
     * Loads the Tetris window when "New Game" is clicked
     * @param event
     * @throws IOException
     */
    private void loadGame(MouseEvent event) throws IOException {



        Parent tetrisRoot = FXMLLoader.load(getClass().getResource("Resources/TetrisGame.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(tetrisRoot, 700 ,540));
        stage.setTitle("TETRIS");
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

        final Node source = (Node) event.getSource();
        final Stage previousStage = (Stage) source.getScene().getWindow();
        previousStage.close();

        TetrisController tetrisController = new TetrisController();
        tetrisController.setGameSettings(gameSettings); // Passing the GameSettings-object to the TetrisController


    }
}
