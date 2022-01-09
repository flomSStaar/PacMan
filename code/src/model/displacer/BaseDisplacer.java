package model.displacer;

import model.entity.BaseEntity;
import model.utils.Direction;
import model.utils.Observer;

public abstract class BaseDisplacer implements Observer {
    BaseEntity entity;

    @Override
    public void update(){}
}
