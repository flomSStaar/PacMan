package model.collider;

import javafx.scene.shape.Rectangle;
import model.entity.BaseEntity;
import model.entity.PacMan;

import java.util.ArrayList;
import java.util.List;

public class PacManCollider implements BaseCollider {
    @Override
    public boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        Rectangle entityRectangle = new Rectangle(x, y, entity.getWidth(), entity.getHeight());

        for (BaseEntity e : entities) {
            if (e instanceof PacMan) {
                Rectangle pacManRectangle = new Rectangle(e.getX() + e.getWidth() / 2, e.getY() + e.getHeight() / 2, 1, 1);
                if (entityRectangle.getBoundsInParent().contains(pacManRectangle.getBoundsInParent())) {
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
            if (e instanceof PacMan) {
                Rectangle pacManRectangle = new Rectangle(e.getX() + e.getWidth() / 2, e.getY() + e.getHeight() / 2, 1, 1);
                if (entityRectangle.getBoundsInParent().contains(pacManRectangle.getBoundsInParent())) {
                    l.add(e);
                }
            }
        }
        return l;
    }
}
