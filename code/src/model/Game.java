package model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.displacer.PacManDisplacer;
import model.entity.BaseEntity;
import model.io.loader.MapEntityLoader;
import model.io.loader.ScoreLoader;
import model.io.saver.ScoreSaver;
import model.utils.Direction;
import model.utils.PlayerScore;

import java.io.IOException;
import java.util.List;

/**
 * Cette classe permet de gerer la naviguation de l'application.
 */
public class Game {
    private final Stage stage;
    private Pane pane;
    private World world;
    private final Score score = new Score();
    private ObservableList<PlayerScore> playerScores;

    private boolean isGameLaunched = false;

    /**
     * Cree une nouvelle instance de Game
     *
     * @param stage Stage de la vue
     * @throws IOException Exception lancee si la vue d'accueil n'arrive pas a charger
     */
    public Game(Stage stage) throws IOException {
        playerScores = FXCollections.observableArrayList(new ScoreLoader().load());
        this.stage = stage;
        home();
        this.stage.show();
    }

    /**
     * Effectue la naviguation vers la vue principale
     *
     * @throws IOException Exception lancee si le fichier de vue a un probleme ou n'arrive pas a etre chargee
     */
    public void home() throws IOException {
        if (isGameLaunched) {
            stopGame();
        }
        Parent p = FXMLLoader.load(getClass().getResource("/fxml/mainView.fxml"));
        Scene scene = new Scene(p);
        scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
        this.stage.setScene(scene);
        this.stage.setHeight(stage.getHeight());
        this.stage.show();
    }

    /**
     * Effectue la naviguation vers la vue de score
     *
     * @throws IOException Exception lancee si le fichier de vue a un probleme ou n'arrive pas a etre chargee
     */
    public void score() throws IOException {
        Parent p = FXMLLoader.load(getClass().getResource("/fxml/scoreView.fxml"));
        Scene scene = new Scene(p);
        scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
        this.stage.setScene(scene);
        this.stage.setHeight(stage.getHeight());
        this.stage.show();
    }

    /**
     * Ferme l'application
     */
    public void close() {
        if (isGameLaunched) {
            stopGame();
        }
        new ScoreSaver().save(playerScores.stream().toList());
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
            scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());

            world = new World(this, entities, new SpriteManager(pane), score);
            world.loadWorld();
            score.reset();

            world.startWorld();
            stage.addEventHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);
            stage.setScene(scene);
            this.stage.setHeight(stage.getHeight());
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
     * Effectue les actions lorsqu'une touche est appuye.
     *
     * @param event Evenement declenche
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
                Parent p = FXMLLoader.load(getClass().getResource("/fxml/gameOverView.fxml"));
                Scene scene = new Scene(p);
                scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
                this.stage.setScene(scene);
                this.stage.setHeight(stage.getHeight());
                this.stage.show();
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

                //Creation du nouveau monde
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
     * Recupere l'instance de score en cours
     *
     * @return Score en cours
     */
    public Score getScore() {
        return score;
    }

    /**
     * Recupere la liste des scores des joueurs
     *
     * @return Liste des scores des joueurs
     */
    public ObservableList<PlayerScore> getPlayerScores() {
        return playerScores;
    }

    /**
     * Ajoute le score d'un joueur a la liste des scores
     *
     * @param playerScore Score du joueur
     */
    public void addPlayerScore(PlayerScore playerScore) {
        playerScores.add(playerScore);
    }
}
