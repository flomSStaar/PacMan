package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import launcher.Launcher;
import model.Score;
import model.io.saver.ScoreSaver;
import model.utils.PlayerScore;

import java.io.IOException;

public class ScoreView {
    @FXML
    public Text textScore;
    public TextField textField;
    private Score score;

    public void initialize() {
        score = Launcher.game.getScore();
        textScore.textProperty().bind(score.textScoreProperty());
    }

    /**
     * Relance une partie
     *
     * @param actionEvent Action qui a réalisée l'événement
     */
    public void restart(ActionEvent actionEvent) {
        saveScore();
        Launcher.game.startGame();
    }

    /**
     * Redirige vers la page principale
     *
     * @param actionEvent Action qui a réalisée l'événement
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
     * Sauvegarde le score
     */
    private void saveScore() {
        String pseudo = textField.getText().isBlank() ? "anonymous" : textField.getText();
        PlayerScore playerScore = new PlayerScore(pseudo, score.getScore());
        new ScoreSaver().save(playerScore);
    }
}
