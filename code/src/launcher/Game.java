package launcher;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.SpriteManager;
import model.World;
import model.animator.GhostAnimator;
import model.animator.PacManAnimator;
import model.displacer.GhostDisplacer;
import model.displacer.PacManDisplacer;
import model.eater.CandyEater;
import model.entity.BaseEntity;
import model.entity.Ghost;
import model.entity.PacMan;
import model.loop.AnimationLooper;
import model.loop.MovementLooper;
import model.utils.Direction;

import java.io.IOException;
import java.util.List;

public class Game {
    private Stage stage;
    private SpriteManager spriteManager;

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
        Platform.exit();
        System.exit(0);
    }

    public void launchGame() {
        try {
            List<BaseEntity> entities = new MapLoader().load();
            Pane pane = FXMLLoader.load(getClass().getResource("/fxml/vueJeu.fxml"));
            pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            Scene scene = new Scene(pane);
            World world = new World(entities);
            PacMan pacMan = world.getPacMan();
            spriteManager = new SpriteManager(pane);

            spriteManager.addAllSprite(entities);
            Text scoreText = new Text(10, 40, "score");
            scoreText.setFill(Color.WHITE);
            scoreText.setFont(Font.loadFont(getClass().getResourceAsStream("/font/PAC-FONT.ttf"), 30));
            pane.getChildren().add(scoreText);

            Image[] im = new Image[3];
            im[0] = new Image("/image/PacManSprite0.png");
            im[1] = new Image("/image/PacManSprite1.png");
            im[2] = new Image("/image/PacManSprite2.png");

            PacManAnimator pacManAnimator = new PacManAnimator(spriteManager.getImageView(pacMan), im);
            PacManDisplacer pacManDisplacer = new PacManDisplacer(entities, pacMan);
            MovementLooper movementLooper = new MovementLooper();
            AnimationLooper animationLooper = new AnimationLooper();
            CandyEater candyEater = new CandyEater(entities);

            pacManDisplacer.attach(pacManAnimator);
            pacManDisplacer.attach(candyEater);
            movementLooper.attach(pacManDisplacer);

            animationLooper.attach(pacManAnimator);

            candyEater.attach(spriteManager);
            candyEater.attach(world);
            candyEater.attach(world.getScore());

            Image[] img = new Image[8];
            img[0] = new Image("/image/RedGhostUp0.png");
            img[1] = new Image("/image/RedGhostUp1.png");
            img[2] = new Image("/image/RedGhostLeft0.png");
            img[3] = new Image("/image/RedGhostLeft1.png");
            img[4] = new Image("/image/RedGhostDown0.png");
            img[5] = new Image("/image/RedGhostDown1.png");
            img[6] = new Image("/image/RedGhostRight0.png");
            img[7] = new Image("/image/RedGhostRight1.png");
            for (Ghost ghost : world.getGhosts()) {
                GhostAnimator ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), img);
                GhostDisplacer ghostDisplacer = new GhostDisplacer(ghost, world.getPacMan(), world.getEntities());

                ghostDisplacer.attach(ghostAnimator);
                animationLooper.attach(ghostAnimator);
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
                    case A:
                        candyEater.attach(spriteManager);
                        candyEater.attach(world);
                        candyEater.attach(world.getScore());
                        break;
                    case E:
                        candyEater.detach(spriteManager);
                        candyEater.detach(world);
                        candyEater.detach(world.getScore());
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

            Thread thread1 = new Thread(movementLooper, "GameThreadMove");
            thread1.start();
            Thread thread2 = new Thread(animationLooper, "GameThreadAnimation");
            thread2.start();

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            //Afficher un message d'erreur si la map n'arrive pas à charger.
            e.printStackTrace();
        }
    }
}
