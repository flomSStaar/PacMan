package model.loop;

import javafx.application.Platform;

public class MovementLooper extends Looper {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(15);
                Platform.runLater(() -> {
                    notifyObject();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
