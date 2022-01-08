package view;

import javafx.event.ActionEvent;
import launcher.Launcher;

import java.io.IOException;

public class VueParametres {
    public void initialize(){

    }

    public void back(ActionEvent actionEvent) {
        try {
            Launcher.game.home();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void apply(ActionEvent actionEvent) {
        //Appliquer les paramètres qui ont été saisis
        try {
            Launcher.game.home();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
