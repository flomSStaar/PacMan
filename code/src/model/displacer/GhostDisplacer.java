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

/**
 * Définit le déplacement d'un fantôme
 * <p>
 * A FINIR DE COMMENTER
 */
public abstract class GhostDisplacer extends BaseDisplacer {
    protected PacMan pacMan;
    protected boolean[][] cell;
    protected Direction directionFuture = Direction.NONE;
    protected int h = 0;
    protected boolean isEatable = false;
    protected boolean hasBeenEaten = false;

    /**
     * Définit le constructeur de GhostDisplacer
     *
     * @param entities Liste des entités
     * @param ghost    Fantôme à déplacer
     * @param pacMan   PacMan
     */
    public GhostDisplacer(List<BaseEntity> entities, Ghost ghost, PacMan pacMan) {
        super(entities, ghost);
        this.pacMan = pacMan;
        cell = new boolean[31][29];
        for (BaseEntity entity : entities) {
            if (entity instanceof Wall) {
                cell[(int) entity.getY() / 15][(int) entity.getX() / 15] = true;
            }
        }
    }

    /**
     * Modifie la direction future de l'entité
     *
     * @param direction Direction de l'entité
     */
    @Override
    public void setDirection(Direction direction) {
        this.directionFuture = direction;
    }

    public void setEatable(boolean eatable) {
        isEatable = eatable;
    }

    protected Direction escape() {
        int x = 0;
        int y = 0;
        int d = 0;
        int xf = ((int) pacMan.getX() - ((int) pacMan.getX() % 15)) / 15;
        int yf = ((int) pacMan.getY() - ((int) pacMan.getY() % 15)) / 15;
        for (int g = 0; g < 28; g++) {
            for (int h = 0; h < 31; h++) {
                if (d < abs(g - xf) + abs(h - yf) && !cell[h][g]) {
                    d = abs(g - xf) + abs(h - yf);
                    x = g;
                    y = h;
                }
            }
        }
        return findShortestPath(cell, ((int) entity.getX() - ((int) entity.getX() % 15)) / 15, ((int) entity.getY() - ((int) entity.getY() % 15)) / 15, x, y);
    }

    /**
     * Trouve le plus court chemin pour arriver à PacMan
     *
     * @param lab
     * @param cx
     * @param cy
     * @param mx
     * @param my
     * @return
     */
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

    @Override
    public void onLoop() {
        if (h % 15 == 0)
            direction = escape();
        if (!wallCollider.isCollide(entities, super.entity, super.entity.getX() + direction.getDx(), super.entity.getY() + direction.getDy())) {
            moveEntity();
        }
        h++;
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
