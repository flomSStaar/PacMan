package model.animator;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.utils.Direction;
import model.utils.Observer;

import javax.swing.text.html.parser.Entity;
import java.net.URL;
import java.util.Map;

public class PacManAnimator extends BaseAnimator {

    private int image;
    private Image[] im;

    public PacManAnimator(ImageView i, Image[] im){
        super(i);
        this.image = 0;
        this.im = im;
    }

    @Override
    public void update(){
        this.image = (this.image + 1)%3;
        super.BaseEntity.setImage(im[this.image]);
        switch (super.d) {
            case UP:
                super.BaseEntity.setRotate(270);
                break;
            case LEFT:
                super.BaseEntity.setRotate(180);
                break;
            case DOWN:
                super.BaseEntity.setRotate(90);
                break;
            case RIGHT:
                super.BaseEntity.setRotate(0);
                break;
            default:
                break;
        }
    }
}
