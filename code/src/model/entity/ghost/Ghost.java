package model.entity.ghost;

import model.entity.BaseEntity;

public abstract class Ghost extends BaseEntity {
    /**
     * Créé une instance de GhostGhost
     *
     * @param x      Position x
     * @param y      Position y
     * @param width  Longueur de l'entité
     * @param height Largeur de l'entité
     */
    public Ghost(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
}
