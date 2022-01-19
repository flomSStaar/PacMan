package model.observer;

import model.entity.BaseEntity;

public interface EatObserver {
    /**
     * Permet de notifier quand une entite a ete mangee
     *
     * @param entity Entite qui a ete mangee
     */
    void onEat(BaseEntity entity);
}
