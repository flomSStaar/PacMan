package model.collider;

import javafx.scene.shape.Rectangle;
import model.entity.BaseEntity;
import model.entity.Wall;

import java.util.List;

public class WallCollider implements BaseCollider {
    @Override
    public boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        Rectangle entityRectangle = new Rectangle(x, y, entity.getWidth(), entity.getHeight());

        for (BaseEntity e : entities) {
            if (e instanceof Wall) {
                Rectangle wallRectangle = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                if (entityRectangle.intersects(wallRectangle.getBoundsInLocal())) {
                    return true;
                }
            }
        }
        return false;
    }
}
