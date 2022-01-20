package model;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Random;

public class SoundManager {

    private final MediaPlayer[] music = new MediaPlayer[5];
    private final MediaPlayer pacmanMusic = new MediaPlayer(new Media(getClass().getResource("/sound/pacman_intermission.wav").toExternalForm()));
    private final AudioClip eatCandy = new AudioClip(getClass().getResource("/sound/pacman_chomp.wav").toExternalForm());
    private final AudioClip eatGhost = new AudioClip(getClass().getResource("/sound/pacman_eatghost.wav").toExternalForm());
    private final AudioClip deathPacman = new AudioClip(getClass().getResource("/sound/pacman_death.wav").toExternalForm());
    private int numMusic = 0;
    private Random rand = new Random();

    public SoundManager() {
        for(int i = 0; i < 5; i++) {
            music[i] = new MediaPlayer(new Media(getClass().getResource("/sound/Music" + i + ".wav").toExternalForm()));
            music[i].setVolume(0.5);
        }
    }

    public void playMusicPacman() {
        pacmanMusic.setAutoPlay(true);
    }

    public void stopMusicPacman() {
        pacmanMusic.setAutoPlay(false);
        pacmanMusic.stop();
    }

    public void playMusic() {
        numMusic = rand.nextInt(5);
        music[numMusic].setAutoPlay(true);
    }

    public void stopMusic() {
        music[numMusic].setAutoPlay(false);
        music[numMusic].stop();
    }

    public void eatCandy() {
        eatCandy.play();
    }

    public void eatGhost() {
        eatGhost.play();
    }

    public void deathPacman() {
        deathPacman.play();
    }

}
