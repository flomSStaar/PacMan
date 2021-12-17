package model.collider;

import model.entity.BaseEntity;
import java.util.List;

public class WallCollider implements BaseCollider{
    @Override
    public boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        return false;
    }

    @Override
    public List<BaseEntity> getColliding(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        return null;
    }
}
