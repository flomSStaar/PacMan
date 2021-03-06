package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.utils.PlayerScore;

/**
 * Permet de convertir le score d'un joueur avec son pseudo en une propriete pour la vue.
 */
public class PlayerScoreConverter implements Comparable<PlayerScoreConverter> {
    private final PlayerScore playerScore;

    private StringProperty text = new SimpleStringProperty();
    public String getText() { return text.get(); }
    public void setText(String string) { text.set(string); }
    public StringProperty textProperty() { return text; }

    /**
     * Cree une instance de PlayerScoreConverter
     *
     * @param playerScore PlayerScore a convertir
     */
    public PlayerScoreConverter(PlayerScore playerScore) {
        this.playerScore = playerScore;
        setText(playerScore.getPseudo() + " - " + playerScore.getScore());
    }

    @Override
    public int compareTo(PlayerScoreConverter o) {
        return this.playerScore.compareTo(o.playerScore);
    }
}
