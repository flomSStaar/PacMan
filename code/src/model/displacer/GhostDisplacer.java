package model.displacer;

import model.collider.WallCollider;
import model.entity.BaseEntity;
import model.entity.Ghost;
import model.entity.PacMan;
import model.entity.Wall;
import model.utils.Direction;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GhostDisplacer extends BaseDisplacer {
    private PacMan pacMan;
    private boolean[][] cell;
    private int h = 0;

    public GhostDisplacer(Ghost g, PacMan p, List<BaseEntity> l) {
        super.entity = g;
        pacMan = p;
        super.entities = l;
        cell = new boolean[31][29];
        for (BaseEntity e : l) {
            if (e instanceof Wall) {
                cell[(int)e.getY()/15][(int)e.getX()/15] = true;
            }
        }
    }


    @Override
    public void onLoop() {
        if(h%15 == 0)
            direction = findShortestPath(cell, (int)super.entity.getX()/15, (int)super.entity.getY()/15, ((int)pacMan.getX()-((int)pacMan.getX()%15))/15, ((int)pacMan.getY()-((int)pacMan.getY()%15))/15);
        bouge(direction, 1);
        h++;
    }
    private Direction findShortestPath(boolean[][] lab, int cx, int cy, int mx, int my) {
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
                        if(newX == -1)
                            newX = 28;
                        if(newY == -1)
                            newY = 30;
                        Direction newDir = node.initialDir == Direction.NONE ? dir : node.initialDir;
                        // Mouse found?
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
