package model.observers;

import model.entity.BaseEntity;
import model.utils.Direction;

public interface DisplacerObserver {
    /**
     * Notifie les observateurs lorsqu'une entité s'est déplacée
     *
     * @param entity    Entité déplacée
     * @param direction Direction dans laquelle l'entité s'est déplacée
     */
    void onMove(BaseEntity entity, Direction direction);
}
