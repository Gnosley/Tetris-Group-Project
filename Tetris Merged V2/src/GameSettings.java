
import javafx.scene.input.KeyCode;

import static javafx.scene.input.KeyCode.*;

public class GameSettings {
    private int difficulty = 1;
    private int level = 1;
    private KeyCode[] currentControls = new KeyCode[]{};
    private KeyCode[] standardControls = new KeyCode[]{LEFT,RIGHT,DOWN,SPACE,UP,ENTER,ESCAPE};

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
}
