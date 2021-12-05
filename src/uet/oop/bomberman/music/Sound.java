package uet.oop.bomberman.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    public static final String enemyDead = "enemy_dead";
    public static final String Explosion = "explosion";
    public static final String putBomb = "place_bomb";
    public static final String playerDead = "player_dead";
    public static final String takeItem  = "power_up";

    private Clip clip;

    public Sound(String fileName) {
        String path = "res/sound/" + fileName + ".wav";
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
