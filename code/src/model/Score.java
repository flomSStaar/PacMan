package model;

import model.entity.BaseEntity;
import model.utils.EatObserver;

public class Score implements EatObserver {
    private int score;

    public void increase() {
        score += Config.CANDY_POINTS;
    }

    public void reset() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void onEat(BaseEntity entity) {
        increase();
        System.out.println(score);
    }
}
