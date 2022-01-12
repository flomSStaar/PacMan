package model.utils;

import model.entity.BaseEntity;

public interface EatObserver {
    /**
     * Permet de notifier quand une entité a été mangé
     * @param entity Entité qui a été mangé
     */
    void onEat(BaseEntity entity);
}
