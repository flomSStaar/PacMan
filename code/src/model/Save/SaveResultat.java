package model.Save;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import model.utils.Resultat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveResultat {
    public void SaveScore(ObservableList<Resultat> allscore) {

        try {
            FileOutputStream fos = new FileOutputStream("Resultats.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Resultat res : allscore) {
                    oos.writeObject(res);
            }
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
