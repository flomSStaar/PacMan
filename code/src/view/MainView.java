package view;

import javafx.event.ActionEvent;
import launcher.Launcher;

public class MainView {
    public void initialize() {

    }

    public void launchGame(ActionEvent actionEvent) {
        Launcher.game.startGame();
    }

    public void close(ActionEvent actionEvent) {
        Launcher.game.close();
    }

    public void scores(ActionEvent actionEvent) {
        try {
            Launcher.game.score();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
