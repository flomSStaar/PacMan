package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import launcher.Launcher;
import model.utils.Resultat;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ScoreView {

    @FXML private Text textScore;
    @FXML private TextField name;
    @FXML private ListView list;
    private boolean isClick = false;

    public void initialize(){
        textScore.setText("Score : " + Launcher.game.score.getScore());
        list.setItems(Launcher.game.Resultats);
    }

    public void home(ActionEvent actionEvent) throws IOException {
        Launcher.game.home();
    }

    public void add(ActionEvent actionEvent) throws IOException {
        if(!isClick) {
            Launcher.game.Resultats.add(new Resultat(name.getText(), Launcher.game.score.getIntegerScore()));
            Collections.sort(Launcher.game.Resultats);
        }
        isClick = true;
    }
}
