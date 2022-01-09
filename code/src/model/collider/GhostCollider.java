package model.collider;

import model.entity.BaseEntity;

import java.util.List;

public class GhostCollider implements BaseCollider{
    @Override
    public boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        return false;
    }

}
