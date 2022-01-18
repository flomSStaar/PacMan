package model.Save;

import javafx.collections.ObservableList;
import model.utils.Resultat;

import java.io.*;

public class LoadResultat {
    public ObservableList<Resultat> LoadScore(ObservableList<Resultat> allRes) {
        try {
            FileInputStream fis = new FileInputStream("Resultats.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
                while (true) {
                    allRes.add((Resultat) ois.readObject());
                }
        }
        catch (ClassNotFoundException e)
        {
            return allRes;
        }
        catch (EOFException e)
        {
            return allRes;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
            return allRes;
    }
        return allRes;
    }
}
