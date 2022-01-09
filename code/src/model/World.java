package model;

import model.entity.BaseEntity;
import model.entity.Ghost;
import model.entity.PacMan;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<BaseEntity> entities;
    private Score score;

    public World(List<BaseEntity> entities) {
        this.entities = entities;
        score = new Score();
    }

    public void loadWorld() {
        //A utiliser pour charger le monde et enlever tout le code de la méthode launchGame de la classe Game
    }

    public PacMan getPacMan() {
        for (BaseEntity entity : entities) {
            if (entity instanceof PacMan)
                return (PacMan) entity;
        }
        return null;
    }

    public List<Ghost> getGhosts() {
        List<Ghost> ghosts = new ArrayList<>();
        for (BaseEntity entity : entities) {
            if (entity instanceof Ghost) {
                ghosts.add((Ghost) entity);
            }
        }
        return ghosts;
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }

    public void remove(BaseEntity e) {
        entities.remove(e);
    }
}
