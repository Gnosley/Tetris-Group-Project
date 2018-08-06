
import javafx.scene.input.KeyCode;

import static javafx.scene.input.KeyCode.*;

public class GameSettings {
    private int difficulty = 1;
    private int level = 1;
    private KeyCode[] currentControls;
    private final KeyCode[] standardControls = new KeyCode[]{LEFT,RIGHT,UP,X,DOWN,SPACE,C,ENTER,ESCAPE};
    private final KeyCode[] wasdControls = new KeyCode[]{A,D,E,Q,S,F,W,ENTER,ESCAPE};

    public GameSettings() {
        this.currentControls = standardControls;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDropSpeed() {
        return 2000/difficulty;
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
