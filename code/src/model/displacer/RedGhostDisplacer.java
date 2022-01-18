package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;

import java.util.List;

public class RedGhostDisplacer extends GhostDisplacer {


    public RedGhostDisplacer(List<BaseEntity> entities, Ghost ghost, PacMan pacMan) {
        super(entities, ghost, pacMan);
    }

    @Override
    public void onLoop() {
        if(isEatable || hasBeenEaten)
            super.onLoop();
        else {
            try {
                if (h % 15 == 0 && (int) super.entity.getX() >= 0 && (int) super.entity.getX() < 420)
                    direction = super.findShortestPath(cell, (int) super.entity.getX() / 15, (int) super.entity.getY() / 15, ((int) pacMan.getX() - ((int) pacMan.getX() % 15)) / 15, ((int) pacMan.getY() - ((int) pacMan.getY() % 15)) / 15);
                if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + direction.getDx(), super.entity.getY() + direction.getDy())) {
                    moveEntity();
                }
                h++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
