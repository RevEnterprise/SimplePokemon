import javax.swing.*;
import java.awt.*;

public class GameApp extends JFrame {

    public GameApp() {
        setTitle("Trainer Exploration Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        int widthScreen = 800;
        int heightScreen = 600;
        
        WorldPanel panel = new WorldPanel(widthScreen, heightScreen, this);
        
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
