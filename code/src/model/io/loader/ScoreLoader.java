package model.io.loader;

import model.utils.Config;
import model.utils.PlayerScore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ScoreLoader implements Loader {
    public List<PlayerScore> load() {
        List<PlayerScore> scores = new ArrayList<>();
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(Config.SCORE_PATH))) {
            scores = (List<PlayerScore>) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
