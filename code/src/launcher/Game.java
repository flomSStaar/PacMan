package launcher;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.World;
import model.animator.GhostAnimator;
import model.animator.PacManAnimator;
import model.displacer.GhostDisplacer;
import model.displacer.PacManDisplacer;
import model.entity.*;
import model.loop.AnimationLooper;
import model.loop.GameLooper;
import model.loop.MovementLooper;
import model.utils.Direction;
import model.utils.EntityObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game implements EntityObserver {
    private Stage stage;
    private List<Thread> gameThread = new ArrayList<>();
    private Map<BaseEntity, ImageView> mapImageView = new HashMap<>();
    private World world;
    private Pane pane;

    public Game(Stage stage) throws IOException {
        this.stage = stage;
        stage.setMinWidth(420);
        stage.setMinHeight(502);
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
            int score = 0;
            List<BaseEntity> entities = new MapLoader().load();
            world = new World(entities);

            PacMan pacMan = world.getPacMan();
            Scene scene = this.getScene(entities, this.mapImageView, score);
            Image[] im = new Image[3];
            im[0] = new Image("/image/PacManSprite0.png");
            im[1] = new Image("/image/PacManSprite1.png");
            im[2] = new Image("/image/PacManSprite2.png");
            PacManAnimator pacManAnimator = new PacManAnimator(this.mapImageView.get(pacMan), im);
            PacManDisplacer pacManDisplacer = new PacManDisplacer(entities, pacMan);
            pacManDisplacer.attach(pacManAnimator);
            MovementLooper movementLooper = new MovementLooper();
            movementLooper.attach(pacManDisplacer);
            AnimationLooper animationLooper = new AnimationLooper();
            animationLooper.attach(pacManAnimator);
            Image[] img = new Image[8];
            img[0] = new Image("/image/RedGhostUp0.png");
            img[1] = new Image("/image/RedGhostUp1.png");
            img[2] = new Image("/image/RedGhostLeft0.png");
            img[3] = new Image("/image/RedGhostLeft1.png");
            img[4] = new Image("/image/RedGhostDown0.png");
            img[5] = new Image("/image/RedGhostDown1.png");
            img[6] = new Image("/image/RedGhostRight0.png");
            img[7] = new Image("/image/RedGhostRight1.png");
            for (Ghost g : world.getGhosts()) {
                GhostAnimator ghostAnimator = new GhostAnimator(this.mapImageView.get(g), img);
                animationLooper.attach(ghostAnimator);
                GhostDisplacer ghostDisplacer = new GhostDisplacer(g, world.getPacMan(), world.getEntities());
                ghostDisplacer.attach(ghostAnimator);
                movementLooper.attach(ghostDisplacer);
            }
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
            GameLooper gl = new GameLooper(0, world);
            Thread thread = new Thread(gl, "GameThreadMove");
            gl.attach(this);
            thread.start();
            gameThread.add(thread);
            Thread thread1 = new Thread(movementLooper, "GameThreadMove");
            thread1.start();
            gameThread.add(thread1);
            Thread thread2 = new Thread(animationLooper, "GameThreadAnimation");
            thread2.start();
            gameThread.add(thread2);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //Afficher un message d'erreur si la map n'arrive pas à charger.
            e.printStackTrace();
        }
    }

    public Scene getScene(List<BaseEntity> entities, Map<BaseEntity, ImageView> m, int score) throws IOException {
        pane = FXMLLoader.load(getClass().getResource("/fxml/vueJeu.fxml"));
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        for (BaseEntity entity : entities) {
            ImageView i;
            if (entity instanceof Wall) {
                i = new ImageView(new Image("/image/wall.png"));
            } else if (entity instanceof PacMan) {
                i = new ImageView(new Image("/image/PacManSprite0.png"));
            } else if (entity instanceof Candy) {
                i = new ImageView(new Image("/image/Bonbon.png"));
            } else if (entity instanceof SuperCandy) {
                i = new ImageView(new Image("/image/SuperBonbon.png"));
            } else if (entity instanceof Ghost) {
                i = new ImageView(new Image("/image/RedGhostRight0.png"));
            } else {
                continue;
            }
            m.put(entity, i);
            i.xProperty().bind(entity.xProperty());
            i.yProperty().bind(entity.yProperty());
            pane.getChildren().add(i);
        }
        return new Scene(pane);
    }

    @Override
    public void update(BaseEntity e) {
        pane.getChildren().remove(mapImageView.get(e));
        mapImageView.remove(e);
        world.Remove(e);
    }
}
