package model.loop;

import javafx.application.Platform;
import model.World;
import model.collider.EatCollider;
import model.entity.BaseEntity;
import model.utils.EntityObserver;

import java.util.ArrayList;
import java.util.List;

public class GameLooper extends Looper {

    private int score;
    private World world;
    protected List<EntityObserver> observers = new ArrayList<>();

    public GameLooper(int score, World world)
    {
        this.score = score;
        this.world = world;
    }

    @Override
    public void run() {
        EatCollider eatCollider = new EatCollider();
        while (true) {
            try {
                Thread.sleep(15);
                List<BaseEntity> l = eatCollider.getColliding(world.getEntities(), world.getPacMan(), world.getPacMan().getX(), world.getPacMan().getY());
                for(BaseEntity e : l)
                {
                    Platform.runLater(() -> {
                        notifyObject(e);
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isFinish(List<BaseEntity> l)
    {
        return true;
    }

    public void notifyObject(BaseEntity e){
        for (EntityObserver o : this.observers){
            o.update(e);
        }
    }

    public void attach(EntityObserver o){
        observers.add(o);
    }

    public void detach(EntityObserver o){
        observers.remove(o);
    }
}
