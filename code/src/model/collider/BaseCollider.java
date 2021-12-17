package model.collider;

import model.entity.BaseEntity;
import java.util.List;

public interface BaseCollider{
    boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y);
    List<BaseEntity> getColliding(List<BaseEntity> entities, BaseEntity entity, float x, float y);
}
