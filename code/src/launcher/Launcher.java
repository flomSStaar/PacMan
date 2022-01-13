package launcher;

import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static Game game;

    public void start(Stage stage) {
        try {
            game = new Game(stage);
        } catch (Exception ioException) {
            ioException.printStackTrace();
            stage.close();
        }
    }
}
