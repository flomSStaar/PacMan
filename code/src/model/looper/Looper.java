package model.looper;

import javafx.application.Platform;
import model.observer.LooperObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class Looper implements Runnable {
    private final List<LooperObserver> observers = new ArrayList<>();
    private final String name;
    protected boolean canRun = true;
    protected int millis = 50;

    /**
     * Définit le constructeur d'un Looper.
     *
     * @param name Nom du Looper
     */
    public Looper(String name) {
        this.name = name;
    }

    /**
     * Ajoute un observateur à la liste d'observateurs du boucleur
     *
     * @param observer Observateur à attacher
     */
    public void attach(LooperObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Supprime un observateur à la liste d'observateurs du boucleur
     *
     * @param observer Observateur à détacher
     */
    public void detach(LooperObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifie tous les objets de la liste d'observateurs du boucleur
     */
    protected void notifyLoop() {
        for (LooperObserver o : observers) {
            o.onLoop();
        }
    }

    /**
     * Définit le Looper comme actif
     */
    public void start() {
        canRun = true;
    }

    /**
     * Définit le Looper comme inactif
     */
    public void stop() {
        canRun = false;
    }

    /**
     * Récupère le temps de bouclage
     *
     * @return Temps du boucleur
     */
    public int getMillis() {
        return millis;
    }

    /**
     * Modifie le temps de bouclage
     *
     * @param millis Temps du boucleur
     */
    public void setMillis(int millis) {
        if (millis > 0) {
            this.millis = millis;
        }
    }

    /**
     * Récupère le nom du boucleur
     *
     * @return Nom du boucleur
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le comportement lorsque le looper est utilisé dans un thread
     */
    @Override
    public void run() {
        while (canRun) {
            try {
                Thread.sleep(millis);
                Platform.runLater(this::notifyLoop);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
