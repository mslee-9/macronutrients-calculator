package ui;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffects {

    public SoundEffects() {
    }

    public static void playStartSound() {
        File sound = new File("./data/enterChime.wav");
        playSoundFile(sound);
    }

    public static void playExitSound() {
        File sound = new File("./data/exitBeep.wav");
        playSoundFile(sound);
    }

    private static void playSoundFile(File sound) {
        Clip c;
        try {
            c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(sound));
            c.start();
        } catch (Exception e) {
            System.out.println("no sound");
        }
    }
}
