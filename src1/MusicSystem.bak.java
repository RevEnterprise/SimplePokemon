import javax.sound.sampled.*;

public class MusicSystem {

    private Clip clip;
    private float volume = 0.5f;

    private byte[] tone(int hz) {
        int samples = 44100;
        byte[] buf = new byte[samples];
        for (int i = 0; i < samples; i++) {
            double ang = 2.0 * Math.PI * i * hz / 44100.0;
            buf[i] = (byte)(Math.sin(ang) * 40);
        }
        return buf;
    }

    private void playTone(int freq) {
        try {
            byte[] data = tone(freq);
            AudioFormat fmt = new AudioFormat(44100, 8, 1, true, false);
            clip = AudioSystem.getClip();
            clip.open(fmt, data, 0, data.length);
            setVolume(volume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Audio error: " + ex);
        }
    }

    public void startMusicA() { playTone(220); }
    public void startMusicB() { playTone(330); }

    public void setVolume(float v) {
        volume = v;
        if (clip != null) {
            FloatControl ctrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            float db = (float)(20 * Math.log10(v <= 0 ? 0.0001 : v));
            ctrl.setValue(db);
        }
    }

    public float getVolume() { return volume; }
}

