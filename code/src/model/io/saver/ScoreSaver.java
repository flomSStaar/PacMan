package model.io.saver;

import model.utils.Config;
import model.utils.PlayerScore;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ScoreSaver implements Saver {
    /**
     * Formate la chaine de caractère à sauvegarder
     *
     * @param playerScore Score du joueur avec son pseudonyme
     * @return chaine formatée
     */
    private String formatOutput(PlayerScore playerScore) {
        StringBuilder string = new StringBuilder();
        string.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")));
        string.append(";");
        string.append(playerScore.getPseudo());
        string.append(";");
        string.append(playerScore.getScore());
        return string.toString();
    }

    @Override
    public void save(Object object) {
        if (!(object instanceof PlayerScore playerScore)) {
            throw new IllegalArgumentException("Object is not a PlayerScore");
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Config.SCORE_PATH, true))) {
            bw.write(formatOutput(playerScore));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(List<Object> objectList) {
        if (objectList == null) {
            throw new IllegalArgumentException("Object list is null");
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Config.SCORE_PATH, true))) {
            for (Object object : objectList) {
                if (!(object instanceof PlayerScore playerScore)) {
                    System.err.println("object cannot be cast to PlayerScore");
                    continue;
                }
                bw.write(formatOutput(playerScore));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
