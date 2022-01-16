package model.eater;

import model.collider.BaseCollider;
import model.entity.BaseEntity;
import model.utils.Direction;
import model.utils.DisplacerObserver;
import model.utils.EatObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEater implements DisplacerObserver {
    private List<EatObserver> observers = new ArrayList<>();
    protected List<BaseEntity> entities = new ArrayList<>();
    protected BaseCollider collider;
    private boolean isActive = true;

    public void attach(EatObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    public void detach(EatObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifie les observateurs qu'une entité peut être mangée
     *
     * @param entity Entité pouvant mangée
     */
    public void notifyEating(BaseEntity entity) {
        for (EatObserver observer : observers) {
            observer.onEat(entity);
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean bool) {
        isActive = bool;
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        if (isActive) {
            List<BaseEntity> collidingEntites = collider.getColliding(entities, entity, entity.getX(), entity.getY());
            for (BaseEntity e : collidingEntites) {
                notifyEating(e);
            }
        }
    }
}
