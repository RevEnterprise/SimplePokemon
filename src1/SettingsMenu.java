import javax.swing.*;
import java.awt.*;

public class SettingsMenu extends JDialog {

    public SettingsMenu(JFrame parent, Trainer trainer, SoundManager soundManager) {
        super(parent, "Settings", true);
        setSize(420, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 1, 10, 10));

        JPanel volPanel = new JPanel();
        volPanel.add(new JLabel("Volume: "));
        
        int currentVol = (int)(soundManager.getVolume() * 100);
        JSlider slider = new JSlider(0, 100, currentVol);
        
        slider.addChangeListener(e -> {
            float volumeValue = slider.getValue() / 100f;
            soundManager.setVolume(volumeValue);
        });
        
        volPanel.add(slider);
        add(volPanel);

        JButton testMusicBtn = new JButton("Test Battle Music");
        testMusicBtn.addActionListener(e -> {
            soundManager.playMusic("../POKEMON/music/battle_theme.wav");
        });
        add(testMusicBtn);

        JButton rearrBtn = new JButton("Rearrange Pokémon");
        rearrBtn.addActionListener(e -> {
            new PokemonArrangeUI(parent, trainer).setVisible(true);
        });
        add(rearrBtn);
        
        JButton closeBtn = new JButton("Resume Game");
        closeBtn.addActionListener(e -> {
            soundManager.playMusic("../POKEMON/music/overworld_theme.wav");
            
            dispose();
        });
        add(closeBtn);

       
        JButton exitBtn = new JButton("Exit to Desktop");
        exitBtn.addActionListener(e -> System.exit(0));
        add(exitBtn);
    }
}
