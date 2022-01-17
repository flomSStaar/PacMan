package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;
import model.observers.DisplacerObserver;
import model.utils.Direction;

import java.util.List;

public class PinkGhostDisplacer extends GhostDisplacer implements DisplacerObserver {
    /**
     * Créé une instance PinkGhostDisplacer
     *
     * @param entities Liste des entités
     * @param ghost    Fantôme à déplacer
     * @param pacMan   PacMan
     */
    public PinkGhostDisplacer(List<BaseEntity> entities, Ghost ghost, PacMan pacMan) {
        super(entities, ghost, pacMan);
    }

    @Override
    public void onLoop() {
        if (isEatable)
            super.onLoop();
        else {
            if (h % 15 == 0) {
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + directionFuture.getDx(), super.entity.getY() + directionFuture.getDy())
                        && directionFuture != Direction.NONE) {
                    if (!(entity.getX() <= 0 && (directionFuture == Direction.UP || directionFuture == Direction.DOWN))) {
                        direction = directionFuture;
                        directionFuture = Direction.NONE;
                        moveEntity();
                    }
                }
            }
            if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + direction.getDx(), super.entity.getY() + direction.getDy())) {
                moveEntity();
            }
            h++;
        }
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        if (entity instanceof PacMan) {
            setDirection(direction);
        }
    }
}
