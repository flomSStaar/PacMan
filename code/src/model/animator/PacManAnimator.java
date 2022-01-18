package model.animator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entity.BaseEntity;
import model.observer.DisplacerObserver;
import model.utils.Direction;

public class PacManAnimator extends BaseAnimator implements DisplacerObserver {
    private int image;
    private final Image[] defaultSprite;

    /**
     * Créé une instance de PacManAnimator
     *
     * @param imageView     ImageView qui va être gérée par l'animateur
     * @param defaultSprite Sprite du PacMan
     */
    public PacManAnimator(ImageView imageView, Image[] defaultSprite) {
        super(imageView);
        this.image = 0;
        this.defaultSprite = defaultSprite;
    }

    @Override
    public void onLoop() {
        this.image = (this.image + 1) % 3;
        super.imageView.setImage(defaultSprite[this.image]);
        switch (super.direction) {
            case UP -> super.imageView.setRotate(270);
            case LEFT -> super.imageView.setRotate(180);
            case DOWN -> super.imageView.setRotate(90);
            case RIGHT -> super.imageView.setRotate(0);
        }
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        setDirection(direction);
    }
}
