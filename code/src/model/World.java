package model;

import model.entity.BaseEntity;
import model.entity.Ghost;
import model.entity.PacMan;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<BaseEntity> entities;

    public World(List<BaseEntity> entities) {
        this.entities = entities;
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

    public void Remove(BaseEntity e){entities.remove(e);}
}
