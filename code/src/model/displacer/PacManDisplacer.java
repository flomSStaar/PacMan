package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.Direction;

import java.util.List;

/**
 * Définit le déplacement de PacMan
 */
public class PacManDisplacer extends BaseDisplacer {
    protected Direction directionFuture = Direction.NONE;

    /**
     * Créé une instance de PacManDisplacer
     *
     * @param entities Liste des entités
     * @param pacMan   PacMan à déplacer
     */
    public PacManDisplacer(List<BaseEntity> entities, PacMan pacMan) {
        this.entities = entities;
        super.entity = pacMan;
    }

    @Override
    public void setDirection(Direction direction) {
        this.directionFuture = direction;
    }

    @Override
    public void onLoop() {
        if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + directionFuture.getDx(), super.entity.getY() + directionFuture.getDy())
                && directionFuture != Direction.NONE) {
            direction = directionFuture;
            directionFuture = Direction.NONE;
            moveEntity();
        } else if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + direction.getDx(), super.entity.getY() + direction.getDy())) {
            moveEntity();
        }
    }
}
