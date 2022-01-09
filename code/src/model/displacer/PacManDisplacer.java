package model.displacer;

import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.Direction;

import java.util.List;

public class PacManDisplacer extends BaseDisplacer {
    private List<BaseEntity> entities;
    private WallCollider wallCollider = new WallCollider();
    private Direction directionFuture = Direction.NONE;

    public PacManDisplacer(List<BaseEntity> entities, PacMan pacMan) {
        this.entities = entities;
        super.entity = pacMan;
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() - 1)) {
                    this.direction = Direction.UP;
                    this.directionFuture = Direction.NONE;
                    notifyObservers();
                } else
                    this.directionFuture = Direction.UP;
                break;
            case LEFT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() - 1, super.entity.getY())) {
                    this.direction = Direction.LEFT;
                    this.directionFuture = Direction.NONE;
                    notifyObservers();
                } else
                    this.directionFuture = Direction.LEFT;
                break;
            case DOWN:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() + 1)) {
                    this.direction = Direction.DOWN;
                    this.directionFuture = Direction.NONE;
                    notifyObservers();
                } else
                    this.directionFuture = Direction.DOWN;
                break;
            case RIGHT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + 1, super.entity.getY())) {
                    this.direction = Direction.RIGHT;
                    this.directionFuture = Direction.NONE;
                    notifyObservers();
                } else
                    this.directionFuture = Direction.RIGHT;
                break;
        }
    }

    @Override
    public void onLoop() {
        switch (directionFuture) {
            case UP:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() - 1)) {
                    this.direction = Direction.UP;
                    this.directionFuture = Direction.NONE;
                    moveUp();
                    notifyObservers();
                } else
                    dep();
                break;
            case LEFT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() - 1, super.entity.getY())) {
                    this.direction = Direction.LEFT;
                    this.directionFuture = Direction.NONE;
                    moveLeft();
                    notifyObservers();
                } else
                    dep();
                break;
            case DOWN:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() + 1)) {
                    this.direction = Direction.DOWN;
                    this.directionFuture = Direction.NONE;
                    moveDown();
                    notifyObservers();
                } else
                    dep();
                break;
            case RIGHT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + 1, super.entity.getY())) {
                    this.direction = Direction.RIGHT;
                    this.directionFuture = Direction.NONE;
                    moveRight();
                    notifyObservers();
                } else
                    dep();
                break;
            case NONE:
                dep();
                break;
        }
    }

    private void dep() {
        switch (direction) {
            case UP:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() - 1))
                    moveUp();
                break;
            case LEFT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() - 1, super.entity.getY()))
                    moveLeft();
                break;
            case DOWN:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() + 1))
                    moveDown();
                break;
            case RIGHT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + 1, super.entity.getY()))
                    moveRight();
                break;
        }
    }

    private void moveUp() {
        super.entity.setY(super.entity.getY() - 1);
    }

    private void moveDown() {
        super.entity.setY(super.entity.getY() + 1);
    }

    private void moveLeft() {
        super.entity.setX(super.entity.getX() - 1);
        if (super.entity.getX() == -13)
            super.entity.setX(420);
    }

    private void moveRight() {
        super.entity.setX(super.entity.getX() + 1);
        if (super.entity.getX() == 420)
            super.entity.setX(-13);
    }
}
