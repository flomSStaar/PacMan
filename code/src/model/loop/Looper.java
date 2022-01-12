package model.loop;

import model.utils.LooperObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class Looper implements Runnable {
    protected List<LooperObserver> observers = new ArrayList<>();

    /**
     * Ajoute un observateur à la liste d'observateurs du boucleur
     * @param observer
     */
    public void attach(LooperObserver observer){
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Supprime un observateur à la liste d'observateurs du boucleur
     * @param observer
     */
    public void detach(LooperObserver observer){
        observers.remove(observer);
    }

    /**
     * Notifie tous les objets de la liste d'observateurs du boucleur
     */
    public void notifyObject(){
        for (LooperObserver o : observers){
            o.onLoop();
        }
    }
}
