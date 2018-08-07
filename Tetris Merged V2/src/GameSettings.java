
import javafx.scene.input.KeyCode;

import static javafx.scene.input.KeyCode.*;

public class GameSettings {
    private int dropSpeed = 1;
    private String level = "EASY";
    private String user = "--";
    private KeyCode[] currentControls;
    private final KeyCode[] standardControls = new KeyCode[]{LEFT,RIGHT,UP,X,DOWN,SPACE,C,ENTER,ESCAPE};
    private final KeyCode[] wasdControls = new KeyCode[]{A,D,E,Q,S,F,W,ENTER,ESCAPE};

    /**
     * Default constructor for GameSettings. Initializes controls and other values
     */
    public GameSettings() {
        this.currentControls = standardControls;
    }

    /**
     *
     * @return The drop speed level
     */
    public int getDropSpeedLevel() {
        return dropSpeed;
    }

    /**
     * Getter method for the drop speed
     * Controls how fast the Tetrominos drops
     * @return The drop speed value
     */
    public int getDropSpeed() {
        return 2000/dropSpeed;
    }

    /**
     * Setter method for the drop speed
     * Controls how fast the Tetrominos drops
     * @param dropSpeed
     */
    public void setDropSpeed(int dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    /**
     * Getter method for the distribution level
     * Controls the distribution of which Tetrominos drop
     * @return The distribution level
     */
    public String getLevel() {
        return level;
    }

    /**
     * Setter method for the distribution level
     * Controls the distribution of which Tetrominos drop
     * @param level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * Getter method for the user name
     * @return User name
     */
    public String getUser() {
        return user;
    }

    /**
     * Setter method for the user name
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Getter method for the selected controls
     * @return User name
     */
    public KeyCode[] getControls() {
        return currentControls;
    }

    /**
     * Setter method for the selected controls
     * @param currentControls
     */
    public void setCurrentControls(String currentControls) {
        if(currentControls.equals("controlsButtonStandard")) {
            this.currentControls = standardControls;
        }
        if(currentControls.equals("controlsButtonWASD")) {
            this.currentControls = wasdControls;
        }
    }
}
