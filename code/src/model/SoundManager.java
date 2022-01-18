package model;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {

    private MediaPlayer music = new MediaPlayer(new Media(getClass().getResource("/sound/Music.wav").toExternalForm()));
    private MediaPlayer pacmanMusic = new MediaPlayer(new Media(getClass().getResource("/sound/pacman_intermission.wav").toExternalForm()));
    private AudioClip eatCandy = new AudioClip(getClass().getResource("/sound/pacman_chomp.wav").toExternalForm());
    private AudioClip eatGhost = new AudioClip(getClass().getResource("/sound/pacman_eatghost.wav").toExternalForm());
    private AudioClip deathPacman = new AudioClip(getClass().getResource("/sound/pacman_death.wav").toExternalForm());

    public SoundManager()
    {
        music.setVolume(0.5);
    }

    public void PlayMusicPacman()
    {
        pacmanMusic.setAutoPlay(true);
    }

    public void StopMusicPacman()
    {
        pacmanMusic.setAutoPlay(false);
        pacmanMusic.stop();
    }

    public void PlayMusic()
    {
        music.setAutoPlay(true);
    }

    public void StopMusic()
    {
        music.setAutoPlay(false);
        music.stop();
    }

    public void EatCandy()
    {
        eatCandy.play();
    }

    public void EatGhost()
    {
        eatGhost.play();
    }

    public void DeathPacman()
    {
        deathPacman.play();
    }

}
