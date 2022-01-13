package model.displacer;

import model.entity.BaseEntity;
import model.entity.Ghost;
import model.entity.PacMan;

import java.util.List;
import java.util.Random;

public class BlueGhostDisplacer extends GhostDisplacer{
    public BlueGhostDisplacer(Ghost ghost, PacMan pacMan, List<BaseEntity> entities) {
        super(ghost, pacMan, entities);
    }

    public void onLoop() {
        moveEntity(fuite(), 1);
    }
}