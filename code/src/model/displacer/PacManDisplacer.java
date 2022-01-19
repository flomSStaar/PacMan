package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.Direction;

import java.util.List;

/**
 * Definit le deplacement de PacMan
 */
public class PacManDisplacer extends BaseDisplacer {
    protected Direction directionFuture = Direction.NONE;

    /**
     * Cree une instance de PacManDisplacer
     *
     * @param entities Liste des entites
     * @param pacMan   PacMan a deplacer
     */
    public PacManDisplacer(List<BaseEntity> entities, PacMan pacMan) {
        super(entities, pacMan);
    }

    @Override
    public void setDirection(Direction direction) {
        this.directionFuture = direction;
    }

    @Override
    public void onLoop() {
        if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + directionFuture.getDx(), super.entity.getY() + directionFuture.getDy())
                && directionFuture != Direction.NONE) {
            if (!((entity.getX() <= 0 || entity.getX() >=420) && (directionFuture == Direction.UP || directionFuture == Direction.DOWN))) {
                direction = directionFuture;
                directionFuture = Direction.NONE;
                moveEntity();
            }
        }
        if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + direction.getDx(), super.entity.getY() + direction.getDy())) {
            moveEntity();
        }
    }
}
