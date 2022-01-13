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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.SpriteManager;
import model.World;
import model.animator.GhostAnimator;
import model.animator.PacManAnimator;
import model.displacer.*;
import model.eater.CandyEater;
import model.entity.*;
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
            scoreText.textProperty().bind(world.getScore().scoreProperty());
            scoreText.setFill(Color.WHITE);
            scoreText.setFont(Font.loadFont(getClass().getResourceAsStream("/font/emulogic.ttf"), 15));
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

            Image[] imgR = new Image[8];
            imgR[0] = new Image("/image/RedGhostUp0.png");
            imgR[1] = new Image("/image/RedGhostUp1.png");
            imgR[2] = new Image("/image/RedGhostLeft0.png");
            imgR[3] = new Image("/image/RedGhostLeft1.png");
            imgR[4] = new Image("/image/RedGhostDown0.png");
            imgR[5] = new Image("/image/RedGhostDown1.png");
            imgR[6] = new Image("/image/RedGhostRight0.png");
            imgR[7] = new Image("/image/RedGhostRight1.png");

            Image[] imgP = new Image[8];
            imgP[0] = new Image("/image/PinkGhostUp0.png");
            imgP[1] = new Image("/image/PinkGhostUp1.png");
            imgP[2] = new Image("/image/PinkGhostLeft0.png");
            imgP[3] = new Image("/image/PinkGhostLeft1.png");
            imgP[4] = new Image("/image/PinkGhostDown0.png");
            imgP[5] = new Image("/image/PinkGhostDown1.png");
            imgP[6] = new Image("/image/PinkGhostRight0.png");
            imgP[7] = new Image("/image/PinkGhostRight1.png");

            Image[] imgB = new Image[8];
            imgB[0] = new Image("/image/BlueGhostUp0.png");
            imgB[1] = new Image("/image/BlueGhostUp1.png");
            imgB[2] = new Image("/image/BlueGhostLeft0.png");
            imgB[3] = new Image("/image/BlueGhostLeft1.png");
            imgB[4] = new Image("/image/BlueGhostDown0.png");
            imgB[5] = new Image("/image/BlueGhostDown1.png");
            imgB[6] = new Image("/image/BlueGhostRight0.png");
            imgB[7] = new Image("/image/BlueGhostRight1.png");

            Image[] imgO = new Image[8];
            imgO[0] = new Image("/image/OrangeGhostUp0.png");
            imgO[1] = new Image("/image/OrangeGhostUp1.png");
            imgO[2] = new Image("/image/OrangeGhostLeft0.png");
            imgO[3] = new Image("/image/OrangeGhostLeft1.png");
            imgO[4] = new Image("/image/OrangeGhostDown0.png");
            imgO[5] = new Image("/image/OrangeGhostDown1.png");
            imgO[6] = new Image("/image/OrangeGhostRight0.png");
            imgO[7] = new Image("/image/OrangeGhostRight1.png");

            List<PinkGhostDisplacer> pgd = new ArrayList<>();
            for (Ghost ghost : world.getGhosts()) {
                GhostAnimator ghostAnimator;
                if (ghost instanceof RedGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), imgR);
                    RedGhostDisplacer ghostDisplacer = new RedGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                    ghostDisplacer.attach(ghostAnimator);
                    animationLooper.attach(ghostAnimator);
                    movementLooper.attach(ghostDisplacer);
                } else if (ghost instanceof BlueGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), imgB);
                    BlueGhostDisplacer ghostDisplacer = new BlueGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                    ghostDisplacer.attach(ghostAnimator);
                    animationLooper.attach(ghostAnimator);
                    movementLooper.attach(ghostDisplacer);
                } else if (ghost instanceof PinkGhost) {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), imgP);
                    PinkGhostDisplacer ghostDisplacer = new PinkGhostDisplacer(ghost, world.getPacMan(), world.getEntities());
                    ghostDisplacer.attach(ghostAnimator);
                    animationLooper.attach(ghostAnimator);
                    movementLooper.attach(ghostDisplacer);
                    pgd.add(ghostDisplacer);
                } else {
                    ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), imgO);
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
                        for(PinkGhostDisplacer p : pgd)
                        {
                            p.move(Direction.UP);
                        }
                        break;
                    case Q:
                        pacManDisplacer.move(Direction.LEFT);
                        for(PinkGhostDisplacer p : pgd)
                        {
                            p.move(Direction.LEFT);
                        }
                        break;
                    case S:
                        pacManDisplacer.move(Direction.DOWN);
                        for(PinkGhostDisplacer p : pgd)
                        {
                            p.move(Direction.DOWN);
                        }
                        break;
                    case D:
                        pacManDisplacer.move(Direction.RIGHT);
                        for(PinkGhostDisplacer p : pgd)
                        {
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
        } catch (Exception e) {
            //Afficher un message d'erreur si la map n'arrive pas à charger.
            e.printStackTrace();
        }
    }
}
