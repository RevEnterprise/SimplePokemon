import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    private Clip clip;
    private float currentVolume = 0.5f; // Default volume 50% (Range 0.0 - 1.0)

    // 1. Method untuk memutar BGM (Looping)
    public void playMusic(String filePath) {
        try {
            stopMusic(); // Matikan lagu sebelumnya kalau ada

            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                
                // PENTING: Set volume dulu sebelum mulai
                setVolume(currentVolume);

                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop selamanya
                clip.start();
            } else {
                System.out.println("File lagu tidak ditemukan: " + filePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2. Method untuk memutar SFX / Sound Effect (Sekali main)
    public void playSE(String filePath) {
        try {
            File soundPath = new File(filePath);
            if (soundPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                Clip sfxClip = AudioSystem.getClip(); // Bikin clip baru biar tidak ganggu BGM
                sfxClip.open(audioInput);
                
                // Set volume SFX juga
                setVolumeForClip(sfxClip, currentVolume);

                sfxClip.start(); // Mainkan sekali saja (jangan di-loop)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. Method Stop
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    // 4. Method Atur Volume (Ini logika yang kita ambil dari MusicSystem)
    public void setVolume(float volume) {
        this.currentVolume = volume; // Simpan nilai (0.0 - 1.0)
        
        // Terapkan ke BGM yang sedang jalan
        if (clip != null && clip.isOpen()) {
            setVolumeForClip(clip, volume);
        }
    }

    // Helper khusus untuk rumus matematika volume
    private void setVolumeForClip(Clip c, float vol) {
        try {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            
            // Rumus Logaritmik (Agar perubahan suara terasa natural di telinga manusia)
            float dB = (float) (Math.log(vol <= 0 ? 0.0001 : vol) / Math.log(10.0) * 20.0);
            
            gainControl.setValue(dB);
        } catch (IllegalArgumentException e) {
            // Kadang error kalau hardware audio tidak mendukung gain control, abaikan saja
        }
    }

    public float getVolume() {
        return currentVolume;
    }
}