import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PokemonArrangeUI extends JDialog {

    private String getPokemonDisplayString(Pokemon p) {
        return String.format("%s (HP: %d/%d)", p.getName(), p.getCurrentHp(), p.getMaxHp());
    }

    public PokemonArrangeUI(JFrame parent, Trainer trainer) {
        super(parent, "Rearrange Pokémon", true);
        setSize(350, 400);
        setLocationRelativeTo(parent);

        setLayout(new BorderLayout());

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Pokemon p : trainer.getPokemon())
            model.addElement(getPokemonDisplayString(p));

        JList<String> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(list);

        JButton up = new JButton("Move Up");
        JButton down = new JButton("Move Down");

        up.addActionListener(e -> {
            int i = list.getSelectedIndex();
            if (i > 0) {
                trainer.arrangePokemon(i, i - 1);
                refresh(model, trainer.getPokemon());
                list.setSelectedIndex(i - 1);
            }
        });

        down.addActionListener(e -> {
            int i = list.getSelectedIndex();
            if (i >= 0 && i < model.size() - 1) {
                trainer.arrangePokemon(i, i + 1);
                refresh(model, trainer.getPokemon());
                list.setSelectedIndex(i + 1);
            }
        });

        JPanel panel = new JPanel();
        panel.add(up);
        panel.add(down);

        add(scroll, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void refresh(DefaultListModel<String> model, ArrayList<Pokemon> arr) {
        model.clear();
        for (Pokemon p : arr)
            model.addElement(getPokemonDisplayString(p));
    }
}
