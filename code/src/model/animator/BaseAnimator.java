package model.animator;

import javafx.scene.image.ImageView;
import model.utils.Direction;
import model.utils.LooperObserver;

public abstract class BaseAnimator implements LooperObserver {
    protected ImageView imageView;
    protected Direction direction;

    public BaseAnimator(ImageView i){
        this.imageView = i;
        this.direction = Direction.NONE;
    }

    public Direction getDirection(){
        return direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }
}
