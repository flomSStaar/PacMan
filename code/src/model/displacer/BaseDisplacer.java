package model.displacer;

import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.observers.DisplacerObserver;
import model.observers.LooperObserver;
import model.utils.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * Déplaceur de BaseEntity
 */
public abstract class BaseDisplacer implements LooperObserver {
    protected BaseEntity entity;
    protected Direction direction = Direction.NONE;
    protected WallCollider wallCollider = new WallCollider();
    protected List<DisplacerObserver> observers = new ArrayList<>();
    protected List<BaseEntity> entities;

    /**
     * Définit le constructeur du déplaceur abstrait
     *
     * @param entities Liste des entités
     * @param entity   Entité à déplacer
     */
    public BaseDisplacer(List<BaseEntity> entities, BaseEntity entity) {
        this.entities = entities;
        this.entity = entity;
    }

    /**
     * Ajoute un observateur à la liste d'observateurs du boucleur
     *
     * @param observer Observateur à attacher
     */
    public void attach(DisplacerObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Supprime un observateur à la liste d'observateurs du déplaceur
     *
     * @param observer Observateur à détacher
     */
    public void detach(DisplacerObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifie tous les objets de la liste d'observateurs du déplaceur
     */
    protected void notifyMove() {
        for (DisplacerObserver observer : observers) {
            observer.onMove(entity, direction);
        }
    }

    /**
     * Récupère la direction de l'entité
     *
     * @return Direction de l'entité
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Modifie la direction de l'entité dans le déplaceur
     *
     * @param direction Direction de l'entité
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Déplacer l'entité gérée par le déplaceur
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
