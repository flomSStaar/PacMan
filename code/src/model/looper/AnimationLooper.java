package model.looper;

import model.utils.Config;

public class AnimationLooper extends Looper {
    /**
     * Cree une instance d'AnimationLooper
     */
    public AnimationLooper() {
        this("Animation Looper");
    }

    /**
     * Cree une instance d'AnimationLooper
     *
     * @param name Nom du Looper
     */
    public AnimationLooper(String name) {
        super(name);
        setMillis(Config.ANIMATION_LOOP);
    }
}