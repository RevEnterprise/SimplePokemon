import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    private Clip clip;
    private float currentVolume = 0.5f;

    public void playMusic(String filePath) {
        try {
            stopMusic();

            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                
                setVolume(currentVolume);

                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } else {
                System.out.println("File lagu tidak ditemukan: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSE(String filePath) {
        try {
            File soundPath = new File(filePath);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip sfxClip = AudioSystem.getClip();
                sfxClip.open(audioInput);
                
                setVolumeForClip(sfxClip, currentVolume);

                sfxClip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    public void setVolume(float volume) {
        this.currentVolume = volume;
        
        if (clip != null && clip.isOpen()) {
            setVolumeForClip(clip, volume);
        }
    }

    private void setVolumeForClip(Clip c, float vol) {
        try {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            
            float dB = (float) (Math.log(vol <= 0 ? 0.0001 : vol) / Math.log(10.0) * 20.0);
            
            gainControl.setValue(dB);
        } catch (IllegalArgumentException e) {
        }
    }

    public float getVolume() {
        return currentVolume;
    }
}
