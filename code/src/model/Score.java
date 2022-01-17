package model;

import javafx.beans.property.SimpleStringProperty;
import model.entity.BaseEntity;
import model.entity.Candy;
import model.entity.SuperCandy;
import model.entity.ghost.Ghost;
import model.observers.BaseObserver;
import model.observers.EatObserver;
import model.utils.Config;

public class Score implements EatObserver {
    private int integerScore = 0;

    private SimpleStringProperty score = new SimpleStringProperty("Score: 0");
    public String getScore() {
        return score.get().substring(7);
    }
    public void setScore(String string) {
        score.set("Score: " + string);
    }
    public SimpleStringProperty scoreProperty() {
        return score;
    }

    /**
     * Augmente le score du nombre de points en paramètres
     *
     * @param points Nombre de points
     */
    public void increase(int points) {
        if (points < 0)
            return;
        integerScore += points;
        setScore(Integer.toString(integerScore));
    }

    /**
     * Remet à zero le score
     */
    public void reset() {
        integerScore = 0;
        setScore(Integer.toString(integerScore));
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
