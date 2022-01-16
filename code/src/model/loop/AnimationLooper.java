package model.loop;

import model.Config;

public class AnimationLooper extends Looper {
    public AnimationLooper() {
        this("Animation Looper");
    }

    public AnimationLooper(String name) {
        super(name);
        super.millis = Config.ANIMATION_LOOP;
    }
}