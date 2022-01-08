package model.loop;

import model.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Looper implements Runnable {
    private List<Observer> observers = new ArrayList<>();

    /**
     * Ajoute un observateur à la liste d'observateurs du boucleur
     * @param o
     */
    public void attach(Observer o){
        observers.add(o);
    }

    /**
     * Supprime un observateur à la liste d'observateurs du boucleur
     * @param o
     */
    public void detach(Observer o){
        observers.remove(o);
    }

    /**
     * Notifie tous les objets de la liste d'observateurs du boucleur
     */
    public void notifyObject(){
        for (Observer o : observers){
            o.update();
        }
    }
}
