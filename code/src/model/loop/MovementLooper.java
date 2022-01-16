package model.loop;

import model.Config;

public class MovementLooper extends Looper {
    public MovementLooper() {
        this("Movement Looper");
    }

    public MovementLooper(String name){
        super(name);
        super.millis = Config.DEFAULT_MOVEMENT_LOOP;
    }
}
