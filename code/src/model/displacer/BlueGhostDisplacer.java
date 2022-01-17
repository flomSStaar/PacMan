package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;

import java.util.List;

public class BlueGhostDisplacer extends GhostDisplacer {
    /**
     * Créé une instance de BlueGhostDisplacer
     *
     * @param entities Liste des entités
     * @param ghost    Fantôme à déplacer
     * @param pacMan   PacMan
     */
    public BlueGhostDisplacer(List<BaseEntity> entities, Ghost ghost, PacMan pacMan) {
        super(entities, ghost, pacMan);
    }

    @Override
    public void onLoop() {
        this.direction = fuite();
        moveEntity();
    }
}