package model.eater;

import model.collider.BaseCollider;
import model.entity.BaseEntity;
import model.utils.Direction;
import model.observers.DisplacerObserver;
import model.observers.EatObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Définit le comportement du mangeur
 */
public abstract class BaseEater implements DisplacerObserver {
    private final List<EatObserver> observers = new ArrayList<>();
    protected List<BaseEntity> entities = new ArrayList<>();
    protected BaseCollider collider;
    private boolean isActive = true;

    /**
     * Ajoute un observateur à la liste d'observateurs du mangeur
     *
     * @param observer Observateur à attacher
     */
    public void attach(EatObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Supprime un observateur à la liste d'observateurs du mangeur
     *
     * @param observer Observateur à détacher
     */
    public void detach(EatObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifie les observateurs qu'une entité peut être mangée
     *
     * @param entity Entité pouvant mangée
     */
    protected void notifyEating(BaseEntity entity) {
        for (EatObserver observer : observers) {
            observer.onEat(entity);
        }
    }

    /**
     * Récupère l'état du mangeur
     *
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Modifie l'état du mangeur
     *
     * @param bool Future état
     */
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
