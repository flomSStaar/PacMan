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
import model.Config;
import model.Score;
import model.SpriteManager;
import model.World;
import model.displacer.PacManDisplacer;
import model.entity.BaseEntity;
import model.utils.Direction;

import java.io.IOException;
import java.util.List;

public class Game {
    private final Stage stage;
    private Pane pane;
    private World world;
    private Score score = new Score();

    private boolean isGameLaunched = false;

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
            pane = (Pane) borderPane.getCenter();
            Scene scene = new Scene(borderPane);

            world = new World(entities, new SpriteManager(pane), score);
            world.loadWorld();
            world.attachGame(this);

            Text scoreText = new Text(10, 40, score.getScore());
            scoreText.textProperty().bind(score.scoreProperty());
            scoreText.setFill(Color.WHITE);
            scoreText.setFont(Font.loadFont(getClass().getResourceAsStream("/font/emulogic.ttf"), 15));
            borderPane.setTop(scoreText);

            world.startThread();
            stage.addEventHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);
            stage.setScene(scene);
            stage.show();

            isGameLaunched = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopGame() {
        if (isGameLaunched) {
            stage.removeEventHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);
            world.stopThread();
            world.clearWorld();
            world = null;
            score.reset();
            isGameLaunched = false;
        }
    }

    private void onKeyPressed(KeyEvent event) {
        try {
            if (world == null)
                return;
            PacManDisplacer pacManDisplacer = world.getPacManDisplacer();
            switch (event.getCode()) {
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
                    world.canPacManEatCandy(true);
                    break;
                case E:
                    world.canPacManEatCandy(false);
                    break;
                case ESCAPE:
                    try {
                        home();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gameOver() {
        stopGame();
        try {
            Thread.sleep(1000);
            home();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void levelUp() {
        try {
            //Arret de l'ancien monde
            world.clearWorld();

            //Création du nouveau monde
            List<BaseEntity> entities = new MapLoader().load();
            SpriteManager spriteManager = world.getSpriteManager();
            spriteManager.reinit();
            world = new World(entities, spriteManager, score);
            world.loadWorld();
            world.attachGame(this);
            world.startThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
