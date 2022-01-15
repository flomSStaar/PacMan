package model;

import javafx.beans.property.SimpleStringProperty;
import model.entity.BaseEntity;
import model.entity.Candy;
import model.entity.SuperCandy;
import model.entity.ghost.Ghost;
import model.utils.EatObserver;

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

    public void increase(int points) {
        integerScore += points;
        setScore(Integer.toString(integerScore));
    }

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
