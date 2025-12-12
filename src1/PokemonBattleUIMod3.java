
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokemonBattleUIMod3 extends JFrame {

    private Pokemon playerPokemon;
    private Pokemon enemyPokemon;

    private JLabel battleMessage;
    private JLabel enemyImageLabel;
    private JLabel playerImageLabel;
    private JLabel playerHPText;
    private JProgressBar enemyHPBar;
    private JProgressBar playerHPBar;
    private JPanel commandPanel;
    private JButton fightBtn, pkmnBtn, runBtn, move1Btn, move2Btn, backBtn;

    private List<Pokemon> playerParty;
    private List<Pokemon> enemyParty1;
    private PlayerTrainer player;
    private Trainer enemyTrainerData;

    private SoundManager soundManager;
    private JLayeredPane layered;
    private final Random rand = new Random();

    private final int enemyDelayMs = 2000;

    private Runnable onBattleEnd;

    public PokemonBattleUIMod3(Runnable onBattleEnd, PlayerTrainer player, Trainer enemyTrainer ) {
        this.onBattleEnd = onBattleEnd;
        this.player = player;
        this.enemyTrainerData = enemyTrainer;

        soundManager = new SoundManager();
        soundManager.playMusic("../POKEMON/music/battle_theme.wav");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int widthScreen = screenSize.width / 2;
        int heightScreen = screenSize.height / 2;

        setSize(widthScreen, heightScreen);
        setLocationRelativeTo(null);
        setTitle("Pokemon Battle");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        int infoWidth = (int) (widthScreen * 0.30);
        int infoHeight = 25;
        int hpBarHeight = 20;
        int fontSize = Math.max(12, (int) (widthScreen * 0.02));
        Font infoFont = new Font("Arial", Font.BOLD, fontSize);

        layered = getLayeredPane();

        ImageIcon bgIcon = new ImageIcon("../POKEMON/BattleBackground.png");
        Image bgScaled = bgIcon.getImage().getScaledInstance(widthScreen, heightScreen, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(bgScaled));
        bgLabel.setBounds(0, 0, widthScreen, heightScreen);
        layered.add(bgLabel, Integer.valueOf(0));

        createPokemons();

        JLabel enemyLabel = new JLabel(enemyPokemon.getName().toUpperCase());
        enemyLabel.setFont(infoFont);
        enemyLabel.setBounds(20, 20, infoWidth, infoHeight);
        enemyLabel.setOpaque(true);
        enemyLabel.setBackground(new Color(255, 255, 255, 200));
        enemyLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        layered.add(enemyLabel, Integer.valueOf(1));

        enemyHPBar = new JProgressBar(0, enemyPokemon.getMaxHp());
        enemyHPBar.setValue(enemyPokemon.getCurrentHp());
        enemyHPBar.setBounds(20, 40 + fontSize, infoWidth, hpBarHeight);
        enemyHPBar.setForeground(Color.GREEN);
        enemyHPBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layered.add(enemyHPBar, Integer.valueOf(1));

        ImageIcon enemySpriteIcon = new ImageIcon(enemyPokemon.getSpritePathEnemy());
        int sqTargetHeight = 150;
        int sqTargetWidth = Math.max(1, enemySpriteIcon.getIconWidth() * sqTargetHeight / Math.max(1, enemySpriteIcon.getIconHeight()));
        Image scaledEnemy = enemySpriteIcon.getImage().getScaledInstance(sqTargetWidth, sqTargetHeight, Image.SCALE_DEFAULT);
        enemyImageLabel = new JLabel(new ImageIcon(scaledEnemy));
        enemyImageLabel.setBounds(widthScreen - sqTargetWidth - 70, 40, sqTargetWidth, sqTargetHeight);
        layered.add(enemyImageLabel, Integer.valueOf(1));

        battleMessage = new JLabel("What will " + playerPokemon.getName() + " do?");
        battleMessage.setFont(new Font("Arial", Font.BOLD, 18));
        battleMessage.setOpaque(true);
        battleMessage.setBackground(new Color(255, 255, 255, 200));
        battleMessage.setBounds(20, heightScreen - 150, widthScreen - 400, 50);
        battleMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layered.add(battleMessage, Integer.valueOf(2));

        JLabel playerLabel = new JLabel(playerPokemon.getName().toUpperCase() + " : L5");
        playerLabel.setFont(infoFont);
        playerLabel.setBounds(widthScreen - infoWidth - 20, heightScreen - (int) (heightScreen * 0.50),
                infoWidth, infoHeight);
        playerLabel.setOpaque(true);
        playerLabel.setBackground(new Color(255, 255, 255, 200));
        layered.add(playerLabel, Integer.valueOf(1));

        playerHPText = new JLabel("HP: " + playerPokemon.getCurrentHp() + " / " + playerPokemon.getMaxHp());
        playerHPText.setFont(infoFont);
        playerHPText.setBounds(widthScreen - infoWidth - 20,
                heightScreen - (int) (heightScreen * 0.50) + infoHeight,
                infoWidth, infoHeight);
        layered.add(playerHPText, Integer.valueOf(1));

        playerHPBar = new JProgressBar(0, playerPokemon.getMaxHp());
        playerHPBar.setValue(playerPokemon.getCurrentHp());
        playerHPBar.setBounds(widthScreen - infoWidth - 20,
                heightScreen - (int) (heightScreen * 0.50) + infoHeight * 2,
                infoWidth, hpBarHeight);
        playerHPBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        layered.add(playerHPBar, Integer.valueOf(1));

        ImageIcon playerSpriteIcon = new ImageIcon(playerPokemon.getSpritePath());
        int chTargetHeight = 200;
        int chTargetWidth = Math.max(1, playerSpriteIcon.getIconWidth() * chTargetHeight / Math.max(1, playerSpriteIcon.getIconHeight()));
        Image scaledPlayer = playerSpriteIcon.getImage().getScaledInstance(chTargetWidth, chTargetHeight, Image.SCALE_DEFAULT);
        playerImageLabel = new JLabel(new ImageIcon(scaledPlayer));
        playerImageLabel.setBounds(50, heightScreen - chTargetHeight - 120, chTargetWidth, chTargetHeight);
        layered.add(playerImageLabel, Integer.valueOf(1));

        commandPanel = new JPanel();
        commandPanel.setLayout(null);
        commandPanel.setOpaque(false);
        int panelHeight = 120;
        commandPanel.setBounds(0, heightScreen - panelHeight, widthScreen, panelHeight);
        layered.add(commandPanel, Integer.valueOf(5));

        int btnWidth = Math.max(80, widthScreen / 5);
        int btnHeight = 40;

        fightBtn = new JButton("FIGHT");
        fightBtn.setBounds(20, 20, btnWidth, btnHeight);
        commandPanel.add(fightBtn);

        pkmnBtn = new JButton("PKMN");
        pkmnBtn.setBounds(40 + btnWidth, 20, btnWidth, btnHeight);
        commandPanel.add(pkmnBtn);

        runBtn = new JButton("RUN");
        runBtn.setBounds(60 + btnWidth * 2, 20, btnWidth, btnHeight);
        commandPanel.add(runBtn);

        move1Btn = new JButton(playerPokemon.getMove1().getName());
        move1Btn.setBounds(20, 20, btnWidth, btnHeight);

        move2Btn = new JButton(playerPokemon.getMove2().getName());
        move2Btn.setBounds(40 + btnWidth, 20, btnWidth, btnHeight);

        backBtn = new JButton("BACK");
        backBtn.setBounds(60 + btnWidth * 2, 20, btnWidth, btnHeight);

        fightBtn.addActionListener(e -> swapToMoves());

        backBtn.addActionListener(e -> swapToMain());

        pkmnBtn.addActionListener(e -> openSwitchMenu(true));

        runBtn.addActionListener(e -> finishBattle());

        move1Btn.addActionListener(e -> playerUseMove(playerPokemon.getMove1()));
        move2Btn.addActionListener(e -> playerUseMove(playerPokemon.getMove2()));

        swapToMain();
    }

    private void finishBattle() {
        if (soundManager != null) {
            soundManager.stopMusic();
        }

        if (onBattleEnd != null) {
            onBattleEnd.run();
        }
        
        dispose();
    }

    private void openSwitchMenu(boolean allowCancel) {
        List<String> optionsLabels = new ArrayList<>();
        List<Pokemon> availablePoke = new ArrayList<>();

        for (Pokemon p : playerParty) {
            if (!p.isFainted()) {
                optionsLabels.add(p.getName() + " (HP: " + p.getCurrentHp() + ")");
                availablePoke.add(p);
            }
        }

        if (allowCancel) {
            optionsLabels.add("Cancel");
        }

        Object[] options = optionsLabels.toArray();

        int chosenIndex = JOptionPane.showOptionDialog(this,
                "Choose Pokémon to switch to:",
                allowCancel ? "Switch Pokémon" : "You must switch!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        if (chosenIndex >= 0 && chosenIndex < availablePoke.size()) {
            Pokemon selected = availablePoke.get(chosenIndex);

            if (selected == playerPokemon) {
                battleMessage.setText(selected.getName() + " is already out!");
                if (!allowCancel) openSwitchMenu(false);
            } else {
                switchPlayerTo(selected);
                if (!allowCancel) enableCommandPanel();
            }
        } else {
            if (!allowCancel) openSwitchMenu(false);
        }
    }

    private void showWhitedOutScreen() {
        JPanel blackPanel = new JPanel();
        blackPanel.setBackground(Color.BLACK);
        blackPanel.setBounds(0, 0, getWidth(), getHeight());
        blackPanel.setLayout(new GridBagLayout());

        JLabel text = new JLabel("You have no more Pokémon... Whited Out!");
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setForeground(Color.WHITE);
        blackPanel.add(text);

        layered.add(blackPanel, Integer.valueOf(100));
        layered.repaint();
    }

    private void createPokemons() {
        playerParty = player.getPokemon();
        enemyParty1 = new ArrayList<>();

        playerPokemon = player.getFirstPokemon();

        if (enemyTrainerData != null) {
            enemyParty1.addAll(enemyTrainerData.getPokemon());
            
            if (enemyParty1.isEmpty()) {
                enemyParty1.add(MonsterFactory.createRandomWild());
            }
        } 
        else {
            enemyParty1.add(MonsterFactory.createRandomWild());
        }

        enemyPokemon = enemyParty1.get(0);

    }

    private void swapToMoves() {
        commandPanel.removeAll();
        move1Btn.setText(playerPokemon.getMove1().getName());
        move2Btn.setText(playerPokemon.getMove2().getName());
        commandPanel.add(move1Btn);
        commandPanel.add(move2Btn);
        commandPanel.add(backBtn);
        commandPanel.revalidate();
        commandPanel.repaint();
    }

    private void swapToMain() {
        commandPanel.removeAll();
        commandPanel.add(fightBtn);
        commandPanel.add(pkmnBtn);
        commandPanel.add(runBtn);
        battleMessage.setText("What will " + playerPokemon.getName() + " do?");
        commandPanel.revalidate();
        commandPanel.repaint();
    }

    private void switchPlayerTo(Pokemon newPlayer) {
        if (newPlayer == playerPokemon) return;
        playerPokemon = newPlayer;

        ImageIcon playerSpriteIcon = new ImageIcon(playerPokemon.getSpritePath());
        int chTargetHeight = 200;
        int chTargetWidth = Math.max(1, playerSpriteIcon.getIconWidth() * chTargetHeight / Math.max(1, playerSpriteIcon.getIconHeight()));
        Image scaledPlayer = playerSpriteIcon.getImage().getScaledInstance(chTargetWidth, chTargetHeight, Image.SCALE_DEFAULT);
        playerImageLabel.setIcon(new ImageIcon(scaledPlayer));
        playerImageLabel.setSize(chTargetWidth, chTargetHeight);

        playerHPBar.setMaximum(playerPokemon.getMaxHp());
        playerHPBar.setValue(playerPokemon.getCurrentHp());
        playerHPText.setText("HP: " + playerPokemon.getCurrentHp() + " / " + playerPokemon.getMaxHp());

        move1Btn.setText(playerPokemon.getMove1().getName());
        move2Btn.setText(playerPokemon.getMove2().getName());

        battleMessage.setText("Switched to " + playerPokemon.getName() + "!");
        new Timer(800, (ActionEvent e) -> {
            ((Timer) e.getSource()).stop();
            swapToMain();
        }).start();
    }

    private void switchToNextEnemy() {
        for (Pokemon p : enemyParty1) {
            if (!p.isFainted()) {
                enemyPokemon = p;
                break;
            }
        }

        enemyHPBar.setMaximum(enemyPokemon.getMaxHp());
        enemyHPBar.setValue(enemyPokemon.getCurrentHp());
        updateHPBarColor(enemyHPBar, enemyPokemon.getCurrentHp(), enemyPokemon.getMaxHp());

        ImageIcon enemySpriteIcon = new ImageIcon(enemyPokemon.getSpritePathEnemy());
        int sqTargetHeight = 150;
        int sqTargetWidth = Math.max(1, enemySpriteIcon.getIconWidth() * sqTargetHeight / Math.max(1, enemySpriteIcon.getIconHeight()));
        Image scaledEnemy = enemySpriteIcon.getImage().getScaledInstance(sqTargetWidth, sqTargetHeight, Image.SCALE_DEFAULT);
        enemyImageLabel.setIcon(new ImageIcon(scaledEnemy));
        enemyImageLabel.setSize(sqTargetWidth, sqTargetHeight);
        int widthScreen = getWidth();
        enemyImageLabel.setLocation(widthScreen - sqTargetWidth - 70, 40);

        battleMessage.setText("Rival sent out " + enemyPokemon.getName() + "!");
    }

    private void playerUseMove(Move move) {
        disableCommandPanel();
        
        boolean playerGoesFirst = decideFirst(playerPokemon.getSpeed(), enemyPokemon.getSpeed());

        if (playerGoesFirst) {
            battleMessage.setText(playerPokemon.getName() + " used " + move.getName() + "!");

            new javax.swing.Timer(1000, e -> {
                ((javax.swing.Timer)e.getSource()).stop();

                int damage = calculateDamage(move, playerPokemon, enemyPokemon);

                performAttack(true, damage, () -> {
                    if (enemyPokemon.getCurrentHp() > 0) {
                         new javax.swing.Timer(enemyDelayMs, ev -> {
                             ((javax.swing.Timer)ev.getSource()).stop();
                             enemyTurn(); 
                         }).start();
                    } else {
                        onFaint(true);
                    }
                });
            }).start();

        } else {
             enemyTurn(() -> {
                 if (!playerPokemon.isFainted()) {
                     
                     battleMessage.setText(playerPokemon.getName() + " used " + move.getName() + "!");
                     
                     new javax.swing.Timer(1000, e -> {
                         ((javax.swing.Timer)e.getSource()).stop();
                         
                         int damage = calculateDamage(move, playerPokemon, enemyPokemon);
                         
                         performAttack(true, damage, () -> {
                             if (enemyPokemon.getCurrentHp() <= 0) onFaint(true);
                             else enableCommandPanel();
                         });
                     }).start();
                 } else {
                     onFaint(false);
                 }
             });
        }
    }

    private boolean decideFirst(int speedA, int speedB) {
        if (speedA > speedB) return true;
        if (speedA < speedB) return false;
        return rand.nextBoolean();
    }

private void enemyTurn() {
        enemyTurn(null);
    }

    private void enemyTurn(Runnable afterEnemyFinish) {
        int roll = rand.nextInt(100);
        Move enemyMove = (roll < 70) ? enemyPokemon.getMove1() : enemyPokemon.getMove2();

        battleMessage.setText(enemyPokemon.getName() + " used " + enemyMove.getName() + "!");

        new javax.swing.Timer(1000, e -> {
            ((javax.swing.Timer)e.getSource()).stop();

            int damageToPlayer = calculateDamage(enemyMove, enemyPokemon, playerPokemon);

            performAttack(false, damageToPlayer, () -> {
                if (playerPokemon.isFainted()) {
                    onFaint(false);
                } else {
                    if (afterEnemyFinish != null) afterEnemyFinish.run();
                    else {
                        enableCommandPanel();
                        battleMessage.setText("What will " + playerPokemon.getName() + " do?");
                    }
                }
            });
        }).start();
    }

    private int calculateDamage(Move move, Pokemon attacker, Pokemon defender) {
        
        double multiplier = ElementType.getEffectiveness(move.getType(), defender.getType());
        
        int baseDamage = move.getDamage() + (attacker.getAttack() / 2);
        
        int finalDamage = (int) (baseDamage * multiplier);
        
        if (multiplier > 1.0) {
            battleMessage.setText("It's Super Effective!");
        } else if (multiplier < 1.0 && multiplier > 0) {
            battleMessage.setText("It's not very effective...");
        } else {
            battleMessage.setText(move.getName() + " used!");
        }
    
        return Math.max(1, finalDamage);
    }

    private void performAttack(boolean attackerIsPlayer, int damage, Runnable onComplete) {
        disableCommandPanel();

        JLabel targetImage = attackerIsPlayer ? enemyImageLabel : playerImageLabel;
        JProgressBar targetHPBar = attackerIsPlayer ? enemyHPBar : playerHPBar;

        final int startHp;
        final int targetMax;
        if (attackerIsPlayer) {
            startHp = enemyPokemon.getCurrentHp();
            targetMax = enemyPokemon.getMaxHp();
        } else {
            startHp = playerPokemon.getCurrentHp();
            targetMax = playerPokemon.getMaxHp();
        }

        String dmgText = "-" + damage;
        JLabel popup = new JLabel(dmgText);
        popup.setFont(new Font("Arial", Font.BOLD, 26));
        popup.setForeground(Color.RED);
        int px = targetImage.getX() + targetImage.getWidth() / 2 - 20;
        int py = targetImage.getY() - 10;
        popup.setBounds(px, py, 120, 40);
        layered.add(popup, Integer.valueOf(50));
        layered.repaint();

        final int[] alpha = {255};
        final int[] rise = {0};
        Timer popupTimer = new Timer(30, null);
        popupTimer.addActionListener((ActionEvent e) -> {
            rise[0] += 2;
            popup.setLocation(px, py - rise[0]);
            alpha[0] -= 12;
            if (alpha[0] < 0) alpha[0] = 0;
            popup.setForeground(new Color(255, 0, 0, alpha[0]));
            if (rise[0] > 60) {
                popupTimer.stop();
                layered.remove(popup);
                layered.repaint();
            }
        });
        popupTimer.start();

        final int baseX = targetImage.getX();
        final int baseY = targetImage.getY();
        Timer shakeTimer = new Timer(40, null);
        final int[] shakeCount = {0};
        shakeTimer.addActionListener((ActionEvent e) -> {
            shakeCount[0]++;
            int offset = (shakeCount[0] % 2 == 0) ? 6 : -6;
            targetImage.setLocation(baseX + offset, baseY);
            if (shakeCount[0] >= 8) {
                shakeTimer.stop();
                targetImage.setLocation(baseX, baseY);
            }
        });
        shakeTimer.start();

        Timer hpTimer = new Timer(10, null);
        hpTimer.addActionListener((ActionEvent e) -> {
            if (attackerIsPlayer) {
                enemyPokemon.takeDamage(1);
                enemyHPBar.setValue(enemyPokemon.getCurrentHp());
                updateHPBarColor(enemyHPBar, enemyPokemon.getCurrentHp(), enemyPokemon.getMaxHp());
            } else {
                playerPokemon.takeDamage(1);
                playerHPBar.setValue(playerPokemon.getCurrentHp());
                playerHPText.setText("HP: " + playerPokemon.getCurrentHp() + " / " + playerPokemon.getMaxHp());
                updateHPBarColor(playerHPBar, playerPokemon.getCurrentHp(), playerPokemon.getMaxHp());
            }

            if (attackerIsPlayer) {
                if (enemyPokemon.getCurrentHp() <= Math.max(0, startHp - damage)) {
                    hpTimer.stop();
                    new Timer(300, (ActionEvent ev) -> {
                        ((Timer) ev.getSource()).stop();
                        if (onComplete != null) onComplete.run();
                    }).start();
                }
            } else {
                if (playerPokemon.getCurrentHp() <= Math.max(0, startHp - damage)) {
                    hpTimer.stop();
                    new Timer(300, (ActionEvent ev) -> {
                        ((Timer) ev.getSource()).stop();
                        if (onComplete != null) onComplete.run();
                    }).start();
                }
            }
        });

        new Timer(120, (ActionEvent e) -> {
            ((Timer) e.getSource()).stop();
            hpTimer.start();
        }).start();
    }

    private void updateHPBarColor(JProgressBar bar, int value, int max) {
        double pct = (double) value / max;
        if (pct > 0.5) bar.setForeground(Color.GREEN);
        else if (pct > 0.2) bar.setForeground(Color.YELLOW);
        else bar.setForeground(Color.RED);
    }

    private void disableCommandPanel() {
        for (Component c : commandPanel.getComponents()) c.setEnabled(false);
    }

    private void enableCommandPanel() {
        for (Component c : commandPanel.getComponents()) c.setEnabled(true);
    }

    private boolean checkAnyPokemonAlive() {
        for (Pokemon p : playerParty) {
            if (!p.isFainted()) {
                return true;
            }
        }
        return false;
    }

    private void onFaint(boolean enemyFainted) {
        disableCommandPanel();

        if (enemyFainted) {
        battleMessage.setText("Enemy " + enemyPokemon.getName() + " fainted!");

        new javax.swing.Timer(1500, e -> {
            ((javax.swing.Timer)e.getSource()).stop();
            
            boolean anyEnemyAlive = false;
            for (Pokemon p : enemyParty1) {
                if (!p.isFainted()) {
                    anyEnemyAlive = true;
                    break;
                }
            }

            if (anyEnemyAlive) {
                switchToNextEnemy();
                
                enableCommandPanel();
                
                new javax.swing.Timer(1000, ev -> {
                     ((javax.swing.Timer)ev.getSource()).stop();
                     battleMessage.setText("What will " + playerPokemon.getName() + " do?");
                }).start();

            } else {
                battleMessage.setText("You defeated the Trainer!");
                JOptionPane.showMessageDialog(this, "Victory! You gained a valuable life experience that will lead you to become a pokemon master.");
                finishBattle();
            }
        }).start();

    } else {
            battleMessage.setText(playerPokemon.getName() + " fainted!");

            new javax.swing.Timer(1500, e -> {
                ((javax.swing.Timer)e.getSource()).stop();

                if (checkAnyPokemonAlive()) {
                    battleMessage.setText("Choose your next Pokémon!");
                    openSwitchMenu(false);
                } else {
                    showWhitedOutScreen();
                    finishBattle();
                }
            }).start();
        }
    }
}
