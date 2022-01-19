package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import launcher.Launcher;
import model.Score;
import model.utils.PlayerScore;

import java.io.IOException;

public class GameOverView {
    @FXML
    public Text textScore;
    @FXML
    public TextField textField;
    @FXML
    private Score score;

    public void initialize() {
        score = Launcher.game.getScore();
        textScore.textProperty().bind(score.textScoreProperty());
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
     * Relance une partie
     *
     * @param actionEvent Action qui a réalisée l'événement
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
//        new ScoreSaver().save(playerScore);
    }
}
