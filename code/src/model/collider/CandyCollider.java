package model.collider;

import javafx.scene.shape.Rectangle;
import model.entity.BaseEntity;
import model.entity.Candy;
import model.entity.SuperCandy;

import java.util.ArrayList;
import java.util.List;

/**
 * Definit si une entite est en collision avec un bonbon
 */
public class CandyCollider implements BaseCollider {
    @Override
    public boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        Rectangle entityRectangle = new Rectangle(x, y, entity.getWidth(), entity.getHeight());

        for (BaseEntity e : entities) {
            if (e instanceof Candy || e instanceof SuperCandy) {
                Rectangle eatRectangle = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                if (entityRectangle.getBoundsInParent().contains(eatRectangle.getBoundsInParent())) {
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
            if (e instanceof Candy || e instanceof SuperCandy) {
                Rectangle eatRectangle = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
                if (entityRectangle.getBoundsInParent().contains(eatRectangle.getBoundsInParent())) {
                    l.add(e);
                }
            }
        }
        return l;
    }
}