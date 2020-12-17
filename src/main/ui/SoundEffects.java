package ui;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

//Sound Effects class containing methods for playing wav files in the data folder
public class SoundEffects {

    File startSound = new File("./data/enterChime.wav");
    File exitBeep = new File("./data/exitBeep.wav");

    // EFFECTS: plays start sound
    public void playStartSound() {
        playSoundFile(startSound);
    }

    // EFFECTS: plays exit beep
    public void playExitSound() {
        playSoundFile(exitBeep);
    }

    // EFFECTS: plays given sound file
    private void playSoundFile(File sound) {
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