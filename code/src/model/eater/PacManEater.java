package model.eater;

import model.collider.PacManCollider;
import model.entity.BaseEntity;
import model.utils.Direction;

import java.util.List;

/**
 * Classe permettant de savoir si PacMan peut être mangé
 * La classe notifie tous les observateurs si c'est le cas
 */
public class PacManEater extends BaseEater {

    private List<BaseEntity> eatenGhost;

    /**
     * Créé une instance de PacManEater
     *
     * @param entities Liste des entités
     */
    public PacManEater(List<BaseEntity> entities, List<BaseEntity> eatenGhost) {
        this.entities = entities;
        this.eatenGhost = eatenGhost;
        collider = new PacManCollider();
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        if (isActive && eatenGhost.contains(entity)) {
            List<BaseEntity> collidingEntites = collider.getColliding(entities, entity, entity.getX(), entity.getY());
            for (BaseEntity e : collidingEntites) {
                notifyEating(e);
            }
        }
    }
}
