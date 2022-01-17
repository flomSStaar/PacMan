package model.entity;

public class Wall extends BaseEntity {
    /**
     * Créé une instance de Wall
     *
     * @param x      Position x
     * @param y      Position y
     * @param width  Longueur de l'entité
     * @param height Largeur de l'entité
     */
    public Wall(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
