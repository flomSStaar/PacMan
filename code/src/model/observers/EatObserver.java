package model.observers;

import model.entity.BaseEntity;

public interface EatObserver {
    /**
     * Permet de notifier quand une entité a été mangée
     * @param entity Entité qui a été mangée
     */
    void onEat(BaseEntity entity);
}
