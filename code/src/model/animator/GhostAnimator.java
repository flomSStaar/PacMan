package model.animator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entity.BaseEntity;
import model.utils.Direction;
import model.utils.DisplacerObserver;

public class GhostAnimator extends BaseAnimator implements DisplacerObserver {

    private int image;
    private Image[] im;

    public GhostAnimator(ImageView i, Image[] im) {
        super(i);
        this.image = 0;
        this.im = im;
    }

    @Override
    public void onLoop() {
        this.image = (this.image + 1) % 2;
        switch (super.direction) {
            case UP:
                super.imageView.setImage(im[this.image]);
                break;
            case LEFT:
                super.imageView.setImage(im[this.image + 2]);
                break;
            case DOWN:
                super.imageView.setImage(im[this.image + 4]);
                break;
            case RIGHT:
                super.imageView.setImage(im[this.image + 6]);
                break;
        }
    }

    @Override
    public void onMove(BaseEntity entity, Direction direction) {
        setDirection(direction);
    }
}
