package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.Direction;

import java.util.List;

public class PacManDisplacer extends BaseDisplacer {
    private Direction directionFuture = Direction.NONE;

    public PacManDisplacer(List<BaseEntity> entities, PacMan pacMan) {
        this.entities = entities;
        super.entity = pacMan;
    }

    public void move(Direction direction) {
        this.directionFuture = direction;
    }

    @Override
    public void onLoop() {
        if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + directionFuture.getDx(), super.entity.getY() + directionFuture.getDy()) && directionFuture != Direction.NONE) {
            if(!(entity.getX() <= 0 && (directionFuture == Direction.UP || directionFuture == Direction.DOWN))) {
                direction = directionFuture;
                directionFuture = Direction.NONE;
                notifyMove();
            }
        }
        if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + direction.getDx(), super.entity.getY() + direction.getDy())) {
            moveEntity(direction);
        }
    }
}
