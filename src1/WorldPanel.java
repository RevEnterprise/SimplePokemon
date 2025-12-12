import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// todo
// pokemon belum nyambung ke battle
// pokecenter belum ada gunanya
// gak ada musik di overworld
// movement player bisa ngebut banget sama kalo ngebut posisi terakhir agak lama buat updatenya
// text di battle belum update ke pokemon lain kalo ganti
// di arrange pokemon belum keliatan currentHP nya
// tanda seru encouter long grass terlalu cepat
// text kiri atas perlu diubah
// item dihapus
// npc ngarah ke player waktu battle habis itu ngarahh ke selatan lagi

public class WorldPanel extends JPanel implements KeyListener {

    private int screenW, screenH;
    private int tileSize = 48;
    private int cols = 10, rows = 10;
    private int gridW, gridH, gridX, gridY;

    private PlayerTrainer player;
    private Trainer npcLeft;
    private Trainer npcRight;
    private PokemonCenter pokeCenter;

    private String grassTexturePath = "../GrassTexture/Screenshot 2025-12-10 153159.png";
    private Image grassTexture;

    private String tallGrassImgPath = "../TallGrass/Screenshot 2025-12-10 153335.png";
    private Image tallGrassTexture;

    private int tgStartCol = 3;
    private int tgEndCol   = 6;
    private int tgStartRow = 7;
    private int tgEndRow   = 9;

    private int tallGrassMoveCounter = 0;
    private final int MOVES_TO_TRIGGER_BATTLE = 5;

    private boolean isEncounterInTallGrass = false;
    
    private JFrame parent;

    // Fade variables
    private float fadeAlpha = 0f;
    private boolean fading = false;
    private boolean fadeIn = true;

    // Last position before entering NPC or PokeCenter
    private int lastPlayerCol;
    private int lastPlayerRow;

    // Player animation state
    private String currentSpritePath = null;
    private int stepFrame = 0;        // 0 or 1
    private boolean isMoving = false;

    private long lastMoveTime = 0;
    private int lastDirection = 1; // 0=N,1=S,2=E,3=W (default south)
    private final int IDLE_WAIT = 1000; // ms until switch to standing
    
    private long stopTime = 0;
    private boolean waitingForIdle = false;
    private SoundManager soundManager;

    private boolean movingKeyDown = false;

    // Simple timer for animation
    private Timer walkTimer;

    private Timer startupStandingTimer;

    private Trainer lastChallengedNPC = null;

    public WorldPanel(int w, int h,  JFrame parent) {
        this.screenW = w;
        this.screenH = h;
        this.parent = parent;

        this.soundManager = new SoundManager();
        this.soundManager.playMusic("../POKEMON/music/overworld_theme.wav");

        setPreferredSize(new Dimension(w, h));
        setFocusable(true);
        addKeyListener(this);

        gridW = cols * tileSize;
        gridH = rows * tileSize;
        gridX = (screenW - gridW) / 2;
        gridY = (screenH - gridH) / 2;

        grassTexture = new ImageIcon(grassTexturePath).getImage();
        tallGrassTexture = new ImageIcon(tallGrassImgPath).getImage();

        // Create player in center
        player = new PlayerTrainer("Player", cols / 2, rows / 2,
            "../MainCharIMG/Standing North Brendan.png",
            "../MainCharIMG/Standing South Brendan.png",
            "../MainCharIMG/Standing East Brendan.png",
            "../MainCharIMG/Standing West Brendan.png",
            "../MainCharIMG/Move North 1 Brendan.png",
            "../MainCharIMG/Move North 2 Brendan.png",
            "../MainCharIMG/Move South 1 Brendan.png",
            "../MainCharIMG/Move South 2 Brendan.png",
            "../MainCharIMG/Move East 1 Brendan.png",
            "../MainCharIMG/Move East 2 Brendan.png",
            "../MainCharIMG/Move West 1 Brendan.png",
            "../MainCharIMG/Move West 2 Brendan.png"
        );

        currentSpritePath = player.getSpritePathStandingSouth();

        // npcLeft = new Trainer("Left NPC", 1, rows / 2, "../MainCharIMG/Standing North Brendan.png", "../MainCharIMG/Standing South Brendan.png", "../MainCharIMG/Standing East Brendan.png", "../MainCharIMG/Standing West Brendan.png");
        //
        // npcRight = new Trainer("Right NPC", cols - 2, rows / 2, "../MainCharIMG/Standing North Brendan.png", "../MainCharIMG/Standing South Brendan.png", "../MainCharIMG/Standing East Brendan.png", "../MainCharIMG/Standing West Brendan.png");


        // Two NPCs on left and right
        npcLeft = new Trainer("Left NPC", 1, rows / 2,
            "../NPC1/North NPC1.png",
            "../NPC1/South NPC1.png",
            "../NPC1/East NPC1.png",
            "../NPC1/West NPC1.png"
        );
        npcLeft.addPokemon(MonsterFactory.createSquirtle());
        npcLeft.addPokemon(MonsterFactory.createPikachu());

        npcRight = new Trainer("Left NPC", cols - 2, rows / 2,
            "../NPC2/North NPC2.png",
            "../NPC2/South NPC2.png",
            "../NPC2/East NPC2.png",
            "../NPC2/West NPC2.png"
        );
        npcRight.addPokemon(MonsterFactory.createBulbasaur());

        // Pokémon Center (top-left-ish)
        pokeCenter = new PokemonCenter(3, 1);

        walkTimer = new Timer(50, e -> {

            long now = System.currentTimeMillis();

            if (isMoving) {
                // walking animation frames
                // stepFrame = (stepFrame == 0 ? 1 : 0);
                repaint();
                waitingForIdle = false;   // cancel idle
            }
            else {
                // player released movement key and is no longer moving
                if (waitingForIdle) {
                    if (now - stopTime >= IDLE_WAIT) {
                        updateStandingSprite(lastDirection);
                        waitingForIdle = false;
                        repaint();
                    }
                }
            }
        });
        walkTimer.start();
    }

    // -----------------------------------------------------------------------------------------
    //  RENDERING
    // -----------------------------------------------------------------------------------------
    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0.create();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ------- WATER BACKGROUND -------
        g.setColor(new Color(173, 216, 230));
        g.fillRect(0, 0, screenW, screenH);

        // ------- GRASS GRID -------
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = gridX + c * tileSize;
                int y = gridY + r * tileSize;

                Color base = new Color(110, 200, 90);
                // g.setColor(((r + c) % 2 == 0) ? base : base.darker());
                // g.fillRect(x, y, tileSize, tileSize);
                g.drawImage(grassTexture, x, y, tileSize, tileSize, null);

                // Draw tall grass overlay if region matches
                if (isTallGrass(c, r)) {
                    g.drawImage(tallGrassTexture, x, y, tileSize, tileSize, null);
                }

            }
        }

        // Grid lines
        g.setColor(new Color(0, 0, 0, 80));
        for (int i = 1; i < cols; i++)
            g.drawLine(gridX + i * tileSize, gridY, gridX + i * tileSize, gridY + gridH);
        for (int i = 1; i < rows; i++)
            g.drawLine(gridX, gridY + i * tileSize, gridX + gridW, gridY + i * tileSize);

        // ------- OBJECTS -------
        drawPokemonCenter(g, pokeCenter);
        drawTrainer(g, npcLeft, Color.BLUE);
        drawTrainer(g, npcRight, Color.MAGENTA);
        drawTrainer(g, player, Color.RED);

        drawNPCAlert(g);  // EXCLAMATION MARK WHEN PLAYER TOUCHES NPC
        drawTallGrassEncounter(g);

        drawHUD(g);

        // ------- FADE-TO-BLACK OVERLAY -------
        if (fadeAlpha > 0f) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAlpha));
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screenW, screenH);
        }

        g.dispose();
    }

    private void drawTrainer(Graphics2D g, Trainer t, Color fallbackColor) {

        int px = gridX + t.getCol() * tileSize;
        int py = gridY + t.getRow() * tileSize;

        // Shadow
        g.setColor(new Color(0, 0, 0, 120));
        g.fillOval(px + 8, py + tileSize - 16, tileSize - 16, (tileSize - 16) / 3);

        // If it is the player AND sprite exists → draw sprite
        if (t == player && currentSpritePath != null) {
            Image img = new ImageIcon(currentSpritePath).getImage();
            g.drawImage(img, px, py, tileSize, tileSize, null);
            return;
        }

        // Fallback: NPC circles
        // g.setColor(fallbackColor);
        // g.fillOval(px + 8, py + 8, tileSize - 16, tileSize - 16);

        if (t != player) {

            String sprite = null;
            int dir = t.getFacingDirection();

            if (dir == 0) sprite = t.getSpritePathStandingNorth();
            if (dir == 1) sprite = t.getSpritePathStandingSouth();
            if (dir == 2) sprite = t.getSpritePathStandingEast();
            if (dir == 3) sprite = t.getSpritePathStandingWest();

            Image img = new ImageIcon(sprite).getImage();
            g.drawImage(img, px, py, tileSize, tileSize, null);
            return;
        }
    }

    private void drawNPCAlert(Graphics2D g) {

        Trainer collided = getNPCPlayerIsTouching();
        if (collided == null) return;

        int x = gridX + collided.getCol() * tileSize + tileSize / 2;
        int y = gridY + collided.getRow() * tileSize - 10;

        g.setColor(Color.YELLOW);
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        g.drawString("!", x, y);
    }

    private void drawPokemonCenter(Graphics2D g, PokemonCenter pokemonCenter) {

        // Load Pokémon Center sprite
        Image img = new ImageIcon(pokemonCenter.getSpritePath()).getImage();

        int px = gridX + pokemonCenter.getCol() * tileSize;
        int py = gridY + pokemonCenter.getRow() * tileSize;

        // The center is 2×2 tiles
        int w = tileSize * 3;
        int h = tileSize * 3;

        g.drawImage(img, px, py, w, h, null);
    }

    private void drawHUD(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 140));
        g.fillRoundRect(10, 10, 450, 130, 12, 12);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));

        g.drawString("Player: " + player.getName(), 20, 35);
        g.drawString("Position: (" + player.getCol() + ", " + player.getRow() + ")", 20, 55);

        String pokemonList = "";
        for (Pokemon pokemon : player.getPokemon()) {
            pokemonList += pokemon.getName() + " ";
        }

        g.drawString("Pokemon: " + pokemonList, 20, 75);

        g.drawString("Press ESC for Menu", 20, 120);
    }

    private void drawTallGrassEncounter(Graphics2D g) {

        if (!isEncounterInTallGrass)
            return;

        int x = gridX + player.getCol() * tileSize + tileSize / 2;
        int y = gridY + player.getRow() * tileSize - 10;

        g.setColor(Color.YELLOW);
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        g.drawString("!", x, y);
    }

    // -----------------------------------------------------------------------------------------
    //  PLAYER / OBJECT DETECTION
    // -----------------------------------------------------------------------------------------

    private boolean isPokeCenterRestrictedTile(int col, int row) {
    
        boolean inPCBounds = (col >= pokeCenter.getCol() && col <= pokeCenter.getCol() + pokeCenter.getWidth() - 1) &&
                            (row >= pokeCenter.getRow() && row <= pokeCenter.getRow() + pokeCenter.getHeight() - 1);

        if (!inPCBounds) {
            return false;
        }

        final int entranceCol = pokeCenter.getCol() + 1;
        final int entranceRow = pokeCenter.getRow() + 2;

        boolean isEntrance = (col == entranceCol && row == entranceRow);

        return !isEntrance;
    }
    
    private boolean isPlayerInsidePokeCenter() {
        int pCol = player.getCol();
        int pRow = player.getRow();

        final int entranceCol = pokeCenter.getCol() + 1;
        final int entranceRow = pokeCenter.getRow() + 2;

        return (pCol == entranceCol && pRow == entranceRow);
    }

    private Trainer getNPCPlayerIsTouching() {
        if (player.getCol() == npcLeft.getCol() &&
            player.getRow() == npcLeft.getRow())
            return npcLeft;

        if (player.getCol() == npcRight.getCol() &&
            player.getRow() == npcRight.getRow())
            return npcRight;

        return null;
    }

    // -----------------------------------------------------------------------------------------
    //  FADE AND TRANSITION LOGIC
    // -----------------------------------------------------------------------------------------

    private void startFadeToBlack(boolean isBattle) {

        fading = true;
        fadeIn = true;
        fadeAlpha = 0f;

        Timer fadeTimer = new Timer(30, null);

        fadeTimer.addActionListener(e -> {

            if (fadeIn) {
                fadeAlpha += 0.05f;

                if (fadeAlpha >= 1f) {
                    fadeAlpha = 1f;
                    fadeIn = false;

                    boolean shouldTeleport = !isTallGrass(player.getCol(), player.getRow());
                    
                    if (shouldTeleport) {
                        player.move(lastPlayerCol - player.getCol(),
                            lastPlayerRow - player.getRow(),
                            cols, rows);
                    }

                    try { Thread.sleep(450); } catch (InterruptedException ignored) {}

                    if (isBattle) {
                        goToBattlePanel(); // PLACEHOLDER
                        tallGrassMoveCounter = 0;
                        isEncounterInTallGrass = false;
                    }
                    else {
                        // RECOVER ALL POKEMON HP WHEN GOING TO POKECENTER
                        for (Pokemon p : player.getPokemon()) {
                            p.fullheal();
                        }
                        System.out.println("All Pokemon recovered at Pokecenter!");
                        lastDirection = 1; // South
                        updateStandingSprite(lastDirection);
                    }
                }

            } else {
                fadeAlpha -= 0.05f;

                if (fadeAlpha <= 0f) {
                    fadeAlpha = 0f;
                    fading = false;
                    fadeTimer.stop();
                }
            }

            repaint();
        });

        fadeTimer.start();
    }

    private void goToBattlePanel() {

        SwingUtilities.invokeLater(() -> {

            // Hide the world window (the parent JFrame)
            parent.setVisible(false);
            Trainer enemyToSend = lastChallengedNPC;
            if (soundManager != null) soundManager.stopMusic();

            // Create battle frame with callback to return
            PokemonBattleUIMod3 battleFrame = new PokemonBattleUIMod3(
                () -> {
                    parent.setVisible(true);
                    this.requestFocusInWindow();

                    if (soundManager != null) {
                        soundManager.playMusic("../POKEMON/music/overworld_theme.wav");
                    }

                    if (lastChallengedNPC != null) {

                        Timer revertTimer = new Timer(1000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                lastChallengedNPC.setFacingDirection(1);

                                lastChallengedNPC = null;

                                repaint();

                                ((Timer)e.getSource()).stop();

                            }
                        });

                        revertTimer.setRepeats(false);
                        revertTimer.start();
                    }
                },
                player,
                enemyToSend
            );
            battleFrame.setVisible(true);
        });
    }


    // -----------------------------------------------------------------------------------------
    //  INTERACTION CHECKS
    // -----------------------------------------------------------------------------------------

    private void checkPokemonCenterEntry() {
        if (isPlayerInsidePokeCenter() && !fading) {
            startFadeToBlack(false); // not a battle
        }
    }

    private void checkNPCEntry(int lastPlayerCol, int lastPlayerRow) {
        Trainer t = getNPCPlayerIsTouching();
        if (t != null && !fading) {
            faceNPCtowardsPlayer(t, lastPlayerCol, lastPlayerRow);
            lastChallengedNPC = t;
            repaint();
            startFadeToBlack(true);  // triggers battle placeholder
        }
    }

    private void checkTallGrassBattleEntry() {
        if (isEncounterInTallGrass && !fading) {
            startEncounterBattle();
        }
    }

    private void startEncounterBattle() {
        startFadeToBlack(true);  // triggers battle placeholder
    }

    // -----------------------------------------------------------------------------------------
    //  INPUT
    // -----------------------------------------------------------------------------------------

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        if (k == KeyEvent.VK_ESCAPE) {
            new SettingsMenu(parent, player, soundManager).setVisible(true);
            repaint();
            return;
        }

        // Player cannot move while inside NPC or PokeCenter or fading
        if (isPlayerInsidePokeCenter() || getNPCPlayerIsTouching() != null || fading) {
            return;
        }

        lastPlayerCol = player.getCol();
        lastPlayerRow = player.getRow();

        isMoving = false;

        int dx = 0, dy = 0;
        int dir = lastDirection;

        if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W)    { dy = -1; dir = 0; }
        if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S)  { dy =  1; dir = 1; }
        if (k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) { dx =  1; dir = 2; }
        if (k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A)  { dx = -1; dir = 3; }

        if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W ||
            k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S ||
            k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A ||
            k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) {

            movingKeyDown = true;  // player is walking
        }

        int targetCol = lastPlayerCol + dx;
        int targetRow = lastPlayerRow + dy;

        // Only update direction
        if (isPokeCenterRestrictedTile(targetCol, targetRow)) {
            lastDirection = dir; 
            updateStandingSprite(dir);
            repaint();
            return; 
        }

        // attempt move
        player.move(dx, dy, cols, rows);

        // if moved, enable walking animation
        if (player.getCol() != lastPlayerCol || player.getRow() != lastPlayerRow) {

            // Movement has just started → reset step cycle
            stepFrame = (stepFrame == 0 ? 1 : 0);
            isMoving = true;
            lastMoveTime = System.currentTimeMillis();
            lastDirection = dir;
            updateMovementSprite(dir);
            checkTallGrassCounter();
            checkTallGrassBattleEntry();
        }

        checkPokemonCenterEntry();
        checkNPCEntry(lastPlayerCol, lastPlayerRow);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int k = e.getKeyCode();

        if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W ||
            k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S ||
            k == KeyEvent.VK_LEFT || k == KeyEvent.VK_A ||
            k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_D) {

            movingKeyDown = false;
            isMoving = false;

            // Start idle countdown
            stopTime = System.currentTimeMillis();
            waitingForIdle = true;
        }
    }

    @Override public void keyTyped(KeyEvent e) {}

    private void updateStandingSprite(int dir) {
        if (dir == 0) currentSpritePath = player.getSpritePathStandingNorth();
        if (dir == 1) currentSpritePath = player.getSpritePathStandingSouth();
        if (dir == 2) currentSpritePath = player.getSpritePathStandingEast();
        if (dir == 3) currentSpritePath = player.getSpritePathStandingWest();
    }

    private void updateMovementSprite(int dir) {
        if (dir == 0)
            currentSpritePath = (stepFrame == 0 ?
                    player.getSpritePathMoveNorth1() :
                    player.getSpritePathMoveNorth2());
        if (dir == 1)
            currentSpritePath = (stepFrame == 0 ?
                    player.getSpritePathMoveSouth1() :
                    player.getSpritePathMoveSouth2());
        if (dir == 2)
            currentSpritePath = (stepFrame == 0 ?
                    player.getSpritePathMoveEast1() :
                    player.getSpritePathMoveEast2());
        if (dir == 3)
            currentSpritePath = (stepFrame == 0 ?
                    player.getSpritePathMoveWest1() :
                    player.getSpritePathMoveWest2());
    }

    private void faceNPCtowardsPlayer(Trainer npc, int lastPlayerCol, int lastPlayerRow) {
        int px = lastPlayerCol;
        int py = lastPlayerRow;
        int nx = npc.getCol();
        int ny = npc.getRow();

        if (py < ny) npc.setFacingDirection(0); // north
        else if (py > ny) npc.setFacingDirection(1); // south
        else if (px > nx) npc.setFacingDirection(2); // east
        else if (px < nx) npc.setFacingDirection(3); // west
    }

    private boolean isTallGrass(int col, int row) {
        return col >= tgStartCol && col <= tgEndCol &&
            row >= tgStartRow && row <= tgEndRow;
    }

    private void checkTallGrassCounter() {
        if (isTallGrass(player.getCol(), player.getRow())) {

            tallGrassMoveCounter++;

            if (tallGrassMoveCounter >= MOVES_TO_TRIGGER_BATTLE) {
                isEncounterInTallGrass = true;
            }

        } else {
            tallGrassMoveCounter = 0;
        }
    }

}
