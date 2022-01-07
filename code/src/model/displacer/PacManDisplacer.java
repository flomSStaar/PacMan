package model.displacer;

import model.collider.BaseCollider;
import model.entity.PacMan;
import model.utils.Direction.Direction;

public class PacManDisplacer extends BaseDisplacer {
    private PacMan pacMan;
    private BaseCollider collider;

    public PacManDisplacer(PacMan pacMan, BaseCollider collider) {
        this.collider = collider;
        this.pacMan = pacMan;
    }

    @Override
    public void move(Direction direction) {

    }
}
