package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import launcher.Launcher;
import model.Score;
import model.utils.PlayerScore;

import java.io.IOException;

public class GameOverView {
    @FXML
    public Label scoreLabel;
    @FXML
    public TextField textField;
    @FXML
    private Score score;

    public void initialize() {
        score = Launcher.game.getScore();
        scoreLabel.textProperty().bind(score.textScoreProperty());
    }

    /**
     * Redirige vers la page principale
     *
     * @param actionEvent Action qui a realisee l'evenement
     */
    public void home(ActionEvent actionEvent) {
        try {
            saveScore();
            Launcher.game.home();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Relance une partie
     *
     * @param actionEvent Action qui a realisee l'evenement
     */
    public void restart(ActionEvent actionEvent) {
        saveScore();
        Launcher.game.startGame();
    }

    /**
     * Sauvegarde le score
     */
    private void saveScore() {
        String pseudo = textField.getText().isBlank() ? "anonymous" : textField.getText();
        PlayerScore playerScore = new PlayerScore(pseudo, score.getScore());
        Launcher.game.addPlayerScore(playerScore);
    }
}
