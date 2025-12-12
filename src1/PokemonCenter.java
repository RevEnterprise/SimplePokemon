public class PokemonCenter {

    private int col;
    private int row;

    private String spritePath = "../PokemonCenterIMG/pokemoncenter.png";

    /** Top-left tile of the Pokémon Center */
    public PokemonCenter(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() { return col; }
    public int getRow() { return row; }

    /** Returns width in tiles */
    public int getWidth() { return 3; }

    /** Returns height in tiles */
    public int getHeight() { return 3; }

    public String getSpritePath() {
        return spritePath;
    }
}

