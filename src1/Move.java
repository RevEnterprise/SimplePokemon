public class Move {
    private final String name;
    private final int damage;
    private final ElementType type;

    public Move(String name, int damage, ElementType type) {
        this.name = name;
        this.damage = Math.max(0, damage);
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public ElementType getType() { 
        return type; 
    }
}
