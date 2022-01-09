package model.loop;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import model.World;
import model.collider.EatCollider;
import model.entity.BaseEntity;
import model.entity.PacMan;
import model.utils.Observer;
import model.utils.ObserverEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLooper extends Looper {

    private int score;
    private World world;
    protected List<ObserverEntity> observers = new ArrayList<>();

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
        for (ObserverEntity o : this.observers){
            o.update(e);
        }
    }

    public void attach(ObserverEntity o){
        observers.add(o);
    }

    public void detach(ObserverEntity o){
        observers.remove(o);
    }
}
