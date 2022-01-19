package model.entity;

public class Wall extends BaseEntity {
    /**
     * Cree une instance de Wall
     *
     * @param x      Position x
     * @param y      Position y
     * @param width  Longueur de l'entite
     * @param height Largeur de l'entite
     */
    public Wall(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
