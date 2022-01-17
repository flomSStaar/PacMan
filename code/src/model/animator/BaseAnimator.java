package model.animator;

import javafx.scene.image.ImageView;
import model.observers.LooperObserver;
import model.utils.Direction;

/**
 * Définit un animateur de base
 */
public abstract class BaseAnimator implements LooperObserver {
    protected ImageView imageView;
    protected Direction direction;

    /**
     * Définit le constructeur de la classe BaseAnimator
     *
     * @param imageView ImageView qui va être gérée par l'animateur
     */
    public BaseAnimator(ImageView imageView) {
        this.imageView = imageView;
        this.direction = Direction.NONE;
    }

    /**
     * Récupère la directioon de l'animateur
     *
     * @return Direction de l'animateur
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Modifie la direction de l'animateur
     *
     * @param direction Future direction de l'animateur
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
