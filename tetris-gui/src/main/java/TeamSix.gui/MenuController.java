package TeamSix.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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
    private Text controlsText1;

    @FXML
    private Text controlsText2;

    @FXML
    private TextField userTextField;

    private Rectangle currentDropSpeedButton = dropSpeedButton1;
    private Rectangle currentLevelButton = EASY;
    private Rectangle currentControlsButton = controlsButtonStandard;
    private GameSettings gameSettings;

    /**
     * Initializes values when Menu window opens
     */
    public void initialize() {
        gameSettings = new GameSettings();
        currentDropSpeedButton = dropSpeedButton1;
        currentLevelButton = EASY;
        currentControlsButton = controlsButtonStandard;

        //Limits text in username TextField to 7 characters
        userTextField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue ov, String old_val, String new_val) {
                if (new_val.length() > 7) {
                    userTextField.setText(old_val);
                }
            }
        });
    }

    /**
     * Calls the load game function when "New Game" button is clicked
     * @param event
     */
    @FXML
    protected void handleNewGameButtonAction(MouseEvent event) {
        try {
            loadGame(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes button color when mouse enters
     * @param event
     */
    @FXML
    protected void mouseOverButton(MouseEvent event) {
        if(!((Rectangle)event.getTarget()).getFill().equals(Color.web("ff481e"))) {
            ((Rectangle) event.getTarget()).setFill(Color.web("155982"));
        }
    }

    /**
     * Resets button color when mouse exits
     * @param event
     */
    @FXML
    protected void mouseExitButton(MouseEvent event) {
        if(!((Rectangle)event.getTarget()).getFill().equals(Color.web("ff481e"))) {
            ((Rectangle) event.getTarget()).setFill(Color.web("1e90ff"));
        }
    }

    /**
     * Changes color of drop speed button to red and calls setDropSpeed function to set the drop speed value
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
     * Sets drop speed based on button selected
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
     * Changes color of level button to red and calls setCurrentControls function
     * Displays text showing the current controls
     * @param event
     */
    @FXML
    protected void mouseClickControls(MouseEvent event) {
        currentControlsButton.setFill(Color.web("1e90ff"));
        ((Rectangle)event.getTarget()).setFill(Color.web("ff481e"));
        currentControlsButton = (Rectangle)event.getTarget();
        gameSettings.setCurrentControls(((Rectangle) event.getTarget()).getId());
        if(((Rectangle) event.getTarget()).getId().equals("controlsButtonStandard")) {
            controlsText1.setText("LEFT: Left\nRIGHT: Right\nUP: Rotate CW\nX: Rotate CCW");
            controlsText2.setText("DOWN: Soft Drop\nSPACE: Hard Drop\nC: Hold");
        }
        if(((Rectangle) event.getTarget()).getId().equals("controlsButtonWASD")) {
            controlsText1.setText("A: Left\nD: Right\nE: Rotate CW\nQ: Rotate CCW");
            controlsText2.setText("S: Soft Drop\nF: Hard Drop\nW: Hold");
        }
    }

    /**
     * Loads the Tetris window when "New Game" is clicked
     * @param event
     * @throws IOException
     */
    private void loadGame(MouseEvent event) throws IOException {

        //Sets the user name to the entered text
        String enteredUserName = userTextField.getText();
        String userName;
        if (enteredUserName.length() < 7) {
            userName = enteredUserName;
            //Adds spaces to end of username if it is less than 7 characters
            for (int letters = enteredUserName.length(); letters < 7; letters++) {
                userName += " ";
            }
        } else {
            userName = enteredUserName;
        }
        gameSettings.setUser(userName);

        //Passes GameSettings to the TetrisController
        TetrisController tetrisController = new TetrisController();
        tetrisController.setGameSettings(gameSettings);

        //Load the Tetris window from FXML
        Parent tetrisRoot = FXMLLoader.load(getClass().getResource("./TetrisGame.fxml"));
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

        //Closes previous window
        final Node source = (Node) event.getSource();
        final Stage previousStage = (Stage) source.getScene().getWindow();
        previousStage.close();




    }
}
