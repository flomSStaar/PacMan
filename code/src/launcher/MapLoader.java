package launcher;

import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.Wall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {
    public List<BaseEntity> load() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File(getClass().getResource("/text/map.txt").toURI())));
        List<BaseEntity> entities = new ArrayList<>();
        int x = 0, y = 0, character;
        while ((character = br.read()) != -1) {
            switch ((char) character) {
                case '\n':
                    x = 0;
                    y += 45;
                    break;
                case '0':
                    x += 45;
                    break;
                case '1':
                    entities.add(new Wall(x, y, 45, 45));
                    x += 45;
                    break;
                case '2':
                    entities.add(new PacMan(x, y, 35, 35));
                    x += 45;
                    break;
            }
        }
        return entities;
    }
}
