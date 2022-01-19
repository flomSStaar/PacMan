package model.observer;

public interface LooperObserver {
    /**
     * Notifie les observateurs lorsqu'un tour de boucle a ete effectue
     */
    void onLoop();
}
