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
import model.GameOver;
import model.SpriteManager;
import model.World;
import model.animator.GhostAnimator;
import model.animator.PacManAnimator;
import model.displacer.*;
import model.eater.CandyEater;
import model.eater.PacManEater;
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
import model.utils.GameOverObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game implements GameOverObserver {
    private final Stage stage;
    private SpriteManager spriteManager;

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
            BorderPane borderPane = FXMLLoader.load(getClass().getResource("/fxml/gameView.fxml"));
            Pane pane = (Pane) borderPane.getCenter();
            Scene scene = new Scene(borderPane);
            World world = new World(entities);
            PacMan pacMan = world.getPacMan();
            spriteManager = new SpriteManager(pane);
            spriteManager.addAllSprite(entities);

            Text scoreText = new Text(10, 40, world.getScore().getScore());
            scoreText.textProperty().bind(world.getScore().scoreProperty());
            scoreText.setFill(Color.WHITE);
            scoreText.setFont(Font.loadFont(getClass().getResourceAsStream("/font/emulogic.ttf"), 15));
            borderPane.setTop(scoreText);

            PacManAnimator pacManAnimator = new PacManAnimator(spriteManager.getImageView(pacMan), SpriteManager.getPacManSprite());
            PacManDisplacer pacManDisplacer = new PacManDisplacer(entities, pacMan);
            MovementLooper movementLooper = new MovementLooper();
            AnimationLooper animationLooper = new AnimationLooper();
            CandyEater candyEater = new CandyEater(entities);
            PacManEater pacManEater = new PacManEater(entities);
            GameOver gameOver = new GameOver();

            pacManDisplacer.attach(pacManAnimator);
            pacManDisplacer.attach(candyEater);

            pacManEater.attach(gameOver);

            gameOver.attach(this);

            movementLooper.attach(pacManDisplacer);
            animationLooper.attach(pacManAnimator);
            loopers.add(movementLooper);
            loopers.add(animationLooper);

            candyEater.attach(spriteManager);
            candyEater.attach(world);
            candyEater.attach(world.getScore());

            List<GhostDisplacer> ghostDisplacers = new ArrayList<>();
            for (Ghost ghost : world.getGhosts()) {
                GhostAnimator ghostAnimator;
                GhostDisplacer ghostDisplacer;
                if (ghost instanceof RedGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getRedSprite());
                    ghostDisplacer = new RedGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                } else if (ghost instanceof BlueGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getBlueSprite());
                    ghostDisplacer = new BlueGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                } else if (ghost instanceof PinkGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getPingSprite());
                    ghostDisplacer = new PinkGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                    ghostDisplacers.add(ghostDisplacer);
                } else {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getOrangeSprite());
                    ghostDisplacer = new OrangeGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                }
                ghostDisplacer.attach(ghostAnimator);
                animationLooper.attach(ghostAnimator);
                movementLooper.attach(ghostDisplacer);
                ghostDisplacer.attach(pacManEater);
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

            Thread movementThread = new Thread(movementLooper, "GameThreadMove");
            movementThread.start();
            Thread animationThread = new Thread(animationLooper, "GameThreadAnimation");
            animationThread.start();

            stage.setScene(scene);
            stage.show();

            isGameLaunched = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopGame() {
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
