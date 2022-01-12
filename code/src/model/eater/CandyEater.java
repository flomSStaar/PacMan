package model.eater;

import model.collider.CandyCollider;
import model.entity.BaseEntity;
import model.utils.Direction;

import java.util.List;

public class CandyEater extends BaseEater {
    private CandyCollider candyCollider = new CandyCollider();

    public CandyEater(List<BaseEntity> entities) {
        this.entities = entities;
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        List<BaseEntity> collidingEntities = candyCollider.getColliding(entities, entity, entity.getX(), entity.getY());
        for (BaseEntity e : collidingEntities) {
            notifyEating(e);
        }
    }
}
