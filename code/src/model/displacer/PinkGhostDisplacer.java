package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;
import model.observer.DisplacerObserver;
import model.utils.Direction;

import java.util.List;

public class PinkGhostDisplacer extends GhostDisplacer implements DisplacerObserver {
    private Direction save = Direction.NONE;

    /**
     * Cree une instance de PinkGhostDisplacer
     *
     * @param entities Liste des entites
     * @param ghost    Fantome a deplacer
     * @param pacMan   PacMan
     */
    public PinkGhostDisplacer(List<BaseEntity> entities, Ghost ghost, PacMan pacMan) {
        super(entities, ghost, pacMan);
    }

    @Override
    public void onLoop() {
        if (isEatable || hasBeenEaten)
            super.onLoop();
        else {
            if (direction == Direction.NONE) {
                direction = save;
            }
            if (h % 15 == 0) {
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + directionFuture.getDx(), super.entity.getY() + directionFuture.getDy()) && directionFuture != Direction.NONE) {
                    if (!((entity.getX() <= 0 || entity.getX() >= 420) && (directionFuture == Direction.UP || directionFuture == Direction.DOWN))) {
                        direction = directionFuture;
                        directionFuture = Direction.NONE;
                        notifyMove();
                    }
                }
            }
            if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + direction.getDx(), super.entity.getY() + direction.getDy())) {
                moveEntity();
            } else {
                save = direction;
                direction = Direction.NONE;
                moveEntity();
            }
            h++;
        }
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        if (entity instanceof PacMan) {
            directionFuture = direction;
        }
    }
}
