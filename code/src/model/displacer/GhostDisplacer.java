package model.displacer;

import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.entity.Ghost;
import model.entity.PacMan;
import model.entity.Wall;
import model.utils.Direction;

import java.util.List;
import java.util.Random;

public class GhostDisplacer extends BaseDisplacer {
    private PacMan pacMan;
    private WallCollider wallCollider = new WallCollider();
    private List<BaseEntity> entities;

    public GhostDisplacer(Ghost g, PacMan p, List<BaseEntity> l) {
        super.entity = g;
        pacMan = p;
        entities = l;
        int[][] cell = new int[31][28];
        //Arrays.fill(cell, 0);
        for (BaseEntity e : l) {
            if (e instanceof Wall) {
                cell[((int) e.getY() - (int) e.getY() % 15) / 15][((int) e.getX() - (int) e.getX() % 15) / 15] = 1;
            }
        }
    }

    @Override
    public void onLoop() {
        if (pacMan.getY() < entity.getY() && !wallCollider.isCollide(entities, entity, entity.getX(), entity.getY() - 1)) {
            entity.setY(entity.getY() - 1);
            direction = Direction.UP;
            notifyObservers();
        } else if (pacMan.getY() > entity.getY() && !wallCollider.isCollide(entities, entity, entity.getX(), entity.getY() + 1)) {
            entity.setY(entity.getY() + 1);
            direction = Direction.DOWN;
            notifyObservers();
        } else if (pacMan.getX() < entity.getY() && !wallCollider.isCollide(entities, entity, entity.getX() - 1, entity.getY())) {
            entity.setX(entity.getX() - 1);
            direction = Direction.LEFT;
            notifyObservers();
        } else if (pacMan.getX() > entity.getY() && !wallCollider.isCollide(entities, entity, entity.getX() + 1, entity.getY())) {
            entity.setX(entity.getX() + 1);
            direction = Direction.RIGHT;
            notifyObservers();
        } else {
            int i = 0;
            Random r = new Random();
            while (i == 0) {
                switch (r.nextInt(4)) {
                    case 0:
                        if (!wallCollider.isCollide(entities, entity, entity.getX(), entity.getY() - 1)) {
                            entity.setY(entity.getY() - 1);
                            direction = Direction.UP;
                            notifyObservers();
                            i++;
                        }
                        break;
                    case 1:
                        if (!wallCollider.isCollide(entities, entity, entity.getX(), entity.getY() + 1)) {
                            entity.setY(entity.getY() + 1);
                            direction = Direction.DOWN;
                            notifyObservers();
                            i++;
                        }
                        break;
                    case 2:
                        if (!wallCollider.isCollide(entities, entity, entity.getX() - 1, entity.getY())) {
                            entity.setX(entity.getX() - 1);
                            direction = Direction.LEFT;
                            notifyObservers();
                            i++;
                        }
                        break;
                    case 3:
                        if (!wallCollider.isCollide(entities, entity, entity.getX() + 1, entity.getY())) {
                            entity.setX(entity.getX() + 1);
                            direction = Direction.RIGHT;
                            notifyObservers();
                            i++;
                        }
                        break;
                }
            }
        }
    }
}
