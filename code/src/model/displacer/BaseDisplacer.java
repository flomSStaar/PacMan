package model.displacer;

import model.entity.BaseEntity;
import model.utils.Direction;
import model.utils.DisplacerObserver;
import model.utils.LooperObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDisplacer implements LooperObserver {
    BaseEntity entity;
    protected Direction direction = Direction.NONE;
    protected List<DisplacerObserver> observers = new ArrayList<>();

    public void attach(DisplacerObserver observer) {
        observers.add(observer);
    }

    public void detach(DisplacerObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (DisplacerObserver observer : observers) {
            observer.onMove(direction);
        }
    }
}
