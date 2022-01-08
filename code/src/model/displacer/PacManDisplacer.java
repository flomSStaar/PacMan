package model.displacer;

import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.Direction;
import model.utils.Observer;

import java.util.List;

public class PacManDisplacer extends BaseDisplacer implements Observer {
    private List<BaseEntity> entities;
    private PacMan pacMan;
    private WallCollider wallCollider = new WallCollider();
    private Direction direction = Direction.RIGHT;

    public PacManDisplacer(List<BaseEntity> entities, PacMan pacMan) {
        this.entities = entities;
        this.pacMan = pacMan;
    }

    @Override
    public void move(Direction direction) {
        switch (direction) {
            case UP:
                if (!wallCollider.isCollide(entities, pacMan, pacMan.getX(), pacMan.getY() - 5))
                    this.direction = Direction.UP;
                break;
            case LEFT:
                if (!wallCollider.isCollide(entities, pacMan, pacMan.getX() - 5, pacMan.getY()))
                    this.direction = Direction.LEFT;
                break;
            case DOWN:
                if (!wallCollider.isCollide(entities, pacMan, pacMan.getX(), pacMan.getY() + 5))
                    this.direction = Direction.DOWN;
                break;
            case RIGHT:
                if (!wallCollider.isCollide(entities, pacMan, pacMan.getX() + 5, pacMan.getY()))
                    this.direction = Direction.RIGHT;
                break;
        }
    }

    @Override
    public void update() {
        switch (direction) {
            case UP:
                if (!wallCollider.isCollide(entities, pacMan, pacMan.getX(), pacMan.getY() - 5))
                    pacMan.setY(pacMan.getY() - 5);
                break;
            case LEFT:
                if (!wallCollider.isCollide(entities, pacMan, pacMan.getX() - 5, pacMan.getY()))
                    pacMan.setX(pacMan.getX() - 5);
                break;
            case DOWN:
                if (!wallCollider.isCollide(entities, pacMan, pacMan.getX(), pacMan.getY() + 5))
                    pacMan.setY(pacMan.getY() + 5);
                break;
            case RIGHT:
                if (!wallCollider.isCollide(entities, pacMan, pacMan.getX() + 5, pacMan.getY()))
                    pacMan.setX(pacMan.getX() + 5);
                break;
        }
    }
}
