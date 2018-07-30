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
    private Rectangle newGame;

    private Rectangle currentDifficultyButton = difficultyButton1;
    private int currentDifficultyInt;
    private int currentDifficulty;
    private int dropSpeed;

    /**
     * Initializes values when Menu window opens
     */
    public void initialize() {
        currentDifficultyButton = difficultyButton1;
        currentDifficulty = 1;
        currentDifficultyButton.setFill(Color.web("ff481e"));
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
    protected void mouseClickButton(MouseEvent event) {
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
            case "difficultyButton1": currentDifficulty = 1; break;
            case "difficultyButton2": currentDifficulty = 2; break;
            case "difficultyButton3": currentDifficulty = 3; break;
            case "difficultyButton4": currentDifficulty = 4; break;
            case "difficultyButton5": currentDifficulty = 5; break;
            case "difficultyButton6": currentDifficulty = 6; break;
            case "difficultyButton7": currentDifficulty = 7; break;
            case "difficultyButton8": currentDifficulty = 8; break;

        }
        dropSpeed = (10-currentDifficulty)*100;
    }

    /**
     * Loads the Tetris window when "New Game" is clicked
     * @param event
     * @throws IOException
     */
    private void loadGame(MouseEvent event) throws IOException {
//        Stage stage = (Stage) newGame.getScene().getWindow();
        Parent root2 = FXMLLoader.load(getClass().getResource("Resources/TetrisGame.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root2, 640 ,540));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }
}
