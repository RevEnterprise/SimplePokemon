public class PlayerTrainer extends Trainer {

    private final String spritePathMoveNorth1;
    private final String spritePathMoveNorth2;

    private final String spritePathMoveSouth1;
    private final String spritePathMoveSouth2;

    private final String spritePathMoveEast1;
    private final String spritePathMoveEast2;

    private final String spritePathMoveWest1;
    private final String spritePathMoveWest2;


    public PlayerTrainer(String name, int startCol, int startRow, String spritePathStandingNorth, String spritePathStandingSouth, String spritePathStandingEast, String spritePathStandingWest, String spritePathMoveNorth1, String spritePathMoveNorth2, String spritePathMoveSouth1, String spritePathMoveSouth2, String spritePathMoveEast1, String spritePathMoveEast2, String spritePathMoveWest1, String spritePathMoveWest2) {

        super(name, startCol, startRow, spritePathStandingNorth, spritePathStandingSouth, spritePathStandingEast, spritePathStandingWest);

        this.spritePathMoveNorth1 = spritePathMoveNorth1;
        this.spritePathMoveNorth2 = spritePathMoveNorth2;
        this.spritePathMoveSouth1 = spritePathMoveSouth1;
        this.spritePathMoveSouth2 = spritePathMoveSouth2;
        this.spritePathMoveEast1 = spritePathMoveEast1;
        this.spritePathMoveEast2 = spritePathMoveEast2;
        this.spritePathMoveWest1 = spritePathMoveWest1;
        this.spritePathMoveWest2 = spritePathMoveWest2;

        addPokemon(MonsterFactory.createCharmander());
        addPokemon(MonsterFactory.createPikachu());
        addPokemon(MonsterFactory.createLatias());
    }

    public String getSpritePathMoveNorth1() {
        return spritePathMoveNorth1;
    }

    public String getSpritePathMoveNorth2() {
        return spritePathMoveNorth2;
    }

    public String getSpritePathMoveSouth1() {
        return spritePathMoveSouth1;
    }

    public String getSpritePathMoveSouth2() {
        return spritePathMoveSouth2;
    }
    
    public String getSpritePathMoveEast1() {
        return spritePathMoveEast1;
    }

    public String getSpritePathMoveEast2() {
        return spritePathMoveEast2;
    }

    public String getSpritePathMoveWest1() {
        return spritePathMoveWest1;
    }

    public String getSpritePathMoveWest2() {
        return spritePathMoveWest2;
    }
}

