package model.animator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.utils.Direction;
import model.utils.DisplacerObserver;

public class PacManAnimator extends BaseAnimator implements DisplacerObserver {

    private int image;
    private Image[] im;

    public PacManAnimator(ImageView i, Image[] im) {
        super(i);
        this.image = 0;
        this.im = im;
    }

    @Override
    public void onLoop() {
        this.image = (this.image + 1) % 3;
        super.imageView.setImage(im[this.image]);
        switch (super.direction) {
            case UP -> super.imageView.setRotate(270);
            case LEFT -> super.imageView.setRotate(180);
            case DOWN -> super.imageView.setRotate(90);
            case RIGHT -> super.imageView.setRotate(0);
        }
    }

    @Override
    public void onMove(Direction direction) {
        setDirection(direction);
    }
}
