import javax.swing.*;
import java.awt.*;

public class SettingsMenu extends JDialog {

    // Perubahan 1: Terima SoundManager, bukan MusicSystem
    public SettingsMenu(JFrame parent, Trainer trainer, SoundManager soundManager) {
        super(parent, "Settings", true); // Modal = true (Game pause di belakang)
        setSize(420, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 1, 10, 10));

        // === 1. VOLUME SLIDER ===
        JPanel volPanel = new JPanel();
        volPanel.add(new JLabel("Volume: "));
        
        // Ambil volume saat ini (0.0 - 1.0) dikali 100 untuk slider
        int currentVol = (int)(soundManager.getVolume() * 100);
        JSlider slider = new JSlider(0, 100, currentVol);
        
        // Listener saat slider digeser
        slider.addChangeListener(e -> {
            float volumeValue = slider.getValue() / 100f;
            soundManager.setVolume(volumeValue);
        });
        
        volPanel.add(slider);
        add(volPanel);

        // === 2. TEST MUSIC BUTTON ===
        // Saya ubah jadi "Test Battle Music" agar jelas
        JButton testMusicBtn = new JButton("Test Battle Music");
        testMusicBtn.addActionListener(e -> {
            // Memutar lagu battle (Akan menghentikan lagu overworld)
            soundManager.playMusic("../POKEMON/music/battle_theme.wav");
        });
        add(testMusicBtn);

        // === 3. REARRANGE POKEMON ===
        JButton rearrBtn = new JButton("Rearrange Pokémon");
        rearrBtn.addActionListener(e -> {
            // Pastikan class PokemonArrangeUI kamu sudah ada
            new PokemonArrangeUI(parent, trainer).setVisible(true);
        });
        add(rearrBtn);
        
        // === 4. CLOSE MENU (Resume Game) ===
        JButton closeBtn = new JButton("Resume Game");
        closeBtn.addActionListener(e -> {
            // Jika tadi user menekan tombol Test Music, kembalikan ke lagu Overworld saat menu ditutup
            // (Opsional, aktifkan baris bawah ini jika ingin otomatis balik ke lagu overworld)
            soundManager.playMusic("../POKEMON/music/overworld_theme.wav");
            
            dispose(); // Tutup dialog
        });
        add(closeBtn);

        // === 5. EXIT GAME ===
        JButton exitBtn = new JButton("Exit to Desktop");
        exitBtn.addActionListener(e -> System.exit(0));
        add(exitBtn);
    }
}