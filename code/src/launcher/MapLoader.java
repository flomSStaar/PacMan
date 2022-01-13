package launcher;

import model.entity.*;

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
                    y += 15;
                    break;
                case '0':
                    x += 15;
                    break;
                case '1':
                    entities.add(new Wall(x, y, 15, 15));
                    x += 15;
                    break;
                case '2':
                    entities.add(new PacMan(x+1, y+1, 13, 13));
                    x += 15;
                    break;
                case '3':
                    entities.add(new Candy(x+7, y+7, 2, 2));
                    x += 15;
                    break;
                case '4':
                    entities.add(new SuperCandy(x+3, y+3, 8, 8));
                    x += 15;
                    break;
                case '5':
                    entities.add(new RedGhost(x, y, 13, 13));
                    x += 15;
                    break;
                case '6':
                    entities.add(new BlueGhost(x, y, 13, 13));
                    x += 15;
                    break;
                case '7':
                    entities.add(new OrangeGhost(x, y, 13, 13));
                    x += 15;
                    break;
                case '8':
                    entities.add(new PinkGhost(x, y, 13, 13));
                    x += 15;
                    break;
            }
        }
        return entities;
    }
}
