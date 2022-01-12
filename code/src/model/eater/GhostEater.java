package model.eater;

import model.collider.GhostCollider;
import model.entity.BaseEntity;
import model.utils.Direction;

import java.util.List;

public class GhostEater extends BaseEater {
    private GhostCollider ghostCollider = new GhostCollider();

    public GhostEater(List<BaseEntity> entities) {
        this.entities = entities;
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        if (ghostCollider.isCollide(entities, entity, entity.getX(), entity.getY())) {
            notifyEating(entity);
        }
    }
}
