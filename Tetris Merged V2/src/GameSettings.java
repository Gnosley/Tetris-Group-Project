
import javafx.scene.input.KeyCode;

import static javafx.scene.input.KeyCode.*;

public class GameSettings {
    private int dropSpeed = 1;
    private String level = "EASY";
    private KeyCode[] currentControls;
    private final KeyCode[] standardControls = new KeyCode[]{LEFT,RIGHT,UP,X,DOWN,SPACE,C,ENTER,ESCAPE};
    private final KeyCode[] wasdControls = new KeyCode[]{A,D,E,Q,S,F,W,ENTER,ESCAPE};

    public GameSettings() {
        this.currentControls = standardControls;
    }

    public int getDropSpeedLevel() {
        return dropSpeed;
    }

    public int getDropSpeed() {
        return 2000/dropSpeed;
    }

    public void setDropSpeed(int dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public KeyCode[] getControls() {
        return currentControls;
    }

    public void setCurrentControls(String currentControls) {
        if(currentControls.equals("controlsButtonStandard")) {
            this.currentControls = standardControls;
        }
        if(currentControls.equals("controlsButtonWASD")) {
            this.currentControls = wasdControls;
        }
    }
}
