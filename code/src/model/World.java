package model;

import launcher.Game;
import model.animator.GhostAnimator;
import model.animator.PacManAnimator;
import model.displacer.*;
import model.eater.CandyEater;
import model.eater.GhostEater;
import model.eater.PacManEater;
import model.entity.BaseEntity;
import model.entity.Candy;
import model.entity.PacMan;
import model.entity.SuperCandy;
import model.entity.ghost.BlueGhost;
import model.entity.ghost.Ghost;
import model.entity.ghost.PinkGhost;
import model.entity.ghost.RedGhost;
import model.loop.AnimationLooper;
import model.loop.Looper;
import model.loop.MovementLooper;
import model.utils.EatObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World implements EatObserver {
    private Game game;
    private SpriteManager spriteManager;
    private Score score;

    private List<BaseEntity> entities;
    private Map<BaseEntity, BaseDisplacer> entityDisplacerMap = new HashMap<>();

    private CandyEater candyEater;
    private GhostEater ghostEater;
    private PacManEater pacManEater;

    private PacManAnimator pacManAnimator;
    private List<GhostAnimator> ghostAnimators = new ArrayList<>();

    private AnimationLooper animationLooper;
    private MovementLooper pacmanMovementLooper;
    private MovementLooper ghostMovementLooper;
    private List<Looper> loopers = new ArrayList<>();

    private boolean isLoad = false;
    private boolean isThreadStart = false;

    public World(List<BaseEntity> entities, SpriteManager spriteManager, Score score) {
        this.entities = entities;
        this.spriteManager = spriteManager;
        spriteManager.addAllSprite(entities);
        this.score = score;
    }

    public void loadWorld() {
        if (!isLoad) {
            initLooper();
            initEater();
            initPacMan();
            initGhosts();
            isLoad = true;
        }
    }

    public void clearWorld() {
        if (isLoad) {
            entities.clear();
            stopThread();
            isLoad = false;
        }
    }

    public void startThread() {
        if (isLoad && !isThreadStart) {
            for (Looper looper : loopers) {
                new Thread(looper, looper.getName()).start();
            }
            isThreadStart = true;
        }
    }

    public void stopThread() {
        for (Looper looper : loopers) {
            looper.stop();
        }
    }

    private void initPacMan() {
        PacMan pacMan = getPacMan();
        PacManDisplacer pacManDisplacer = new PacManDisplacer(entities, pacMan);
        pacManAnimator = new PacManAnimator(spriteManager.getImageView(getPacMan()), SpriteManager.getPacManSprite());
        pacManDisplacer.attach(candyEater);
        pacManDisplacer.attach(ghostEater);
        pacManDisplacer.attach(pacManAnimator);
        entityDisplacerMap.put(pacMan, pacManDisplacer);
        pacmanMovementLooper.attach(pacManDisplacer);
        animationLooper.attach(pacManAnimator);
    }

    private void initGhosts() {
        PacMan pacMan = getPacMan();
        for (Ghost ghost : getGhosts()) {
            GhostAnimator ghostAnimator;
            GhostDisplacer ghostDisplacer;
            if (ghost instanceof RedGhost) {
                ghostDisplacer = new RedGhostDisplacer(ghost, pacMan, entities);
                ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getRedSprite());
            } else if (ghost instanceof BlueGhost) {
                ghostDisplacer = new BlueGhostDisplacer(ghost, pacMan, entities);
                ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getBlueSprite());
            } else if (ghost instanceof PinkGhost) {
                ghostDisplacer = new PinkGhostDisplacer(ghost, pacMan, entities);
                ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getPingSprite());
                getPacManDisplacer().attach((PinkGhostDisplacer) ghostDisplacer);
            } else {
                ghostDisplacer = new OrangeGhostDisplacer(ghost, pacMan, entities);
                ghostAnimator = new GhostAnimator(spriteManager.getImageView(ghost), SpriteManager.getOrangeSprite());
            }
            entityDisplacerMap.put(ghost, ghostDisplacer);
            ghostMovementLooper.attach(ghostDisplacer);
            ghostDisplacer.attach(ghostAnimator);
            ghostDisplacer.attach(pacManEater);
            animationLooper.attach(ghostAnimator);
            ghostAnimators.add(ghostAnimator);
        }
    }

    private void initEater() {
        candyEater = new CandyEater(entities);
        pacManEater = new PacManEater(entities);
        ghostEater = new GhostEater(entities);
        candyEater.attach(spriteManager);
        candyEater.attach(score);
        candyEater.attach(this);
        ghostEater.attach(spriteManager);
        ghostEater.attach(score);
        ghostEater.attach(this);
        pacManEater.attach(this);
        pacManEater.setActive(true);
        ghostEater.setActive(false);
    }

    private void initLooper() {
        loopers.clear();
        animationLooper = new AnimationLooper();
        pacmanMovementLooper = new MovementLooper("PacManMovementLooper");
        ghostMovementLooper = new MovementLooper("GhostMovementLooper");
        loopers.add(animationLooper);
        loopers.add(pacmanMovementLooper);
        loopers.add(ghostMovementLooper);
    }

    public void canPacManEatCandy(boolean canEatCandy) {
        if (canEatCandy) {
            candyEater.attach(spriteManager);
            candyEater.attach(this);
            candyEater.attach(score);
        } else {
            candyEater.detach(spriteManager);
            candyEater.detach(this);
            candyEater.detach(score);
        }
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

    public List<BaseEntity> getEntities() {
        return entities;
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public void attachGame(Game game) {
        this.game = game;
    }

    private void removeEntity(BaseEntity entity) {
        entities.remove(entity);
    }

    @Override
    public void onEat(BaseEntity entity) {
        if (entity instanceof PacMan) {
            game.gameOver();
            return;
        } else if (entity instanceof SuperCandy) {
            new Thread(() -> {
                try {
                    pacManEater.setActive(false);
                    ghostEater.setActive(true);
                    pacmanMovementLooper.setMillis(Config.FAST_MOVEMENT_LOOP);
                    ghostMovementLooper.setMillis(Config.SLOW_MOVEMENT_LOOP);
                    Thread.sleep(Config.PACMAN_POWER_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    pacManEater.setActive(true);
                    ghostEater.setActive(false);
                    pacmanMovementLooper.setMillis(Config.DEFAULT_MOVEMENT_LOOP);
                    ghostMovementLooper.setMillis(Config.DEFAULT_MOVEMENT_LOOP);
                }
            },"PacManPower").start();
        } else if (entity instanceof Ghost) {
            GhostDisplacer ghostDisplacer = (GhostDisplacer) entityDisplacerMap.remove(entity);
            ghostMovementLooper.detach(ghostDisplacer);
            if (ghostDisplacer instanceof PinkGhostDisplacer) {
                getPacManDisplacer().detach((PinkGhostDisplacer) ghostDisplacer);
            }
        }
        removeEntity(entity);
        for (BaseEntity e : entities) {
            if (e instanceof Candy || e instanceof SuperCandy) {
                return;
            }
        }
        score.increase(Config.LEVEL_UP);
        game.levelUp();
    }
}
