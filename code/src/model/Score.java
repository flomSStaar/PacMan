package model;

import javafx.beans.property.SimpleStringProperty;
import model.entity.BaseEntity;
import model.utils.EatObserver;

public class Score implements EatObserver {
    private int integerScore = 0;

    private SimpleStringProperty score = new SimpleStringProperty("Score: 0");
    public String getScore() { return score.get().substring(7); }
    public void setScore(String string) { score.set("Score: "+string); }
    public SimpleStringProperty scoreProperty() { return score; }

    public void increase() {
        integerScore += Config.CANDY_POINTS;
        setScore(Integer.toString(integerScore));
    }

    public void reset() {
        integerScore = 0;
        setScore(Integer.toString(integerScore));
    }

    @Override
    public void onEat(BaseEntity entity) {
        increase();
    }
}
