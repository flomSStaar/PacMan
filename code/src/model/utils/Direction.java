package model.utils;

/**
 * Direction sur x et y
 */
public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
    NONE(0, 0);


    private final int dx;
    private final int dy;

    /**
     * Cree une instance de Direction
     *
     * @param dx Direction sur l'axe x
     * @param dy Direction sur l'axe y
     */
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Recupere la valeur de la direction sur l'axe x
     *
     * @return Valeur de la direction sur l'axe x
     */
    public int getDx() {
        return dx;
    }

    /**
     * Recupere la valeur de la direction sur l'axe y
     *
     * @return Valeur de la direction sur l'axe y
     */
    public int getDy() {
        return dy;
    }
}
