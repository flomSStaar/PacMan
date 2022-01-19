package model.animator;

import javafx.scene.image.ImageView;
import model.observer.LooperObserver;
import model.utils.Direction;

/**
 * Definit un animateur de base
 */
public abstract class BaseAnimator implements LooperObserver {
    protected ImageView imageView;
    protected Direction direction;

    /**
     * Definit le constructeur de la classe BaseAnimator
     *
     * @param imageView ImageView qui va etre geree par l'animateur
     */
    public BaseAnimator(ImageView imageView) {
        this.imageView = imageView;
        this.direction = Direction.NONE;
    }

    /**
     * Recupere la directioon de l'animateur
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
