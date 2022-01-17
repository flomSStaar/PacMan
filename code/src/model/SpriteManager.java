package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.entity.*;
import model.entity.ghost.BlueGhost;
import model.entity.ghost.OrangeGhost;
import model.entity.ghost.PinkGhost;
import model.entity.ghost.RedGhost;
import model.observers.EatObserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteManager implements EatObserver {
    private final Map<BaseEntity, ImageView> entityImageViewMap = new HashMap<>();
    private final Pane pane;

    /**
     * Créé une instance de SpriteManager
     *
     * @param pane Pane de la vue
     */
    public SpriteManager(Pane pane) {
        this.pane = pane;
    }

    /**
     * Remet à zero l'instance du SpriteManager
     */
    public void reset() {
        entityImageViewMap.clear();
        pane.getChildren().clear();
    }

    /**
     * Ajoute toutes les entités à la vue
     *
     * @param entities Liste des entités à ajouter à la vue
     */
    public void addAllSprite(List<BaseEntity> entities) {
        for (BaseEntity entity : entities) {
            addSprite(entity);
        }
    }

    /**
     * Ajoute l'entité à la vue
     *
     * @param entity Entité à ajouter à la vue
     */
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

    /**
     * Ajoute l'ImageView associé à l'entité à la vue
     *
     * @param entity    Entité associée à l'ImageView
     * @param imageView ImageView à ajouter dans la vue
     */
    public void addSprite(BaseEntity entity, ImageView imageView) {
        if (entity == null || imageView == null)
            throw new IllegalArgumentException("Parameter is null");
        imageView.xProperty().bind(entity.xProperty());
        imageView.yProperty().bind(entity.yProperty());
        entityImageViewMap.put(entity, imageView);
        pane.getChildren().add(imageView);
    }

    /**
     * Supprime une l'ImageView associée à l'entité passée en paramètre
     *
     * @param entity Entité à supprimer dans la vue
     */
    public void removeSprite(BaseEntity entity) {
        ImageView imageView = entityImageViewMap.get(entity);
        if (imageView == null)
            throw new IllegalArgumentException("ImageView not found for entity" + entity);
        pane.getChildren().remove(entityImageViewMap.get(entity));
        entityImageViewMap.remove(entity);
    }

    /**
     * Récupère l'ImageView associée à l'entité passé en paramètre
     *
     * @param entity Entité à récupérer
     * @return ImageView de l'entité
     */
    public ImageView getImageView(BaseEntity entity) {
        return entityImageViewMap.get(entity);
    }

    /**
     * Retourne les sprites de PacMan
     *
     * @return Sprites de PacMan
     */
    public static Image[] getPacManSprite() {
        Image[] im = new Image[3];
        im[0] = new Image("/image/PacManSprite0.png");
        im[1] = new Image("/image/PacManSprite1.png");
        im[2] = new Image("/image/PacManSprite2.png");

        return im;
    }

    /**
     * Retourne les sprites du fantôme rouge
     *
     * @return Sprites du fantôme rouge
     */
    public static Image[] getRedSprite() {
        Image[] imgR = new Image[8];
        imgR[0] = new Image("/image/RedGhostUp0.png");
        imgR[1] = new Image("/image/RedGhostUp1.png");
        imgR[2] = new Image("/image/RedGhostLeft0.png");
        imgR[3] = new Image("/image/RedGhostLeft1.png");
        imgR[4] = new Image("/image/RedGhostDown0.png");
        imgR[5] = new Image("/image/RedGhostDown1.png");
        imgR[6] = new Image("/image/RedGhostRight0.png");
        imgR[7] = new Image("/image/RedGhostRight1.png");

        return imgR;
    }

    /**
     * Retourne les sprites du fantôme rose
     *
     * @return Sprites  du fantôme rose
     */
    public static Image[] getPinkSprite() {
        Image[] imgP = new Image[8];
        imgP[0] = new Image("/image/PinkGhostUp0.png");
        imgP[1] = new Image("/image/PinkGhostUp1.png");
        imgP[2] = new Image("/image/PinkGhostLeft0.png");
        imgP[3] = new Image("/image/PinkGhostLeft1.png");
        imgP[4] = new Image("/image/PinkGhostDown0.png");
        imgP[5] = new Image("/image/PinkGhostDown1.png");
        imgP[6] = new Image("/image/PinkGhostRight0.png");
        imgP[7] = new Image("/image/PinkGhostRight1.png");

        return imgP;
    }

    /**
     * Retourne les sprites du fantôme bleu
     *
     * @return Sprites du fantôme bleu
     */
    public static Image[] getBlueSprite() {
        Image[] imgB = new Image[8];
        imgB[0] = new Image("/image/BlueGhostUp0.png");
        imgB[1] = new Image("/image/BlueGhostUp1.png");
        imgB[2] = new Image("/image/BlueGhostLeft0.png");
        imgB[3] = new Image("/image/BlueGhostLeft1.png");
        imgB[4] = new Image("/image/BlueGhostDown0.png");
        imgB[5] = new Image("/image/BlueGhostDown1.png");
        imgB[6] = new Image("/image/BlueGhostRight0.png");
        imgB[7] = new Image("/image/BlueGhostRight1.png");

        return imgB;
    }

    /**
     * Retourne les sprites du fantôme orange
     *
     * @return Sprites du fantôme orange
     */
    public static Image[] getOrangeSprite() {
        Image[] imgO = new Image[8];
        imgO[0] = new Image("/image/OrangeGhostUp0.png");
        imgO[1] = new Image("/image/OrangeGhostUp1.png");
        imgO[2] = new Image("/image/OrangeGhostLeft0.png");
        imgO[3] = new Image("/image/OrangeGhostLeft1.png");
        imgO[4] = new Image("/image/OrangeGhostDown0.png");
        imgO[5] = new Image("/image/OrangeGhostDown1.png");
        imgO[6] = new Image("/image/OrangeGhostRight0.png");
        imgO[7] = new Image("/image/OrangeGhostRight1.png");

        return imgO;
    }

    /**
     * Retourne les sprites d'un fantôme mangeable
     *
     * @return Sprites d'un fantôme mangeable
     */
    public static Image[] getGhostEatableSprite() {
        Image[] im = new Image[4];
        im[0] = new Image("/image/GhostEatable0.png");
        im[1] = new Image("/image/GhostEatable1.png");
        im[2] = new Image("/image/GhostEatable2.png");
        im[3] = new Image("/image/GhostEatable3.png");
        return im;
    }

    public static Image[] getGhostEyeSprite() {
        Image[] im = new Image[4];
        im[0] = new Image("/image/GhostEyeUp.png");
        im[1] = new Image("/image/GhostEyeRight.png");
        im[2] = new Image("/image/GhostEyeDown.png");
        im[3] = new Image("/image/GhostEyeLeft.png");
        return im;
    }

    @Override
    public void onEat(BaseEntity entity) {
        removeSprite(entity);
    }
}
