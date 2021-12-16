package Launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.entity.PacMan;
import model.entity.Wall;

import javafx.scene.image.ImageView;
import javax.swing.text.html.parser.Entity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Launcher extends Application {
    public void start(Stage stage) throws IOException {
        //stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/vueJeu.fxml")))));
        List<Wall> walls = new ArrayList<>();
        List<PacMan> pacmans = new ArrayList<>();
        readFile(walls, pacmans);
        getClass().getResource("text/map.txt");
        stage.setScene(getSce(walls, pacmans));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        //stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
    }

    public Scene getSce(List<Wall> walls, List<PacMan> pacmans) {
        Pane pane = new Pane();
        for (Wall entity : walls) {
            ImageView i = new ImageView(new Image("/image/wall.jpg"));
            i.relocate(entity.getX(), entity.getY());
            pane.getChildren().addAll(i);
        }
        for (PacMan entity : pacmans) {
            ImageView i = new ImageView(new Image("/image/PacManSprite.jpg"));
            i.relocate(entity.getX(), entity.getY());
            pane.getChildren().addAll(i);
        }
        return new Scene(pane);
    }

    public void readFile(List<Wall> walls, List<PacMan> pacmans) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(getClass().getResourceAsStream("/text/map.txt")));
        int ligne = 0, collone = 0, c = 0;
        while ((c = br.read()) != -1) {
            switch ((char)c){
                case '\n':
                    collone =  0;
                    ligne += 45;
                    break;
                case '1':
                    collone +=  45;
                    walls.add(new Wall(collone, ligne, 45, 45));
                    break;
                case '2':
                    collone +=  45;
                    pacmans.add(new PacMan(collone, ligne, 45, 45));
                    break;
            }
        }
    }
}
