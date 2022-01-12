package model.eater;

import model.entity.BaseEntity;
import model.utils.DisplacerObserver;
import model.utils.EatObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEater implements DisplacerObserver {
    protected List<BaseEntity> entities = new ArrayList<>();
    private List<EatObserver> observers = new ArrayList<>();

    public void attach(EatObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    public void detach(EatObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifie les observateurs qu'une entité peut être mangée
     * @param entity Entité pouvant mangée
     */
    public void notifyEating(BaseEntity entity) {
        for (EatObserver observer : observers) {
            observer.onEat(entity);
        }
    }
}
