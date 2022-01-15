package model;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.EatObserver;
import model.utils.GameOverObserver;

import java.util.ArrayList;
import java.util.List;

public class GameOver implements EatObserver {
    private List<GameOverObserver> observers = new ArrayList<>();

    public void attach(GameOverObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    public void detach(GameOverObserver observer) {
        observers.remove(observer);
    }

    private void notifyGameOver() {
        for (GameOverObserver observer : observers) {
            observer.onGameOver();
        }
    }

    @Override
    public void onEat(BaseEntity entity) {
        if (entity instanceof PacMan)
            notifyGameOver();
    }
}
