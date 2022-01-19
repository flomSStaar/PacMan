package model.displacer;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.Wall;
import model.entity.ghost.Ghost;
import model.observer.BaseObserver;
import model.utils.Direction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static java.lang.Math.abs;

/**
 * Definit le deplacement d'un fantome
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
    private List<BaseObserver> observerGhost = new ArrayList<>();

    /**
     * Definit le constructeur de GhostDisplacer
     *
     * @param entities Liste des entites
     * @param ghost    Fantome a deplacer
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
     * Modifie la direction future de l'entite
     *
     * @param direction Direction de l'entite
     */
    @Override
    public void setDirection(Direction direction) {
        this.directionFuture = direction;
    }

    /**
     * Modifie l'etat de isEatable
     *
     * @param eatable Future etat
     */
    public void setEatable(boolean eatable) {
        isEatable = eatable;
    }

    /**
     * Modifie l'etat de hasBeenEaten
     *
     * @param hasBeenEaten Future etat
     */
    public void setHasBeenEaten(boolean hasBeenEaten) {
        this.hasBeenEaten = hasBeenEaten;
    }

    /**
     * Trouve la direction pour s'echapper de PacMan
     *
     * @return Direction de fuite
     */
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
     * Trouve la direction pour retourner a la base
     *
     * @return Direction vers la base
     */
    protected Direction returnBase() {
        return findShortestPath(cell, ((int) entity.getX() - ((int) entity.getX() % 15)) / 15, ((int) entity.getY() - ((int) entity.getY() % 15)) / 15, 13, 14);
    }

    /**
     * Trouve le plus court chemin pour arriver a PacMan
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
        if (h % 15 == 0) {
            if (hasBeenEaten) {
                if (((int) entity.getX() - ((int) entity.getX() % 15)) / 15 == 13 && ((int) entity.getY() - ((int) entity.getY() % 15)) / 15 == 14) {
                    notifyGhost(entity);
                }
                direction = returnBase();
            } else
                direction = escape();
        }
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

    /**
     * Attache un observateur
     *
     * @param observer Observateur a ajouter
     */
    public void attachGhost(BaseObserver observer) {
        if (observer != null && !observerGhost.contains(observer))
            observerGhost.add(observer);
    }

    /**
     * Supprime un observateur
     *
     * @param observer Observateur a detacher
     */
    public void detachGhost(BaseObserver observer) {
        observerGhost.remove(observer);
    }

    /**
     * Notifie les observateurs qu'une entite est sur la base
     *
     * @param entity Entite sur la base
     */
    protected void notifyGhost(BaseEntity entity) {
        for (BaseObserver observer : observerGhost) {
            observer.onBase(entity);
        }
    }
}
