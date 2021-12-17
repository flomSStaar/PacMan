package Launcher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.entity.PacMan;
import model.entity.Wall;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {
    public void start(Stage stage) throws Exception {
        //stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/vueJeu.fxml")))));
        List<Wall> walls = new ArrayList<>();
        List<PacMan> pacmans = new ArrayList<>();
        readFile(walls, pacmans);
        stage.setScene(getSce(walls, pacmans));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        //stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
    }

    public Scene getSce(List<Wall> walls, List<PacMan> pacmans) throws IOException {
        Pane p = FXMLLoader.load(getClass().getResource("/fxml/vueJeu.fxml"));
        for (Wall entity : walls) {
            ImageView i = new ImageView(new Image("/image/wall.jpg"));
            i.xProperty().bind(entity.xProperty());
            i.yProperty().bind(entity.yProperty());
            p.getChildren().add(i);
        }
        for (PacMan entity : pacmans) {
            ImageView i = new ImageView(new Image("/image/PacManSprite.png"));
            i.xProperty().bind(entity.xProperty());
            i.yProperty().bind(entity.yProperty());
            p.getChildren().add(i);
        }
        return new Scene(p);
    }

    public void readFile(List<Wall> walls, List<PacMan> pacmans) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File(getClass().getResource("/text/map.txt").toURI())));
        int ligne = 0, colonne = 0, c = 0;
        while ((c = br.read()) != -1) {
            switch ((char)c){
                case '\n':
                    colonne =  0;
                    ligne += 45;
                    break;
                case '1':
                    walls.add(new Wall(colonne, ligne, 45, 45));
                    colonne +=  45;
                    break;
                case '2':
                    pacmans.add(new PacMan(colonne, ligne, 45, 45));
                    colonne +=  45;
                    break;
                case '0':
                    colonne +=  45;
                    break;
            }
        }
    }
}
