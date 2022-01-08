package launcher;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.World;
import model.displacer.PacManDisplacer;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.Wall;
import model.loop.MovementLooper;
import model.utils.Direction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Stage stage;
    private List<Thread> gameThread = new ArrayList<>();

    public Game(Stage stage) throws IOException {
        this.stage = stage;
        home();
    }

    public void home() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/fxml/vueAccueil.fxml"));
        Scene scene = new Scene(p);
        this.stage.setScene(scene);
        this.stage.show();
    }

    public void parameters() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/fxml/vueParametres.fxml"));
        Scene scene = new Scene(p);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case ESCAPE:
                    try {
                        home();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
        stage.setScene(scene);
    }

    public void close() {
        //La libération des threads ne fonctionne pas
        //Par conséquent le jeu ne s'arrête pas correctement si un thread a été demarré
        for (Thread thread : gameThread) {
            if (thread.getState() == Thread.State.RUNNABLE)
                thread.interrupt();
        }
        gameThread.clear();
        Platform.exit();
    }

    public void launchGame() {
        try {
            List<BaseEntity> entities = new MapLoader().load();
            World world = new World(entities);

            PacMan pacMan = world.getPacMan();
            PacManDisplacer pacManDisplacer = new PacManDisplacer(entities, pacMan);
            MovementLooper ml = new MovementLooper();
            ml.attach(pacManDisplacer);
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
                    case ESCAPE:
                        try {
                            home();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            });
            Thread thread = new Thread(ml, "GameThread");
            thread.start();
            gameThread.add(thread);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //Afficher un message d'erreur si la map n'arrive pas à charger.
            e.printStackTrace();
        }
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
}
