package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.Ghost;

import java.util.List;
import java.util.Random;

public class OrangeGhostDisplacer extends GhostDisplacer{

    private int h = 0;
    private int x;
    private int y;

    public OrangeGhostDisplacer(Ghost ghost, PacMan pacMan, List<BaseEntity> entities) {
        super(ghost, pacMan, entities);
        x = 0;
        y = 0;
        Random rand = new Random();
        while (cell[y][x]) {
            x = rand.nextInt(29);
            y = rand.nextInt(31);
        }
    }

    @Override
    public void onLoop() {
        if (h % 15 == 0 && (int) super.entity.getX() >= 0 && (int) super.entity.getX() < 420) {
            if((int) super.entity.getX()/15 == x && (int) super.entity.getY()/15 == y) {
                Random rand = new Random();
                x = 0;
                y = 0;
                while (cell[y][x]) {
                    x = rand.nextInt(25)+3;
                    y = rand.nextInt(31);
                }
            }
            direction = super.findShortestPath(cell, (int) super.entity.getX() / 15, (int) super.entity.getY() / 15, x, y);
        }
        moveEntity(direction);
        h++;
    }
}
