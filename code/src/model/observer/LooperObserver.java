package model.observer;

public interface LooperObserver {
    /**
     * Notifie les observateurs lorsqu'un tour de boucle a été efféctué
     */
    void onLoop();
}
