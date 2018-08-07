

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Loads initial settings to start the Tetris application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Resources/TetrisMenu.fxml"));
            Scene scene = new Scene(root, 580, 510);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tetris Main Menu");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Close application gracefully when user presses the close button on the window
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }


}
