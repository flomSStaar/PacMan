package model.displacer;

import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.observer.DisplacerObserver;
import model.observer.LooperObserver;
import model.utils.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Deplaceur de BaseEntity
 */
public abstract class BaseDisplacer implements LooperObserver {
    protected BaseEntity entity;
    protected Direction direction = Direction.NONE;
    protected WallCollider wallCollider = new WallCollider();
    protected List<DisplacerObserver> observers = new ArrayList<>();
    protected List<BaseEntity> entities;

    /**
     * Definit le constructeur du deplaceur abstrait
     *
     * @param entities Liste des entites
     * @param entity   Entite a deplacer
     */
    public BaseDisplacer(List<BaseEntity> entities, BaseEntity entity) {
        this.entities = entities;
        this.entity = entity;
    }

    /**
     * Ajoute un observateur a la liste d'observateurs du boucleur
     *
     * @param observer Observateur a attacher
     */
    public void attach(DisplacerObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Supprime un observateur a la liste d'observateurs du deplaceur
     *
     * @param observer Observateur a detacher
     */
    public void detach(DisplacerObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifie tous les objets de la liste d'observateurs du deplaceur
     */
    protected void notifyMove() {
        for (DisplacerObserver observer : observers) {
            observer.onMove(entity, direction);
        }
    }

    /**
     * Recupere la direction de l'entite
     *
     * @return Direction de l'entite
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Modifie la direction de l'entite dans le deplaceur
     *
     * @param direction Direction de l'entite
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Dplacer l'entit gre par le dplaceur
     */
    protected void moveEntity() {
        entity.setX(entity.getX() + direction.getDx());
        entity.setY(entity.getY() + direction.getDy());
        if (entity.getX() <= -14)
            entity.setX(421);
        else if (entity.getX() >= 421)
            entity.setX(-14);
        notifyMove();
    }
}
