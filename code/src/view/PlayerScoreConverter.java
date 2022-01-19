package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.utils.PlayerScore;

/**
 * Permet de convertir le score d'un joueur avec son pseudo en une propriété pour la vue.
 */
public class PlayerScoreConverter {
    private final PlayerScore playerScore;

    private StringProperty text = new SimpleStringProperty();
    public String getText() { return text.get(); }
    public void setText(String string) { text.set(string); }
    public StringProperty textProperty() { return text; }

    /**
     * Créé une instance de PlayerScoreConverter
     *
     * @param playerScore PlayerScore à convertir
     */
    public PlayerScoreConverter(PlayerScore playerScore) {
        this.playerScore = playerScore;
        setText(playerScore.getPseudo() + " - " + playerScore.getScore());
    }
}
