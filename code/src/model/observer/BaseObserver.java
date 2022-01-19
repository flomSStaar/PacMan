package model.observer;

import model.entity.BaseEntity;

public interface BaseObserver {
    /**
     * Permet de notifier quand un fantome est revenue a sa base quand il a etait mange
     *
     * @param entity Fantome qui a ete mangee
     */
    void onBase(BaseEntity entity);
}