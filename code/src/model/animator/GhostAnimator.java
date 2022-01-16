package model.animator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.SpriteManager;
import model.entity.BaseEntity;
import model.utils.Direction;
import model.utils.DisplacerObserver;

public class GhostAnimator extends BaseAnimator implements DisplacerObserver {

    private int image;
    private Image[] defaultSprite;
    private Image[] eatableSprite;
    private boolean isEatable = false;

    public GhostAnimator(ImageView i, Image[] defaultSprite) {
        super(i);
        this.image = 0;
        this.defaultSprite = defaultSprite;
        this.eatableSprite = SpriteManager.getGhostEatableSprite();
    }

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
                case UP:
                    super.imageView.setImage(defaultSprite[this.image]);
                    break;
                case LEFT:
                    super.imageView.setImage(defaultSprite[this.image + 2]);
                    break;
                case DOWN:
                    super.imageView.setImage(defaultSprite[this.image + 4]);
                    break;
                case RIGHT:
                    super.imageView.setImage(defaultSprite[this.image + 6]);
                    break;
            }
        }
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        setDirection(direction);
    }
}
