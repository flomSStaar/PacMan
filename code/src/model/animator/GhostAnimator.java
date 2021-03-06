package model.animator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.SpriteManager;
import model.entity.BaseEntity;
import model.observer.DisplacerObserver;
import model.utils.Direction;

public class GhostAnimator extends BaseAnimator implements DisplacerObserver {
    private final Image[] defaultSprite;
    private final Image[] eatableSprite;
    private final Image[] eyeSprite;
    private int image;
    /**
     * Dfinit si le fantome est dans un etat où il peut-etre mange.
     */
    private boolean isEatable = false;
    protected boolean hasBeenEaten = false;

    /**
     * Cree une instance de GhostAnimator
     *
     * @param imageView     ImageView qui va etre geree par l'animateur
     * @param defaultSprite Sprites du fantome
     */
    public GhostAnimator(ImageView imageView, Image[] defaultSprite) {
        super(imageView);
        this.image = 0;
        this.defaultSprite = defaultSprite;
        this.eatableSprite = SpriteManager.getEatableGhostSprite();
        this.eyeSprite = SpriteManager.getGhostEyeSprite();
    }

    /**
     * Recupere la valeur de isEateable
     *
     * @return valeur de isEatable
     */
    public boolean isEatable() {
        return isEatable;
    }

    /**
     * Modifie la valeur de isEatable
     *
     * @param eatable Nouvel etat
     */
    public void setEatable(boolean eatable) {
        this.isEatable = eatable;
    }

    /**
     * Recupere la valeur de hasBeenEaten
     *
     * @return valeur de hasBeenEaten
     */
    public boolean hasBeenEaten() {
        return hasBeenEaten;
    }

    /**
     * Modifie la valeur de hasBeenEaten
     *
     * @param hasBeenEaten Nouvel etat
     */
    public void setHasBeenEaten(boolean hasBeenEaten) {
        this.hasBeenEaten = hasBeenEaten;
    }

    @Override
    public void onLoop() {
        this.image = (this.image + 1) % 4;
        if (hasBeenEaten) {
            switch (super.direction) {
                case UP -> super.imageView.setImage(eyeSprite[0]);
                case LEFT -> super.imageView.setImage(eyeSprite[3]);
                case DOWN -> super.imageView.setImage(eyeSprite[2]);
                case RIGHT -> super.imageView.setImage(eyeSprite[1]);
            }
        } else if (isEatable) {
            super.imageView.setImage(eatableSprite[this.image]);
        } else {
            switch (super.direction) {
                case UP -> super.imageView.setImage(defaultSprite[this.image % 2]);
                case LEFT -> super.imageView.setImage(defaultSprite[this.image % 2 + 2]);
                case DOWN -> super.imageView.setImage(defaultSprite[this.image % 2 + 4]);
                case RIGHT -> super.imageView.setImage(defaultSprite[this.image % 2 + 6]);
            }
        }
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        setDirection(direction);
    }
}
