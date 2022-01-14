package view;

import javafx.event.ActionEvent;
import launcher.Launcher;

import java.io.IOException;

public class MainView {
    public void initialize() {

    }

    public void launchGame(ActionEvent actionEvent) {
        Launcher.game.startGame();
    }

    public void parameters(ActionEvent actionEvent) {
        try {
            Launcher.game.parameters();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent actionEvent) {
        Launcher.game.close();
    }
}
