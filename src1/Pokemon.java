public class Pokemon {
    private final String name;
    private final int attack;
    private final int maxHp;
    private int currentHp;
    private final int speed;
    private final Move move1;
    private final Move move2;
    private final String spritePath;
    private final String spritePathEnemy;
    private final ElementType type;

    public Pokemon(String name, int maxHp, int speed, int attack, Move move1, Move move2, String spritePath, String spritePathEnemy, ElementType type) {
        this.name = name;
        this.attack = attack;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.speed = speed;
        this.move1 = move1;
        this.move2 = move2;
        this.spritePath = spritePath;
        this.spritePathEnemy = spritePathEnemy;
        this.type = type;
    }

    public String getName() { return name; }
    public int getAttack() { return attack; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }
    public int getSpeed() { return speed; }
    public Move getMove1() { return move1; }
    public Move getMove2() { return move2; }
    public String getSpritePath() { return spritePath; }
    public String getSpritePathEnemy() { return spritePathEnemy; }
    public ElementType getType() { return type; }

    public void takeDamage(int amount) {
        currentHp = Math.max(0, currentHp - Math.max(0, amount));
    }

    public void heal(int amount) {
        currentHp = Math.min(maxHp, currentHp + Math.max(0, amount));
    }

    public void fullheal() {
        this.currentHp = this.maxHp;
    }


    public boolean isFainted() { return currentHp <= 0; }   
}
