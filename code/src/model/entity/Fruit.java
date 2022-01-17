package model.entity;

public class Fruit extends BaseEntity {
    /**
     * Créé une instance de Fruit
     *
     * @param x      Position x
     * @param y      Position y
     * @param width  Longueur de l'entité
     * @param height Largeur de l'entité
     */
    public Fruit(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
