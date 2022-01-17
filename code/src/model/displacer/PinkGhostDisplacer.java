package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;
import model.utils.Direction;
import model.utils.DisplacerObserver;

import java.util.List;

public class PinkGhostDisplacer extends GhostDisplacer implements DisplacerObserver {
    public PinkGhostDisplacer(Ghost ghost, PacMan pacMan, List<BaseEntity> entities) {
        super(ghost, pacMan, entities);
    }

    @Override
    public void onLoop() {
        if(isEatable)
            super.onLoop();
        else {
            if (h % 15 == 0) {
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + directionFuture.getDx(), super.entity.getY() + directionFuture.getDy()) && directionFuture != Direction.NONE) {
                    if (!(entity.getX() <= 0 && (directionFuture == Direction.UP || directionFuture == Direction.DOWN))) {
                        direction = directionFuture;
                        directionFuture = Direction.NONE;
                        notifyMove();
                    }
                }
            }
            if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + direction.getDx(), super.entity.getY() + direction.getDy())) {
                moveEntity(direction);
            }
            h++;
        }
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        if(entity instanceof PacMan){
            move(direction);
        }
    }
}
