package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;

import java.util.List;

public class BlueGhostDisplacer extends GhostDisplacer {

    public BlueGhostDisplacer(Ghost ghost, PacMan pacMan, List<BaseEntity> entities) {
        super(ghost, pacMan, entities);
    }
}