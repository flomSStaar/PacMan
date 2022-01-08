package model.displacer;

import model.entity.BaseEntity;
import model.utils.Direction;

public abstract class BaseDisplacer {
    private BaseEntity entity;

    abstract void move(Direction direction);
}
