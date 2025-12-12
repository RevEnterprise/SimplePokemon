public enum ElementType {
    NORMAL, 
    FIRE,
    WATER,
    GRASS,
    FLYING,
    ROCK,
    PSYCHIC,
    ICE,
    DRAGON,
    ELECTRIC;

    public static double getEffectiveness(ElementType attacker, ElementType defender) {
        if (attacker == FIRE) {
            if (defender == GRASS) return 2.0;
            if (defender == WATER) return 0.5;
            if (defender == FIRE) return 0.5;
            if (defender == ROCK) return 0.5;
            if (defender == DRAGON) return 0.5;
        }
        
        if (attacker == WATER) {
            if (defender == FIRE) return 2.0;
            if (defender == GRASS) return 0.5;
            if (defender == ELECTRIC) return 0.5;
            if (defender == WATER) return 0.5;
            if (defender == DRAGON) return 0.5;
        }

        if (attacker == GRASS) {
            if (defender == WATER) return 2.0;
            if (defender == FIRE) return 0.5;
            if (defender == FLYING) return 0.5;
            if (defender == GRASS) return 0.5;
            if (defender == ROCK) return 2.0;
            if (defender == DRAGON) return 0.5;
        }

        if (attacker == FLYING) {
            if (defender == GRASS) return 2.0;
            if (defender == ELECTRIC) return 0.5;
            if (defender == ROCK) return 0.5;
        }

        if (attacker == ELECTRIC) {
            if (defender == WATER) return 2.0;
            if (defender == GRASS) return 0.5;
            if (defender == ELECTRIC) return 0.5;
            if (defender == DRAGON) return 0.5;
        }

        if (attacker == ROCK) {
            if (defender == FIRE) return 2.0;
            if (defender == FLYING) return 2.0;
            if (defender == ICE) return 2.0;
            if (defender == ROCK) return 0.5;
        }

        if (attacker == NORMAL) {
            if (defender == ROCK) return 0.5;
        }

        if (attacker == PSYCHIC) {
            if (defender == PSYCHIC) return 0.5;
        }

        if (attacker == ICE) {
            if (defender == GRASS) return 2.0;
            if (defender == FLYING) return 2.0;
            if (defender == DRAGON) return 2.0;
            if (defender == WATER) return 0.5;
            if (defender == FIRE) return 0.5;
            if (defender == ICE) return 0.5;
        }

        if (attacker == DRAGON) {
            if (defender == DRAGON) return 2.0;
        }



        return 1.0;
    }
}