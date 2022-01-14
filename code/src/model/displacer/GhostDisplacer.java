package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.Wall;
import model.entity.ghost.Ghost;
import model.utils.Direction;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static java.lang.Math.abs;

public class GhostDisplacer extends BaseDisplacer {
    protected PacMan pacMan;
    protected boolean[][] cell;
    protected Direction directionFuture = Direction.NONE;


    public GhostDisplacer(Ghost ghost, PacMan pacMan, List<BaseEntity> entities) {
        super.entity = ghost;
        this.pacMan = pacMan;
        super.entities = entities;
        cell = new boolean[31][29];
        for (BaseEntity entity : entities) {
            if (entity instanceof Wall) {
                cell[(int) entity.getY() / 15][(int) entity.getX() / 15] = true;
            }
        }
    }

    public void move(Direction direction) {
        this.directionFuture = direction;
    }

    @Override
    public void onLoop() {
    }

    protected Direction fuite() {
        int x = 0;
        int y = 0;
        int d = 0;
        int xf = ((int) entity.getX() - ((int) entity.getX() % 15)) / 15;
        int yf = ((int) entity.getY() - ((int) entity.getY() % 15)) / 15;
        for (int g = 0; g < 28; g++) {
            for (int h = 0; h < 31; h++) {
                if (d < abs(g - xf) + abs(h - yf) && !cell[h][g]) {
                    d = abs(g - xf) + abs(h - yf);
                    x = g;
                    y = h;
                }
            }
        }
        return findShortestPath(cell, xf, yf, x, y);
    }

    protected Direction findShortestPath(boolean[][] lab, int cx, int cy, int mx, int my) {
        Queue<Node> queue = new ArrayDeque<>();
        boolean[][] discovered = new boolean[31][29];
        discovered[cy][cx] = true;
        queue.add(new Node(cx, cy, Direction.NONE));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            for (Direction dir : Direction.values()) {
                switch (dir) {
                    case NONE:
                        break;
                    default:
                        int newX = (node.x + dir.getDx()) % 29;
                        int newY = (node.y + dir.getDy()) % 31;
                        if (newX == -1)
                            newX = 28;
                        if (newY == -1)
                            newY = 30;
                        Direction newDir = node.initialDir == Direction.NONE ? dir : node.initialDir;
                        if (newX == mx && newY == my) {
                            return newDir;
                        }
                        if (!lab[newY][newX] && !discovered[newY][newX]) {
                            discovered[newY][newX] = true;
                            queue.add(new Node(newX, newY, newDir));
                        }
                        break;
                }
            }
        }

        throw new IllegalStateException("No path found");
    }

    private static class Node {
        final int x;
        final int y;
        final Direction initialDir;

        public Node(int x, int y, Direction initialDir) {
            this.x = x;
            this.y = y;
            this.initialDir = initialDir;
        }
    }

}
