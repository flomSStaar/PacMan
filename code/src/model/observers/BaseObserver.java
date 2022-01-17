package model.observers;

import model.entity.BaseEntity;

public interface BaseObserver {
    /**
     * Permet de notifier quand un fantome est revenue a sa base quand il a était mangé
     * @param entity Fantome qui a été mangée
     */
    void onBase(BaseEntity entity);
}