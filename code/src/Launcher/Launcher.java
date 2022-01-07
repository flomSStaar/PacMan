package Launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.displacer.PacManDisplacer;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.Wall;
import model.utils.Direction.Direction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {
    public void start(Stage stage) throws Exception {
        List<BaseEntity> entities = new ArrayList<>();
        readFile(entities);
        PacMan pacMan = getPacMan(entities);
        PacManDisplacer pacManDisplacer = new PacManDisplacer(entities, pacMan);
        Scene scene = this.getScene(entities);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case Z:
                    pacManDisplacer.move(Direction.UP);
                    break;
                case Q:
                    pacManDisplacer.move(Direction.LEFT);
                    break;
                case S:
                    pacManDisplacer.move(Direction.DOWN);
                    break;
                case D:
                    pacManDisplacer.move(Direction.RIGHT);
                    break;
            }
        });
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    public Scene getScene(List<BaseEntity> entities) throws IOException {
        Pane p = FXMLLoader.load(getClass().getResource("/fxml/vueJeu.fxml"));
        for (BaseEntity entity : entities) {
            ImageView i;
            if (entity instanceof Wall) {
                i = new ImageView(new Image("/image/wall.jpg"));

            } else if (entity instanceof PacMan) {
                i = new ImageView(new Image("/image/PacManSprite.png"));
            } else {
                continue;
            }
            i.xProperty().bind(entity.xProperty());
            i.yProperty().bind(entity.yProperty());
            p.getChildren().add(i);
        }
        return new Scene(p);
    }

    private PacMan getPacMan(List<BaseEntity> entities) {
        for (var entity : entities) {
            if (entity instanceof PacMan)
                return (PacMan) entity;
        }
        return null;
    }

    public void readFile(List<BaseEntity> entities) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File(getClass().getResource("/text/map.txt").toURI())));
        int ligne = 0, colonne = 0, c = 0;
        while ((c = br.read()) != -1) {
            switch ((char) c) {
                case '\n':
                    colonne = 0;
                    ligne += 45;
                    break;
                case '1':
                    entities.add(new Wall(colonne, ligne, 45, 45));
                    colonne += 45;
                    break;
                case '2':
                    entities.add(new PacMan(colonne, ligne, 35, 35));
                    colonne += 45;
                    break;
                case '0':
                    colonne += 45;
                    break;
            }
        }
    }
}
