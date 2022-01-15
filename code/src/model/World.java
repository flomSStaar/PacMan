package model;

import model.displacer.*;
import model.eater.CandyEater;
import model.eater.GhostEater;
import model.eater.PacManEater;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.entity.ghost.BlueGhost;
import model.entity.ghost.Ghost;
import model.entity.ghost.PinkGhost;
import model.entity.ghost.RedGhost;
import model.loop.MovementLooper;
import model.utils.EatObserver;
import model.utils.GameOverObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World implements EatObserver {
    private List<BaseEntity> entities;
    private Map<BaseEntity, BaseDisplacer> entityDisplacerMap = new HashMap<>();
    private Score score = new Score();
    private GameOver gameOver = new GameOver();
    private MovementLooper pacmanMovementLooper = new MovementLooper();
    private MovementLooper ghostMovementLooper = new MovementLooper();
    private CandyEater candyEater;
    private PacManEater pacManEater;
    private GhostEater ghostEater;

    private boolean isLoad = false;
    private boolean isThreadStart = false;

    public World(List<BaseEntity> entities) {
        this.entities = entities;
        candyEater = new CandyEater(entities);
        pacManEater = new PacManEater(entities);
        ghostEater = new GhostEater(entities);
    }

    public void loadWorld() {
        if (!isLoad) {
            PacMan pacMan = initPacMan();
            initGhosts(pacMan);
            initEater();
            isLoad = true;
        }
    }

    public void clearWorld() {
        if (isLoad) {
            entities.clear();
            score.reset();
            pacmanMovementLooper.stop();
            ghostMovementLooper.stop();
            isLoad = false;
        }
    }

    public void startThread() {
        if (isLoad && !isThreadStart) {
            Thread pacmanMovementThread = new Thread(pacmanMovementLooper, "GameThreadPacManMove");
            pacmanMovementThread.start();
            Thread ghostMovementThread = new Thread(ghostMovementLooper, "GameThreadGhostMove");
            ghostMovementThread.start();
            isThreadStart = true;
        }
    }

    private PacMan initPacMan() {
        PacMan pacMan = getPacMan();
        PacManDisplacer pacManDisplacer = new PacManDisplacer(entities, pacMan);
        pacManDisplacer.attach(candyEater);
        entityDisplacerMap.put(pacMan, pacManDisplacer);
        pacmanMovementLooper.attach(pacManDisplacer);

        return pacMan;
    }

    private void initGhosts(PacMan pacMan) {
        for (Ghost ghost : getGhosts()) {
            GhostDisplacer ghostDisplacer;
            if (ghost instanceof RedGhost) {
                ghostDisplacer = new RedGhostDisplacer(ghost, pacMan, entities);
            } else if (ghost instanceof BlueGhost) {
                ghostDisplacer = new BlueGhostDisplacer(ghost, pacMan, entities);
            } else if (ghost instanceof PinkGhost) {
                ghostDisplacer = new PinkGhostDisplacer(ghost, pacMan, entities);
            } else {
                ghostDisplacer = new OrangeGhostDisplacer(ghost, pacMan, entities);
            }
            entityDisplacerMap.put(ghost, ghostDisplacer);
            ghostMovementLooper.attach(ghostDisplacer);
            ghostDisplacer.attach(pacManEater);
        }
        ghostEater.attach(this);
        ghostEater.attach(score);
    }

    private void initEater() {
        candyEater.attach(this);
        candyEater.attach(score);
        pacManEater.attach(gameOver);
    }

    public CandyEater getCandyEater() {
        return candyEater;
    }

    public PacManEater getPacManEater() {
        return pacManEater;
    }

    public GhostEater getGhostEater() {
        return ghostEater;
    }

    public Score getScore() {
        return score;
    }

    public PacMan getPacMan() {
        for (BaseEntity entity : entities) {
            if (entity instanceof PacMan)
                return (PacMan) entity;
        }
        throw new IllegalStateException("PacMan is not defined");
    }

    public PacManDisplacer getPacManDisplacer() {
        return (PacManDisplacer) entityDisplacerMap.get(getPacMan());
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

    public GhostDisplacer getGhostDisplacer(Ghost ghost) {
        for (var set : entityDisplacerMap.entrySet()) {
            if (ghost == set.getKey())
                return (GhostDisplacer) set.getValue();
        }
        throw new IllegalArgumentException("Ghost not found");
    }

    public List<GhostDisplacer> getGhostDisplacers() {
        List<GhostDisplacer> ghostDisplacers = new ArrayList<>();
        for (BaseDisplacer displacer : entityDisplacerMap.values()) {
            if (displacer instanceof GhostDisplacer) {
                ghostDisplacers.add((GhostDisplacer) displacer);
            }
        }
        return ghostDisplacers;
    }

    public List<BaseEntity> getEntities() {
        return entities;
    }

    public void attachGameOver(GameOverObserver gameOverObserver) {
        gameOver.attach(gameOverObserver);
    }

    private void removeEntity(BaseEntity entity) {
        entities.remove(entity);
    }

    @Override
    public void onEat(BaseEntity entity) {
        removeEntity(entity);
    }
}
