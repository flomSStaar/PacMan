package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;

import java.util.List;

public class BlueGhostDisplacer extends GhostDisplacer {
    /**
     * Cree une instance de BlueGhostDisplacer
     *
     * @param entities Liste des entites
     * @param ghost    Fantome a deplacer
     * @param pacMan   PacMan
     */
    public BlueGhostDisplacer(List<BaseEntity> entities, Ghost ghost, PacMan pacMan) {
        super(entities, ghost, pacMan);
    }
}