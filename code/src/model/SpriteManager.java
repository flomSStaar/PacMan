package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.entity.*;
import model.entity.ghost.BlueGhost;
import model.entity.ghost.OrangeGhost;
import model.entity.ghost.PinkGhost;
import model.entity.ghost.RedGhost;
import model.utils.EatObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteManager implements EatObserver {
    private Map<BaseEntity, ImageView> entityImageViewMap = new HashMap<>();
    private Pane pane;

    public SpriteManager(Pane pane) {
        this.pane = pane;
    }

    public void addAllSprite(List<BaseEntity> entities) {
        for (BaseEntity entity : entities) {
            addSprite(entity);
        }
    }

    public void addSprite(BaseEntity entity) {
        ImageView imageView;
        if (entity instanceof Wall) {
            imageView = new ImageView(new Image("/image/wall.png"));
        } else if (entity instanceof PacMan) {
            imageView = new ImageView(new Image("/image/PacManSprite0.png"));
        } else if (entity instanceof Candy) {
            imageView = new ImageView(new Image("/image/Bonbon.png"));
        } else if (entity instanceof SuperCandy) {
            imageView = new ImageView(new Image("/image/SuperBonbon.png"));
        } else if (entity instanceof RedGhost) {
            imageView = new ImageView(new Image("/image/RedGhostRight0.png"));
        } else if (entity instanceof BlueGhost) {
            imageView = new ImageView(new Image("/image/BlueGhostRight0.png"));
        } else if (entity instanceof PinkGhost) {
            imageView = new ImageView(new Image("/image/PinkGhostRight0.png"));
        } else if (entity instanceof OrangeGhost) {
            imageView = new ImageView(new Image("/image/OrangeGhostRight0.png"));
        } else {
            throw new IllegalArgumentException("Unknown Entity");
        }
        addSprite(entity, imageView);
    }

    public void addSprite(BaseEntity entity, ImageView imageView) {
        if (entity == null || imageView == null)
            throw new IllegalArgumentException("Parameter is null");
        imageView.xProperty().bind(entity.xProperty());
        imageView.yProperty().bind(entity.yProperty());
        entityImageViewMap.put(entity, imageView);
        pane.getChildren().add(imageView);
    }

    public void removeSprite(BaseEntity entity) {
        entityImageViewMap.remove(entity);
        pane.getChildren().remove(entityImageViewMap.get(entity));
    }

    public ImageView getImageView(BaseEntity entity){
        return entityImageViewMap.get(entity);
    }

    @Override
    public void onEat(BaseEntity entity) {
        ImageView imageView = entityImageViewMap.get(entity);
        if (imageView == null)
            throw new IllegalArgumentException();
        pane.getChildren().remove(imageView);
        entityImageViewMap.remove(entity);
    }
}
