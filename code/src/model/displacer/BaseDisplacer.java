package model.displacer;

import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.utils.Direction;
import model.utils.DisplacerObserver;
import model.utils.LooperObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDisplacer implements LooperObserver {
    protected BaseEntity entity;
    protected Direction direction = Direction.NONE;
    protected WallCollider wallCollider = new WallCollider();
    protected List<DisplacerObserver> observers = new ArrayList<>();
    protected List<BaseEntity> entities;

    public void attach(DisplacerObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    public void detach(DisplacerObserver observer) {
        observers.remove(observer);
    }

    public void notifyMove() {
        for (DisplacerObserver observer : observers) {
            observer.onMove(entity, direction);
        }
    }

    protected void moveEntity(Direction d, int speed) {
        entity.setX(entity.getX() + (d.getDx() * speed));
        entity.setY(entity.getY() + (d.getDy() * speed));
        if (entity.getX() <= -13)
            entity.setX(420);
        else if (entity.getX() >= 420)
            entity.setX(-13);
        direction = d;
        notifyMove();
    }
}
