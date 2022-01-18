package model;

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
import model.displacer.PacManDisplacer;
import model.entity.BaseEntity;
import model.io.loader.MapEntityLoader;
import model.utils.Direction;

import java.io.IOException;
import java.util.List;

/**
 * Cette classe permet de gérer la naviguation de l'application.
 */
public class Game {
    private final Stage stage;
    private Pane pane;
    private World world;
    private final Score score = new Score();

    private boolean isGameLaunched = false;

    /**
     * Créer une nouvelle instance de Game
     *
     * @param stage Stage de la vue
     * @throws IOException
     */
    public Game(Stage stage) throws IOException {
        this.stage = stage;
        home();
        this.stage.show();
    }

    /**
     * Effectue la naviguation vers la vue principale
     *
     * @throws IOException Exception lancée si le fichier de vue à un problème ou n'arrive pas à être chargée
     */
    public void home() throws IOException {
        if (isGameLaunched) {
            stopGame();
        }
        Parent p = FXMLLoader.load(getClass().getResource("/fxml/mainView.fxml"));
        Scene scene = new Scene(p);
        this.stage.setScene(scene);
    }

    /**
     * Effectue la naviguation vers la vue de score
     *
     * @throws IOException Exception lancée si le fichier de vue à un problème ou n'arrive pas à être chargée
     */
    public void score() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/fxml/scoreView.fxml"));
        Scene scene = new Scene(p);
        this.stage.setScene(scene);
    }

    /**
     * Ferme l'application
     */
    public void close() {
        if (isGameLaunched) {
            stopGame();
        }
        Platform.exit();
        System.exit(0);
    }

    /**
     * Lance la partie de PacMan
     */
    public void startGame() {
        if (isGameLaunched) {
            stopGame();
        }
        try {
            List<BaseEntity> entities = new MapEntityLoader().load();
            BorderPane borderPane = FXMLLoader.load(getClass().getResource("/fxml/gameView.fxml"));
            pane = (Pane) borderPane.getCenter();
            Scene scene = new Scene(borderPane);

            score.reset();
            world = new World(this, entities, new SpriteManager(pane), score);
            world.loadWorld();

            Text scoreText = new Text(10, 40, score.getTextScore());
            scoreText.textProperty().bind(score.textScoreProperty());
            scoreText.setFill(Color.WHITE);
            scoreText.setFont(Font.loadFont(getClass().getResourceAsStream("/font/emulogic.ttf"), 15));
            borderPane.setTop(scoreText);

            world.startWorld();
            stage.addEventHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);
            stage.setScene(scene);
            stage.show();

            isGameLaunched = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stoppe le jeu en cours.
     */
    private void stopGame() {
        if (isGameLaunched) {
            stage.removeEventHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);
            world.stopWorld();
            world.clearWorld();
            world = null;
            isGameLaunched = false;
        }
    }

    /**
     * Effectue les actions lorsqu'une touche est appuyé.
     *
     * @param event Evenement déclenché
     */
    private void onKeyPressed(KeyEvent event) {
        try {
            if (world == null)
                return;
            PacManDisplacer pacManDisplacer = world.getPacManDisplacer();
            switch (event.getCode()) {
                case Z:
                    pacManDisplacer.setDirection(Direction.UP);
                    break;
                case Q:
                    pacManDisplacer.setDirection(Direction.LEFT);
                    break;
                case S:
                    pacManDisplacer.setDirection(Direction.DOWN);
                    break;
                case D:
                    pacManDisplacer.setDirection(Direction.RIGHT);
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

    /**
     * Termine la partie en cours.
     */
    public void gameOver() {
        if (isGameLaunched) {
            stopGame();
            try {
                Thread.sleep(1000);
                score();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Augmente le niveau du jeu en cours.
     */
    public void levelUp() {
        if (isGameLaunched) {
            try {
                //Arret de l'ancien monde
                world.clearWorld();

                //Création du nouveau monde
                List<BaseEntity> entities = new MapEntityLoader().load();
                SpriteManager spriteManager = world.getSpriteManager();
                spriteManager.reset();
                world = new World(this, entities, spriteManager, score);
                world.loadWorld();
                world.startWorld();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Récupère l'instance de score en cours
     *
     * @return Score en cours
     */
    public Score getScore() {
        return score;
    }
}
