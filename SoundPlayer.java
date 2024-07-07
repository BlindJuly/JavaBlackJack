import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    // Method to play a sound file
    public static void playSound(String soundFileName) {
        try {
            // Open an audio input stream
            File soundFile = new File(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            // Get a sound clip resource
            Clip clip = AudioSystem.getClip();

            // Open the audio clip and load samples from the audio input stream
            clip.open(audioInputStream);

            // Start playing the sound clip
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}