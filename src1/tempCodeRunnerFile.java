import javax.swing.*;
import java.awt.*;

public class GameApp extends JFrame {

    private MusicSystem musicSystem;

    public GameApp() {
        setTitle("Trainer Exploration Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        musicSystem = new MusicSystem();
        musicSystem.startMusicA();

        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // int widthScreen = screenSize.width / 2;
        // int heightScreen = screenSize.height / 2;
        int widthScreen = 800;
        int heightScreen = 600;

        WorldPanel panel = new WorldPanel(widthScreen, heightScreen, musicSystem, this);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        panel.requestFocusInWindow();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameApp::new);
    }
}

