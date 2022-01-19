package model.eater;

import model.collider.BaseCollider;
import model.entity.BaseEntity;
import model.observer.DisplacerObserver;
import model.observer.EatObserver;
import model.utils.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Definit le comportement du mangeur
 */
public abstract class BaseEater implements DisplacerObserver {
    private final List<EatObserver> observers = new ArrayList<>();
    protected List<BaseEntity> entities;
    protected BaseCollider collider;
    protected boolean isActive = true;

    public BaseEater(List<BaseEntity> entities, BaseCollider collider) {
        this.entities = entities;
        this.collider = collider;
    }

    /**
     * Ajoute un observateur a la liste d'observateurs du mangeur
     *
     * @param observer Observateur a attacher
     */
    public void attach(EatObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Supprime un observateur a la liste d'observateurs du mangeur
     *
     * @param observer Observateur a detacher
     */
    public void detach(EatObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifie les observateurs qu'une entite peut etre mangee
     *
     * @param entity Entite pouvant etre mangee
     */
    protected void notifyEating(BaseEntity entity) {
        for (EatObserver observer : observers) {
            observer.onEat(entity);
        }
    }

    /**
     * Recupere l'etat du mangeur
     *
     * @return Etat du mangeur
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Modifie l'etat du mangeur
     *
     * @param isActive Future etat
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
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
