package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import launcher.Launcher;
import model.Score;
import model.io.saver.ScoreSaver;
import model.utils.PlayerScore;

import java.io.IOException;
import java.util.Collections;

public class ScoreView {
    @FXML
    public Text textScore;
    @FXML
    public TextField textField;
    @FXML
    public ListView list;
    private Score score;

    //    private boolean isClick = false;
    public void initialize() {
        score = Launcher.game.getScore();
        textScore.textProperty().bind(score.textScoreProperty());
//        textScore.setText("Score : " + Launcher.game.score.getScore());
//        list.setItems(Launcher.game.Resultats);
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
        new ScoreSaver().save(playerScore);
    }


//    public void add(ActionEvent actionEvent) throws IOException {
//        if(!isClick) {
//            Launcher.game.Resultats.add(new Resultat(name.getText(), Launcher.game.score.getIntegerScore()));
//            Collections.sort(Launcher.game.Resultats);
//        }
//        isClick = true;
//    }
}
