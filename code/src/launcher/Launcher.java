package launcher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static Game game;

    public void start(Stage stage) {
        try {
            game = new Game(stage);
            stage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        } catch (Exception ioException) {
            ioException.printStackTrace();
            stage.close();
        }
    }
}
