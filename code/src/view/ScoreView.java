package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import launcher.Launcher;
import model.utils.PlayerScore;

import java.io.IOException;

public class ScoreView {
    @FXML
    public ListView<PlayerScoreConverter> playerScoreListView;

    public void initialize() {
        ObservableList<PlayerScoreConverter> playerScoreConverters = FXCollections.observableArrayList();
        for (PlayerScore playerScore : Launcher.game.getPlayerScores()) {
            playerScoreConverters.add(new PlayerScoreConverter(playerScore));
        }

        playerScoreListView.setItems(playerScoreConverters);
        playerScoreListView.setCellFactory(__ -> new ListCell<>() {
            @Override
            protected void updateItem(PlayerScoreConverter item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    textProperty().bind(item.textProperty());
                } else {
                    textProperty().unbind();
                    setText("");
                }
            }
        });
    }

    /**
     * Redirige vers la page principale
     *
     * @param actionEvent Action qui a réalisée l'événement
     */
    public void home(ActionEvent actionEvent) {
        try {
            Launcher.game.home();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
