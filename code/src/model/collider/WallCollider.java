package model.collider;

import javafx.scene.shape.Rectangle;
import model.entity.BaseEntity;
import model.entity.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Définit si une entité est en collision avec un mur
 */
public class WallCollider implements BaseCollider {
    @Override
    public boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        Rectangle entityRectangle = new Rectangle(x, y, entity.getWidth(), entity.getHeight());

        for (BaseEntity e : entities) {
            if (e instanceof Wall) {
                Rectangle wallRectangle = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                if (entityRectangle.intersects(wallRectangle.getBoundsInParent())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<BaseEntity> getColliding(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        List<BaseEntity> l = new ArrayList<>();
        Rectangle entityRectangle = new Rectangle(x, y, entity.getWidth(), entity.getHeight());

        for (BaseEntity e : entities) {
            if (e instanceof Wall) {
                Rectangle wallRectangle = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                if (entityRectangle.intersects(wallRectangle.getBoundsInParent())) {
                    l.add(e);
                }
            }
        }
        return l;
    }
}
