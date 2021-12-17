package view;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.entity.PacMan;

public class VueMap {
    @FXML
    public Pane pane;

    @FXML
    public Text score;

    public void initialize() {

    }

    public void onKeyPressedPane(KeyEvent keyEvent) {
        PacMan p = new PacMan(0, 0, 10, 10);
        switch (keyEvent.getCode()) {
            case Z -> p.setY(p.getY() - 10);
            case S -> p.setY(p.getY() + 10);
            case Q -> p.setX(p.getX() - 10);
            case D -> p.setX(p.getX() + 10);
        }
    }
}
