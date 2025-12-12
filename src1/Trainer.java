import java.util.ArrayList;

public class Trainer {

    private String name;
    private int col;
    private int row;

    private int facingDirection = 1; // 0=N,1=S,2=E,3=W

    private final String spritePathStandingNorth;

    private final String spritePathStandingSouth;

    private final String spritePathStandingEast;

    private final String spritePathStandingWest;

    public static final int MAX_POKEMON = 6;

    private ArrayList<Pokemon> pokemon;

    public Trainer(String name, int startCol, int startRow, String spritePathStandingNorth, String spritePathStandingSouth, String spritePathStandingEast, String spritePathStandingWest) {
        this.name = name;
        this.col = startCol;
        this.row = startRow;
        this.spritePathStandingNorth = spritePathStandingNorth;
        this.spritePathStandingSouth = spritePathStandingSouth;
        this.spritePathStandingEast = spritePathStandingEast;
        this.spritePathStandingWest = spritePathStandingWest;

        pokemon = new ArrayList<>(6);
    }

    public String getName() { return name; }

    public ArrayList<Pokemon> getPokemon() { return pokemon; }
    public Pokemon getFirstPokemon() { return pokemon.get(0); }

    public int getCol() { return col; }
    public int getRow() { return row; }

    public boolean hasSpaceForPokemon() {
        return pokemon.size() < MAX_POKEMON;
    }

    /** Add one Pokémon to trainer's roster */
    public void addPokemon(Pokemon p) {
        if (p != null) pokemon.add(p);
    }

    public void move(int dx, int dy, int maxCols, int maxRows) {
        int newCol = col + dx;
        int newRow = row + dy;

        if (newCol >= 0 && newCol < maxCols && newRow >= 0 && newRow < maxRows) {
            col = newCol;
            row = newRow;
        }
    }

    public void arrangePokemon(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= pokemon.size()) return;
        if (toIndex < 0 || toIndex >= pokemon.size()) return;

        Pokemon p = pokemon.remove(fromIndex);
        pokemon.add(toIndex, p);
    }

    public String getSpritePathStandingNorth() {
        return spritePathStandingNorth;
    }

    public String getSpritePathStandingSouth() {
        return spritePathStandingSouth;
    }

    public String getSpritePathStandingEast() {
        return spritePathStandingEast;
    }

    public String getSpritePathStandingWest() {
        return spritePathStandingWest;
    }

    public void setFacingDirection(int dir) { 
        this.facingDirection = dir; 
    }

    public int getFacingDirection() { 
        return facingDirection; 
    }
}

