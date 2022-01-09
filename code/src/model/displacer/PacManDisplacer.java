package model.displacer;

import model.animator.PacManAnimator;
import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.Direction;
import model.utils.Observer;

import java.util.List;

public class PacManDisplacer extends BaseDisplacer{
    private List<BaseEntity> entities;
    private WallCollider wallCollider = new WallCollider();
    private Direction direction = Direction.NONE;
    private Direction directionFuture = Direction.NONE;
    private PacManAnimator pacManAnimator;

    public PacManDisplacer(List<BaseEntity> entities, PacMan pacMan, PacManAnimator pacManAnimator) {
        this.entities = entities;
        super.entity = pacMan;
        this.pacManAnimator = pacManAnimator;
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() - 1)) {
                    this.direction = Direction.UP;
                    this.directionFuture = Direction.NONE;
                    pacManAnimator.SetDirection(Direction.UP);
                }
                else
                    this.directionFuture = Direction.UP;
                break;
            case LEFT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() - 1, super.entity.getY())) {
                    this.direction = Direction.LEFT;
                    this.directionFuture = Direction.NONE;
                    pacManAnimator.SetDirection(Direction.LEFT);
                }
                else
                    this.directionFuture = Direction.LEFT;
                break;
            case DOWN:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() + 1)) {
                    this.direction = Direction.DOWN;
                    this.directionFuture = Direction.NONE;
                    pacManAnimator.SetDirection(Direction.DOWN);
                }
                else
                    this.directionFuture = Direction.DOWN;
                break;
            case RIGHT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + 1, super.entity.getY())) {
                    this.direction = Direction.RIGHT;
                    this.directionFuture = Direction.NONE;
                    pacManAnimator.SetDirection(Direction.RIGHT);
                }
                else
                    this.directionFuture = Direction.RIGHT;
                break;
            case NONE:
                break;
        }
    }

    @Override
    public void update() {
        switch (directionFuture) {
            case UP:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() - 1)) {
                    super.entity.setY(super.entity.getY() - 1);
                    pacManAnimator.SetDirection(Direction.UP);
                    this.direction = Direction.UP;
                    this.directionFuture = Direction.NONE;
                }
                else
                    dep();
                break;
            case LEFT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() - 1, super.entity.getY())) {
                    super.entity.setX(super.entity.getX() - 1);
                    pacManAnimator.SetDirection(Direction.LEFT);
                    this.direction = Direction.LEFT;
                    this.directionFuture = Direction.NONE;
                }
                else
                    dep();
                break;
            case DOWN:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() + 1)) {
                    super.entity.setY(super.entity.getY() + 1);
                    pacManAnimator.SetDirection(Direction.DOWN);
                    this.direction = Direction.DOWN;
                    this.directionFuture = Direction.NONE;
                }
                else
                    dep();
                break;
            case RIGHT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + 1, super.entity.getY())) {
                    super.entity.setX(super.entity.getX() + 1);
                    pacManAnimator.SetDirection(Direction.RIGHT);
                    this.direction = Direction.RIGHT;
                    this.directionFuture = Direction.NONE;
                }
                else
                    dep();
                break;
            case NONE:
                dep();
                break;
        }
    }

    private void dep()
    {
        switch (direction) {
            case UP:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() - 1))
                    super.entity.setY(super.entity.getY() - 1);
                break;
            case LEFT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() - 1, super.entity.getY())) {
                    super.entity.setX(super.entity.getX() - 1);
                    if (super.entity.getX() == -13)
                        super.entity.setX(420);
                }
                break;
            case DOWN:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() + 1))
                    super.entity.setY(super.entity.getY() + 1);
                break;
            case RIGHT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + 1, super.entity.getY())) {
                    super.entity.setX(super.entity.getX() + 1);
                    if (super.entity.getX() == 420)
                        super.entity.setX(-13);
                }
                break;
        }
    }
}
