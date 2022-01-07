package model.displacer;

import model.collider.BaseCollider;
import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.Direction.Direction;

import java.util.List;

public class PacManDisplacer extends BaseDisplacer {
    private List<BaseEntity> entities;
    private PacMan pacMan;
    private WallCollider wallCollider = new WallCollider();

    public PacManDisplacer(List<BaseEntity> entities, PacMan pacMan) {
        this.entities = entities;
        this.pacMan = pacMan;
    }

    @Override
    public void move(Direction direction) {
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
