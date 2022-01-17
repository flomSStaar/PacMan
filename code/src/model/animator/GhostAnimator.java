package model.animator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.SpriteManager;
import model.entity.BaseEntity;
import model.utils.Direction;
import model.observers.DisplacerObserver;

public class GhostAnimator extends BaseAnimator implements DisplacerObserver {

    private int image;
    private Image[] defaultSprite;
    private Image[] eatableSprite;
    private Image[] eyeSprite;
    private boolean isEatable = false;
    protected boolean hasBeenEaten = false;


    public GhostAnimator(ImageView i, Image[] defaultSprite) {
        super(i);
        this.image = 0;
        this.defaultSprite = defaultSprite;
        this.eatableSprite = SpriteManager.getGhostEatableSprite();
        this.eyeSprite = SpriteManager.getGhostEyeSprite();
    }

    public void setEatable(boolean eatable) {
        isEatable = eatable;
    }

    public void setHasBeenEaten(boolean hasBeenEaten) {
        this.hasBeenEaten = hasBeenEaten;
    }

    @Override
    public void onLoop() {
        this.image = (this.image + 1) % 4;
        if (hasBeenEaten) {
            switch (super.direction) {
                case UP:
                    super.imageView.setImage(eyeSprite[0]);
                    break;
                case LEFT:
                    super.imageView.setImage(eyeSprite[3]);
                    break;
                case DOWN:
                    super.imageView.setImage(eyeSprite[2]);
                    break;
                case RIGHT:
                    super.imageView.setImage(eyeSprite[1]);
                    break;
            }
        }
        else if (isEatable) {
            super.imageView.setImage(eatableSprite[this.image]);
        } else {
            switch (super.direction) {
                case UP:
                    super.imageView.setImage(defaultSprite[this.image%2]);
                    break;
                case LEFT:
                    super.imageView.setImage(defaultSprite[this.image%2 + 2]);
                    break;
                case DOWN:
                    super.imageView.setImage(defaultSprite[this.image%2 + 4]);
                    break;
                case RIGHT:
                    super.imageView.setImage(defaultSprite[this.image%2 + 6]);
                    break;
            }
        }
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        setDirection(direction);
    }
}
