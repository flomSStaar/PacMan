package model.looper;

import model.utils.Config;

public class MovementLooper extends Looper {
    /**
     * Cree une instance de MovementLooper
     */
    public MovementLooper() {
        this("Movement Looper");
    }

    /**
     * Cree une instance de MovementLooper
     *
     * @param name Nom du looper
     */
    public MovementLooper(String name) {
        super(name);
        setMillis(Config.DEFAULT_MOVEMENT_LOOP);
    }
}
