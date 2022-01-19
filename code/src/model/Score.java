package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.entity.BaseEntity;
import model.entity.Candy;
import model.entity.SuperCandy;
import model.entity.ghost.Ghost;
import model.observer.EatObserver;
import model.utils.Config;

public class Score implements EatObserver {
    private int score = 0;

    private StringProperty textScore = new SimpleStringProperty("Score: " + score);
        public String getTextScore() { return textScore.get(); }
        public void setTextScore(String string) { textScore.set(string + getScore()); }
        public StringProperty textScoreProperty() { return textScore; }

    /**
     * Augmente le score du nombre de points en parametres
     *
     * @param points Nombre de points
     */
    public void increase(int points) {
        if (points < 0)
            return;
        score += points;
        setTextScore("Score: ");
    }

    /**
     * Remet a zero le score
     */
    public void reset() {
        score = 0;
        setTextScore("Score: ");
    }

    /**
     * Recupere le score actuel
     *
     * @return Score actuel
     */
    public int getScore() {
        return score;
    }

    @Override
    public void onEat(BaseEntity entity) {
        if (entity instanceof Candy) {
            increase(Config.CANDY_POINTS);
        } else if (entity instanceof SuperCandy) {
            increase(Config.SUPER_CANDY_POINTS);
        } else if (entity instanceof Ghost) {
            increase(Config.GHOST_POINTS);
        }
    }
}
