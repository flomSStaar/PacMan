package launcher;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Game;

/**
 * Lanceur de l'application
 */
public class Launcher extends Application {
    public static Game game;

    public void start(Stage stage) {
        try {
            game = new Game(stage);
            stage.setOnCloseRequest(event -> game.close());
        } catch (Exception ioException) {
            ioException.printStackTrace();
            stage.close();
        }
    }
}
