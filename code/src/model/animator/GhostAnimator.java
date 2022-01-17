package model.animator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.SpriteManager;
import model.entity.BaseEntity;
import model.utils.Direction;
import model.observers.DisplacerObserver;

public class GhostAnimator extends BaseAnimator implements DisplacerObserver {
    private final Image[] defaultSprite;
    private final Image[] eatableSprite;
    private int image;
    /**
     * Définit si le fantôme est dans un état ou il peut-être mangé.
     */
    private boolean isEatable = false;

    /**
     * Créé une instance de GhostAnimator
     *
     * @param imageView     ImageView qui va être gérée par l'animateur
     * @param defaultSprite Sprites du fantôme
     */
    public GhostAnimator(ImageView imageView, Image[] defaultSprite) {
        super(imageView);
        this.image = 0;
        this.defaultSprite = defaultSprite;
        this.eatableSprite = SpriteManager.getGhostEatableSprite();
    }

    /**
     * Récupère la valeur de isEateable
     *
     * @return valeur de isEatable
     */
    public boolean isEatable() {
        return isEatable;
    }

    /**
     * Modifie la valeur de isEatable
     *
     * @param eatable Nouvelle état
     */
    public void setEatable(boolean eatable) {
        isEatable = eatable;
    }

    @Override
    public void onLoop() {
        this.image = (this.image + 1) % 2;
        if (isEatable) {
            super.imageView.setImage(eatableSprite[this.image]);
        } else {
            switch (super.direction) {
                case UP -> super.imageView.setImage(defaultSprite[this.image]);
                case LEFT -> super.imageView.setImage(defaultSprite[this.image + 2]);
                case DOWN -> super.imageView.setImage(defaultSprite[this.image + 4]);
                case RIGHT -> super.imageView.setImage(defaultSprite[this.image + 6]);
            }
        }
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        setDirection(direction);
    }
}
