package model.displacer;

import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.entity.Wall;
import model.utils.Direction;
import model.utils.DisplacerObserver;
import model.utils.LooperObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDisplacer implements LooperObserver {
    BaseEntity entity;
    protected Direction direction = Direction.NONE;
    protected List<DisplacerObserver> observers = new ArrayList<>();
    protected WallCollider wallCollider = new WallCollider();
    protected List<BaseEntity> entities;

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

    void bouge(Direction d, int speed)
    {
        entity.setX(entity.getX() + (d.getDx()*speed));
        entity.setY(entity.getY() + (d.getDy()*speed));
        if (entity.getX() <= -13)
            entity.setX(420);
        else if (entity.getX() >= 420)
            entity.setX(-13);
        direction = d;
        notifyObservers();
    }
}
