package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;
import model.utils.Direction;

import java.util.List;

public class BlueGhostDisplacer extends GhostDisplacer {
    /**
     * Créé une instance de BlueGhostDisplacer
     *
     * @param ghost    Fantôme à déplacer
     * @param pacMan   PacMan
     * @param entities Liste des entités
     */
    public BlueGhostDisplacer(Ghost ghost, PacMan pacMan, List<BaseEntity> entities) {
        super(ghost, pacMan, entities);
    }

    @Override
    public void onLoop() {
        this.direction = fuite();
        moveEntity();
    }
}