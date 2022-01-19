package model.io.saver;

import model.utils.Config;
import model.utils.PlayerScore;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class ScoreSaver implements Saver {
    @Override
    public void save(Object object) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(Config.SCORE_PATH))) {
            List<PlayerScore> scores = (List<PlayerScore>) object;
            stream.writeObject(scores);
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }
}
