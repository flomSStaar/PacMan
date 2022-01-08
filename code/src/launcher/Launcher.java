package launcher;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    public static Game game;

    public void start(Stage stage) throws Exception {
        try{
            game = new Game(stage);
        }catch (IOException ioException){
            ioException.printStackTrace();
            stage.close();
        }catch (Exception e){
            e.printStackTrace();
            stage.close();
        }
    }
}
