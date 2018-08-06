import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controls all actions in the Menu Window
 */
public class MenuController {
    @FXML
    private Rectangle difficultyButton1;

    @FXML
    private Rectangle levelButton1;

    @FXML
    private Rectangle controlsButtonStandard;

    @FXML
    private Rectangle screenButtonSmall;

    @FXML
    private Rectangle newGame;

    private Rectangle currentDifficultyButton = difficultyButton1;
    private Rectangle currentLevelButton = levelButton1;
    private Rectangle currentControlsButton = controlsButtonStandard;
    private Rectangle currentScreenButton = screenButtonSmall;
    //private int currentDifficulty;
    private GameSettings gameSettings;
    private int dropSpeed;

    /**
     * Initializes values when Menu window opens
     */
    public void initialize() {
//        this.difficultyButton1.setFill(Color.web("ff481e"));
        currentDifficultyButton = difficultyButton1;
        currentLevelButton = levelButton1;
        currentControlsButton = controlsButtonStandard;
        currentScreenButton = screenButtonSmall;
        //currentDifficulty = 1;
        currentDifficultyButton.setFill(Color.web("ff481e"));
        currentLevelButton.setFill(Color.web("ff481e"));
        currentControlsButton.setFill(Color.web("ff481e"));
        currentScreenButton.setFill(Color.web("ff481e"));
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
            ((Rectangle) event.getTarget()).setFill(Color.web("72ff1e"));
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
    protected void mouseClickDifficulty(MouseEvent event) {
        currentDifficultyButton.setFill(Color.web("1e90ff"));
        ((Rectangle)event.getTarget()).setFill(Color.web("ff481e"));
        currentDifficultyButton = (Rectangle)event.getTarget();
        setDifficulty(((Rectangle) event.getTarget()).getId());
    }

    /**
     * Sets difficulty based on button selected
     * @param difficulty
     */
    private void setDifficulty(String difficulty) {
        switch (difficulty) {
            case "difficultyButton1": gameSettings.setDifficulty(1); break;
            case "difficultyButton2": gameSettings.setDifficulty(2); break;
            case "difficultyButton3": gameSettings.setDifficulty(3); break;
            case "difficultyButton4": gameSettings.setDifficulty(4); break;
            case "difficultyButton5": gameSettings.setDifficulty(5); break;
            case "difficultyButton6": gameSettings.setDifficulty(6); break;
            case "difficultyButton7": gameSettings.setDifficulty(7); break;
            case "difficultyButton8": gameSettings.setDifficulty(8); break;
            case "difficultyButton9": gameSettings.setDifficulty(9); break;
            case "difficultyButton10": gameSettings.setDifficulty(10); break;

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
            case "levelButton1": gameSettings.setLevel(1); break;
            case "levelButton2": gameSettings.setLevel(2); break;
            case "levelButton3": gameSettings.setLevel(3); break;
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
    }

    /**
     * Changes color of level button to red and calls setLevel function
     * @param event
     */
    @FXML
    protected void mouseClickScreenSize(MouseEvent event) {
        currentScreenButton.setFill(Color.web("1e90ff"));
        ((Rectangle)event.getTarget()).setFill(Color.web("ff481e"));
        currentScreenButton = (Rectangle)event.getTarget();
        //setLevel(((Rectangle) event.getTarget()).getId());
    }

    /**
     * Loads the Tetris window when "New Game" is clicked
     * @param event
     * @throws IOException
     */
    private void loadGame(MouseEvent event) throws IOException {
//        Stage stage = (Stage) newGame.getScene().getWindow();
        System.out.println(gameSettings.getDifficulty());
        System.out.println(gameSettings);



        Parent tetrisRoot = FXMLLoader.load(getClass().getResource("Resources/TetrisGame.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(tetrisRoot, 640 ,540));
        stage.setTitle("TETRIS");
        stage.setResizable(false);
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

        TetrisController tetrisController = new TetrisController();
        tetrisController.setGameSettings(gameSettings); // Passing the GameSettings-object to the TetrisController


    }
}
