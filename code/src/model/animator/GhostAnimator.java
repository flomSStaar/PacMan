package model.animator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GhostAnimator extends BaseAnimator {

    private int image;
    private Image[] im;

    public GhostAnimator(ImageView i, Image[] im){
        super(i);
        this.image = 0;
        this.im = im;
    }

    @Override
    public void update(){
        this.image = (this.image + 1)%2;
        switch (super.d) {
            case UP:
                super.BaseEntity.setImage(im[this.image]);
                break;
            case LEFT:
                super.BaseEntity.setImage(im[this.image+2]);
                break;
            case DOWN:
                super.BaseEntity.setImage(im[this.image+4]);
                break;
            case RIGHT:
                super.BaseEntity.setImage(im[this.image+6]);
                break;
            default:
                break;
        }
    }
}
