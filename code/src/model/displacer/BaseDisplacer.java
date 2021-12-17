package model.displacer;

import model.collider.BaseCollider;
import model.entity.BaseEntity;

public interface BaseDisplacer {
    void move(BaseEntity p, int x, int y, BaseCollider collider);
}
