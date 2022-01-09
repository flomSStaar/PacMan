package model.displacer;

import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.Direction;
import model.utils.DisplacerObserver;

import java.util.ArrayList;
import java.util.List;

public class PacManDisplacer extends BaseDisplacer {
    private List<BaseEntity> entities;
    private WallCollider wallCollider = new WallCollider();
    private Direction direction = Direction.NONE;
    private Direction directionFuture = Direction.NONE;

    private List<DisplacerObserver> observers = new ArrayList<>();

    public PacManDisplacer(List<BaseEntity> entities, PacMan pacMan) {
        this.entities = entities;
        super.entity = pacMan;
    }

    public void attach(DisplacerObserver observer) {
        observers.add(observer);
    }

    public void detach(DisplacerObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (DisplacerObserver observer : observers) {
            observer.onMove(direction);
        }
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
            case NONE:
                break;
        }
    }

    @Override
    public void onLoop() {
        switch (directionFuture) {
            case UP:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() - 1)) {
                    super.entity.setY(super.entity.getY() - 1);
                    notifyObservers();
                    this.direction = Direction.UP;
                    this.directionFuture = Direction.NONE;
                } else
                    dep();
                break;
            case LEFT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() - 1, super.entity.getY())) {
                    super.entity.setX(super.entity.getX() - 1);
                    notifyObservers();
                    this.direction = Direction.LEFT;
                    this.directionFuture = Direction.NONE;
                } else
                    dep();
                break;
            case DOWN:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX(), super.entity.getY() + 1)) {
                    super.entity.setY(super.entity.getY() + 1);
                    notifyObservers();
                    this.direction = Direction.DOWN;
                    this.directionFuture = Direction.NONE;
                } else
                    dep();
                break;
            case RIGHT:
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + 1, super.entity.getY())) {
                    super.entity.setX(super.entity.getX() + 1);
                    notifyObservers();
                    this.direction = Direction.RIGHT;
                    this.directionFuture = Direction.NONE;
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
