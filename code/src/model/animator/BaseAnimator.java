package model.animator;

import javafx.scene.image.ImageView;
import model.entity.BaseEntity;
import model.utils.Direction;
import model.utils.Observer;

import javax.swing.text.html.parser.Entity;
import java.util.Map;

public class BaseAnimator implements Observer {
    protected ImageView BaseEntity;
    protected Direction d;

    public BaseAnimator(ImageView i){
        this.BaseEntity = i;
        this.d = Direction.NONE;
    }

    public void SetDirection(Direction direction){
        this.d = direction;
    }

    @Override
    public void update(){}
}
