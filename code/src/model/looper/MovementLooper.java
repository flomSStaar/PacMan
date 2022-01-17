package model.looper;

import model.utils.Config;

public class MovementLooper extends Looper {
    /**
     * Créé une instance de MovementLooper
     */
    public MovementLooper() {
        this("Movement Looper");
    }

    /**
     * Créé une instance de MovementLooper
     *
     * @param name Nom du looper
     */
    public MovementLooper(String name) {
        super(name);
        super.millis = Config.DEFAULT_MOVEMENT_LOOP;
    }
}
