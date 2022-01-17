package model.looper;

import model.utils.Config;

public class AnimationLooper extends Looper {
    /**
     * Créé une instance d'AnimationLooper
     */
    public AnimationLooper() {
        this("Animation Looper");
    }

    /**
     * Créé une instance d'AnimationLooper
     *
     * @param name Nom du Looper
     */
    public AnimationLooper(String name) {
        super(name);
        super.millis = Config.ANIMATION_LOOP;
    }
}