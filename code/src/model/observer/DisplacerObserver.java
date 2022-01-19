package model.observer;

import model.entity.BaseEntity;
import model.utils.Direction;

public interface DisplacerObserver {
    /**
     * Notifie les observateurs lorsqu'une entite s'est deplacee
     *
     * @param entity    Entite deplacee
     * @param direction Direction dans laquelle l'entite s'est deplacee
     */
    void onMove(BaseEntity entity, Direction direction);
}
