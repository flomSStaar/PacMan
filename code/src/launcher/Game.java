package launcher;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
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
import model.eater.GhostEater;
import model.eater.PacManEater;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.BlueGhost;
import model.entity.ghost.Ghost;
import model.entity.ghost.PinkGhost;
import model.entity.ghost.RedGhost;
import model.loop.AnimationLooper;
import model.loop.Looper;
import model.utils.Direction;
import model.utils.GameOverObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game implements GameOverObserver {
    private final Stage stage;
    private World world;

    private boolean isGameLaunched = false;
    private final List<Looper> loopers = new ArrayList<>();

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
        Parent p = FXMLLoader.load(getClass().getResource("/fxml/mainView.fxml"));
        Scene scene = new Scene(p);
        this.stage.setScene(scene);
    }

    public void close() {
        if (isGameLaunched) {
            stopGame();
        }
        Platform.exit();
        System.exit(0);
    }

    public void startGame() {
        if (isGameLaunched) {
            stopGame();
        }
        try {
            List<BaseEntity> entities = new MapLoader().load();
            //Vue
            BorderPane borderPane = FXMLLoader.load(getClass().getResource("/fxml/gameView.fxml"));
            Pane pane = (Pane) borderPane.getCenter();
            Scene scene = new Scene(borderPane);

            world = new World(entities);
            world.loadWorld();
            PacMan pacMan = world.getPacMan();

            SpriteManager spriteManager = new SpriteManager(pane);
            spriteManager.addAllSprite(entities);

            Text scoreText = new Text(10, 40, world.getScore().getScore());
            scoreText.textProperty().bind(world.getScore().scoreProperty());
            scoreText.setFill(Color.WHITE);
            scoreText.setFont(Font.loadFont(getClass().getResourceAsStream("/font/emulogic.ttf"), 15));
            borderPane.setTop(scoreText);

            //Vue
            PacManAnimator pacManAnimator = new PacManAnimator(spriteManager.getImageView(pacMan), SpriteManager.getPacManSprite());
            AnimationLooper animationLooper = new AnimationLooper();

            PacManDisplacer pacManDisplacer = world.getPacManDisplacer();
            CandyEater candyEater = world.getCandyEater();
            PacManEater pacManEater = world.getPacManEater();
            GhostEater ghostEater = world.getGhostEater();

            //Vue
            pacManDisplacer.attach(pacManAnimator);
            ghostEater.attach(spriteManager);
            candyEater.attach(spriteManager);
            animationLooper.attach(pacManAnimator);
            loopers.add(animationLooper);

            world.attachGameOver(this);

            List<GhostDisplacer> ghostDisplacers = new ArrayList<>();
            for (Ghost ghost : world.getGhosts()) {
                GhostAnimator ghostAnimator;
                GhostDisplacer ghostDisplacer = world.getGhostDisplacer(ghost);
                if (ghost instanceof RedGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getRedSprite());
                } else if (ghost instanceof BlueGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getBlueSprite());
                } else if (ghost instanceof PinkGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getPingSprite());
                    ghostDisplacers.add(ghostDisplacer);
                } else {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getOrangeSprite());
                }
                ghostDisplacer.attach(ghostAnimator);
                animationLooper.attach(ghostAnimator);
            }
            scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                switch (key.getCode()) {
                    case Z:
                        pacManDisplacer.move(Direction.UP);
                        for (GhostDisplacer ghostDisplacer : ghostDisplacers) {
                            ghostDisplacer.move(Direction.UP);
                        }
                        break;
                    case Q:
                        pacManDisplacer.move(Direction.LEFT);
                        for (GhostDisplacer ghostDisplacer : ghostDisplacers) {
                            ghostDisplacer.move(Direction.LEFT);
                        }
                        break;
                    case S:
                        pacManDisplacer.move(Direction.DOWN);
                        for (GhostDisplacer ghostDisplacer : ghostDisplacers) {
                            ghostDisplacer.move(Direction.DOWN);
                        }
                        break;
                    case D:
                        pacManDisplacer.move(Direction.RIGHT);
                        for (GhostDisplacer ghostDisplacer : ghostDisplacers) {
                            ghostDisplacer.move(Direction.RIGHT);
                        }
                        break;
//                    case C:
//                        pacmanMovementLooper.setMillis(10);
//                        ghostMovementLooper.setMillis(20);
//                        pacManEater.detach(gameOver);
//                        pacManDisplacer.attach(ghostEater);
//                        break;
//                    case X:
//                        pacmanMovementLooper.setMillis(15);
//                        ghostMovementLooper.setMillis(15);
//                        pacManEater.attach(gameOver);
//                        pacManDisplacer.detach(ghostEater);
//                        break;
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

            Thread animationThread = new Thread(animationLooper, "GameThreadAnimation");
            world.startThread();
            animationThread.start();

            stage.setScene(scene);
            stage.show();

            isGameLaunched = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopGame() {
        world.clearWorld();
        for (Looper looper : loopers) {
            looper.stop();
        }
        isGameLaunched = false;
    }

    @Override
    public void onGameOver() {
        stopGame();
        try {
            Thread.sleep(1000);
            home();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
