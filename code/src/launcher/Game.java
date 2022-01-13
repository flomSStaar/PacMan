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
import model.displacer.*;
import model.eater.CandyEater;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.BlueGhost;
import model.entity.ghost.Ghost;
import model.entity.ghost.PinkGhost;
import model.entity.ghost.RedGhost;
import model.loop.AnimationLooper;
import model.loop.Looper;
import model.loop.MovementLooper;
import model.utils.Direction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Stage stage;
    private SpriteManager spriteManager;

    private boolean isGameLaunched = false;
    private List<Looper> loopers = new ArrayList<>();

    public Game(Stage stage) throws IOException {
        this.stage = stage;
        stage.setMinWidth(420);
        stage.setMinHeight(502);
        home();
        this.stage.show();
    }

    public void home() throws IOException {
        if (isGameLaunched) {
            stopGame();
        }
        Parent p = FXMLLoader.load(getClass().getResource("/fxml/vueAccueil.fxml"));
        Scene scene = new Scene(p);
        this.stage.setScene(scene);
    }

    public void parameters() throws IOException {
        if (isGameLaunched) {
            stopGame();
        }
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
        this.stage.setScene(scene);
    }

    public void close() {
        if (isGameLaunched) {
            stopGame();
        }
        Platform.exit();
        System.exit(0);
    }

    public void launchGame() {
        if (isGameLaunched) {
            stopGame();
        }
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
            scoreText.textProperty().bind(world.getScore().scoreProperty());
            scoreText.setFill(Color.WHITE);
            scoreText.setFont(Font.loadFont(getClass().getResourceAsStream("/font/emulogic.ttf"), 15));
            pane.getChildren().add(scoreText);

            PacManAnimator pacManAnimator = new PacManAnimator(spriteManager.getImageView(pacMan), SpriteManager.getPacManSprite());
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

            loopers.add(movementLooper);
            loopers.add(animationLooper);

            List<PinkGhostDisplacer> pgd = new ArrayList<>();
            for (Ghost ghost : world.getGhosts()) {
                GhostAnimator ghostAnimator;
                if (ghost instanceof RedGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getRedSprite());
                    RedGhostDisplacer ghostDisplacer = new RedGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                    ghostDisplacer.attach(ghostAnimator);
                    animationLooper.attach(ghostAnimator);
                    movementLooper.attach(ghostDisplacer);
                } else if (ghost instanceof BlueGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getBlueSprite());
                    BlueGhostDisplacer ghostDisplacer = new BlueGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                    ghostDisplacer.attach(ghostAnimator);
                    animationLooper.attach(ghostAnimator);
                    movementLooper.attach(ghostDisplacer);
                } else if (ghost instanceof PinkGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getPingSprite());
                    PinkGhostDisplacer ghostDisplacer = new PinkGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                    ghostDisplacer.attach(ghostAnimator);
                    animationLooper.attach(ghostAnimator);
                    movementLooper.attach(ghostDisplacer);
                    pgd.add(ghostDisplacer);
                } else {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getOrangeSprite());
                    OrangeGhostDisplacer ghostDisplacer = new OrangeGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                    ghostDisplacer.attach(ghostAnimator);
                    animationLooper.attach(ghostAnimator);
                    movementLooper.attach(ghostDisplacer);
                }
            }
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                switch (key.getCode()) {
                    case Z:
                        pacManDisplacer.move(Direction.UP);
                        for (PinkGhostDisplacer p : pgd) {
                            p.move(Direction.UP);
                        }
                        break;
                    case Q:
                        pacManDisplacer.move(Direction.LEFT);
                        for (PinkGhostDisplacer p : pgd) {
                            p.move(Direction.LEFT);
                        }
                        break;
                    case S:
                        pacManDisplacer.move(Direction.DOWN);
                        for (PinkGhostDisplacer p : pgd) {
                            p.move(Direction.DOWN);
                        }
                        break;
                    case D:
                        pacManDisplacer.move(Direction.RIGHT);
                        for (PinkGhostDisplacer p : pgd) {
                            p.move(Direction.RIGHT);
                        }
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

            isGameLaunched = true;
        } catch (Exception e) {
            //Afficher un message d'erreur si la map n'arrive pas à charger.
            e.printStackTrace();
        }
    }

    private void stopGame() {
        for (Looper looper : loopers) {
            looper.stop();
        }
        isGameLaunched = false;
    }
}
