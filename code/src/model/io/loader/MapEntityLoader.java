package model.io.loader;

import model.entity.*;
import model.entity.ghost.BlueGhost;
import model.entity.ghost.OrangeGhost;
import model.entity.ghost.PinkGhost;
import model.entity.ghost.RedGhost;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Chargeur de carte.
 */
public class MapEntityLoader implements Loader {
    /**
     * Charge les entites a partir d'un fichier
     *
     * @return Entites chargees
     */
    public List<BaseEntity> load() {
        List<BaseEntity> entities = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(getClass().getResource("/text/map.txt").getFile()))) {
            int x = 0, y = 0, character;
            while ((character = br.read()) != -1) {
                switch ((char) character) {
                    case '\n' -> {
                        x = 0;
                        y += 15;
                    }
                    case '0' -> x += 15;
                    case '1' -> {
                        entities.add(new Wall(x, y, 15, 15));
                        x += 15;
                    }
                    case '2' -> {
                        entities.add(new PacMan(x + 1, y + 1, 13, 13));
                        x += 15;
                    }
                    case '3' -> {
                        entities.add(new Candy(x + 7, y + 7, 2, 2));
                        x += 15;
                    }
                    case '4' -> {
                        entities.add(new SuperCandy(x + 3, y + 3, 8, 8));
                        x += 15;
                    }
                    case '5' -> {
                        entities.add(new RedGhost(x + 1, y + 1, 13, 13));
                        x += 15;
                    }
                    case '6' -> {
                        entities.add(new BlueGhost(x + 1, y + 1, 13, 13));
                        x += 15;
                    }
                    case '7' -> {
                        entities.add(new OrangeGhost(x + 1, y + 1, 13, 13));
                        x += 15;
                    }
                    case '8' -> {
                        entities.add(new PinkGhost(x + 1, y + 1, 13, 13));
                        x += 15;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entities;
    }
}
