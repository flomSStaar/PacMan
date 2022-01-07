package model.collider;

import model.entity.BaseEntity;
import model.entity.Wall;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

public class WallCollider implements BaseCollider{
    @Override
    public boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        Point2D.Float[] entityCoin = new Point2D.Float[4];
        entityCoin[0] = new Point2D.Float(x, y);
        entityCoin[1] = new Point2D.Float(x+entity.getWidth(), y);
        entityCoin[2] = new Point2D.Float(x, y+entity.getHeight());
        entityCoin[3] = new Point2D.Float(x+entity.getWidth(), y+entity.getHeight());
        for(BaseEntity e : entities)
        {
            if(e instanceof Wall)
            {
                if((entityCoin[0].getX() > e.getX() && entityCoin[0].getX() < e.getX()+e.getWidth() && entityCoin[0].getY() > e.getY() && entityCoin[0].getY() < e.getY()+e.getHeight()) || (entityCoin[1].getX() > e.getX() && entityCoin[1].getX() < e.getX()+e.getWidth() && entityCoin[1].getY() > e.getY() && entityCoin[1].getY() < e.getY()+e.getHeight()) || (entityCoin[2].getX() > e.getX() && entityCoin[2].getX() < e.getX()+e.getWidth() && entityCoin[2].getY() > e.getY() && entityCoin[2].getY() < e.getY()+e.getHeight()) || (entityCoin[3].getX() > e.getX() && entityCoin[3].getX() < e.getX()+e.getWidth() && entityCoin[3].getY() > e.getY() && entityCoin[3].getY() < e.getY()+e.getHeight()))
                {
                    return true;
                }
                Point2D.Float[] entityCoinWall = new Point2D.Float[4];
                entityCoinWall[0] = new Point2D.Float(x, y);
                entityCoinWall[1] = new Point2D.Float(x+entity.getWidth(), y);
                entityCoinWall[2] = new Point2D.Float(x, y+entity.getHeight());
                entityCoinWall[3] = new Point2D.Float(x+entity.getWidth(), y+entity.getHeight());
                if((entityCoinWall[0].getX() > entity.getX() && entityCoinWall[0].getX() < entity.getX()+entity.getWidth() && entityCoinWall[0].getY() > entity.getY() && entityCoinWall[0].getY() < entity.getY()+entity.getHeight()) || (entityCoinWall[1].getX() > entity.getX() && entityCoinWall[1].getX() < entity.getX()+e.getWidth() && entityCoinWall[1].getY() > entity.getY() && entityCoinWall[1].getY() < entity.getY()+entity.getHeight()) || (entityCoinWall[2].getX() > entity.getX() && entityCoinWall[2].getX() < entity.getX()+entity.getWidth() && entityCoinWall[2].getY() > entity.getY() && entityCoinWall[2].getY() < entity.getY()+entity.getHeight()) || (entityCoinWall[3].getX() > entity.getX() && entityCoinWall[3].getX() < entity.getX()+entity.getWidth() && entityCoinWall[3].getY() > entity.getY() && entityCoin[3].getY() < entity.getY()+entity.getHeight()))
                    return true;
            }
        }
        return false;
    }

    @Override
    public List<BaseEntity> getColliding(List<BaseEntity> entities, BaseEntity entity, float x, float y) {
        return null;
    }
}
