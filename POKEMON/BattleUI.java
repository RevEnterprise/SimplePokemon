import javax.swing.*;
import java.awt.*;

public class PokemonBattleUI extends JFrame {
    public PokemonBattleUI() {
        setTitle("Pokemon Battle");
        setSize(600, 450); // frame lebih besar
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Squirtle info
        JLabel squirtleLabel = new JLabel("SQUIRTLE : L5");
        squirtleLabel.setBounds(20, 20, 150, 20);
        add(squirtleLabel);

        JProgressBar squirtleHP = new JProgressBar(0, 100);
        squirtleHP.setValue(60);
        squirtleHP.setBounds(20, 40, 150, 15);
        add(squirtleHP);

        // Sprite Squirtle (dibesarkan)
        ImageIcon squirtleSpriteRaw = new ImageIcon("POKEMON/squirtle_front.gif");
        Image squirtleScaled = squirtleSpriteRaw.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        JLabel squirtleImage = new JLabel(new ImageIcon(squirtleScaled));
        squirtleImage.setBounds(400, 20, 140, 140);
        add(squirtleImage);

        // Charmander info
        JLabel charmanderLabel = new JLabel("CHARMANDER : L5");
        charmanderLabel.setBounds(350, 200, 150, 20);
        add(charmanderLabel);

        JLabel charmanderHPText = new JLabel("HP: 15 / 19");
        charmanderHPText.setBounds(350, 220, 150, 20);
        add(charmanderHPText);

        JProgressBar charmanderHPBar = new JProgressBar(0, 19);
        charmanderHPBar.setValue(15);
        charmanderHPBar.setBounds(350, 240, 150, 15); // lebih tinggi, tidak tabrakan
        add(charmanderHPBar);

        // Sprite Charmander (dibesarkan)
        ImageIcon charmanderSpriteRaw = new ImageIcon("POKEMON/charmander_back.gif");
        Image charmanderScaled = charmanderSpriteRaw.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
        JLabel charmanderImage = new JLabel(new ImageIcon(charmanderScaled));
        charmanderImage.setBounds(50, 180, 160, 160);
        add(charmanderImage);

        // Menu buttons (geser lebih ke bawah)
        JButton fightBtn = new JButton("FIGHT");
        fightBtn.setBounds(50, 360, 100, 30);
        add(fightBtn);

        JButton pkmnBtn = new JButton("PKMN");
        pkmnBtn.setBounds(160, 360, 100, 30);
        add(pkmnBtn);

        JButton itemBtn = new JButton("ITEM");
        itemBtn.setBounds(270, 360, 100, 30);
        add(itemBtn);

        JButton runBtn = new JButton("RUN");
        runBtn.setBounds(380, 360, 100, 30);
        add(runBtn);
    }

    public static void main(String[] args) {
        new PokemonBattleUI().setVisible(true);
    }
}