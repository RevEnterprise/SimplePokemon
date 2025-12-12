public class PokemonCenter {

    private int col;
    private int row;

    private String spritePath = "../PokemonCenterIMG/pokemoncenter.png";

    public PokemonCenter(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public int getCol() { return col; }
    public int getRow() { return row; }

    public int getWidth() { return 3; }

    public int getHeight() { return 3; }

    public String getSpritePath() {
        return spritePath;
    }
}

