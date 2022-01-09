package model.loop;

import javafx.application.Platform;

public class AnimationLooper extends Looper {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                Platform.runLater(() -> {
                    notifyObject();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}