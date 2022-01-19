package model;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private final MediaPlayer music = new MediaPlayer(new Media(getClass().getResource("/sound/Music.wav").toExternalForm()));
    private final MediaPlayer pacmanMusic = new MediaPlayer(new Media(getClass().getResource("/sound/pacman_intermission.wav").toExternalForm()));
    private final AudioClip eatCandy = new AudioClip(getClass().getResource("/sound/pacman_chomp.wav").toExternalForm());
    private final AudioClip eatGhost = new AudioClip(getClass().getResource("/sound/pacman_eatghost.wav").toExternalForm());
    private final AudioClip deathPacman = new AudioClip(getClass().getResource("/sound/pacman_death.wav").toExternalForm());

    public SoundManager() {
        music.setVolume(0.5);
    }

    public void playMusicPacman() {
        pacmanMusic.setAutoPlay(true);
    }

    public void stopMusicPacman() {
        pacmanMusic.setAutoPlay(false);
        pacmanMusic.stop();
    }

    public void playMusic() {
        music.setAutoPlay(true);
    }

    public void stopMusic() {
        music.setAutoPlay(false);
        music.stop();
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
