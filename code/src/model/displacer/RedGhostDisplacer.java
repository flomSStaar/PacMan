package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;

import java.util.List;

public class RedGhostDisplacer extends GhostDisplacer {
    private int h = 0;

    /**
     * Créé une instance de RedGhostDisplacer
     *
     * @param ghost    Fantôme à déplacer
     * @param pacMan   PacMan
     * @param entities Liste des entités
     */
    public RedGhostDisplacer(Ghost ghost, PacMan pacMan, List<BaseEntity> entities) {
        super(ghost, pacMan, entities);
    }

    @Override
    public void onLoop() {
        try {
            if (h % 15 == 0 && (int) super.entity.getX() >= 0 && (int) super.entity.getX() < 420)
                direction = super.findShortestPath(cell, (int) super.entity.getX() / 15, (int) super.entity.getY() / 15, ((int) pacMan.getX() - ((int) pacMan.getX() % 15)) / 15, ((int) pacMan.getY() - ((int) pacMan.getY() % 15)) / 15);
            moveEntity();
            h++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
