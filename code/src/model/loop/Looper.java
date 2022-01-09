package model.loop;

import model.utils.LooperObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class Looper implements Runnable {
    protected List<LooperObserver> observers = new ArrayList<>();

    /**
     * Ajoute un observateur à la liste d'observateurs du boucleur
     * @param o
     */
    public void attach(LooperObserver o){
        observers.add(o);
    }

    /**
     * Supprime un observateur à la liste d'observateurs du boucleur
     * @param o
     */
    public void detach(LooperObserver o){
        observers.remove(o);
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
