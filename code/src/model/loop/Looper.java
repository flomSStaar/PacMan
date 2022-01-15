package model.loop;

import javafx.application.Platform;
import model.utils.LooperObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class Looper implements Runnable {
    private final List<LooperObserver> observers = new ArrayList<>();
    protected boolean run = true;
    protected int millis = 50;

    /**
     * Ajoute un observateur à la liste d'observateurs du boucleur
     *
     * @param observer
     */
    public void attach(LooperObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    /**
     * Supprime un observateur à la liste d'observateurs du boucleur
     *
     * @param observer
     */
    public void detach(LooperObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifie tous les objets de la liste d'observateurs du boucleur
     */
    public void notifyLoop() {
        for (LooperObserver o : observers) {
            o.onLoop();
        }
    }

    public void start() {
        run = true;
    }

    public void stop() {
        run = false;
    }

    public int getMillis() {
        return millis;
    }

    public void setMillis(int millis) {
        if (millis > 0) {
            this.millis = millis;
        }
    }

    @Override
    public void run() {
        while (run) {
            try {
                Thread.sleep(millis);
                Platform.runLater(() -> {
                    notifyLoop();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
