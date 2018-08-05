

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Loads initial settings to start the Tetris application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Resources/TetrisMenu.fxml"));
            Scene scene = new Scene(root, 580, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tetris Main Menu");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}
