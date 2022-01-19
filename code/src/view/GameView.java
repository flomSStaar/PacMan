package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import launcher.Launcher;
import model.Score;

public class GameView {
    @FXML
    Label scoreLabel;

    public void initialize() {
        Score score = Launcher.game.getScore();
        scoreLabel.textProperty().bind(score.textScoreProperty());
    }
}
