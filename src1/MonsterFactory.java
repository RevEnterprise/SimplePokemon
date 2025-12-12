public class MonsterFactory {

    private static Move createMove(String name, int damage, ElementType type) {
        return new Move(name, damage, type);
    }

    public static Pokemon createCharmander() {
        return new Pokemon(
            "Charmander",
            139, // MaxHP
            65,  // Speed
            52,
            createMove("Ember", 40, ElementType.FIRE),
            createMove("Scratch", 40, ElementType.NORMAL),
            "../POKEMON/charmander_back2.gif",
            "../POKEMON/charmander_front2.gif",
            ElementType.FIRE
        );
    }

    public static Pokemon createSquirtle() {
        return new Pokemon(
            "Squirtle",
            144,
            43,
            48, 
            createMove("Water Gun", 40, ElementType.WATER),
            createMove("Tackle", 40, ElementType.NORMAL),
            "../POKEMON/squirtle_back2.gif",
            "../POKEMON/squirtle_front2.gif",
            ElementType.WATER
        );
    }

    public static Pokemon createBulbasaur() {
        return new Pokemon(
            "Bulbasaur",
            145,
            45,
            49, 
            createMove("Vine Whip", 45, ElementType.GRASS),
            createMove("Tackle", 40, ElementType.NORMAL),
            "../POKEMON/bulbasaur_back2.gif",
            "../POKEMON/bulbasaur_front2.gif",
            ElementType.GRASS
        );
    }

    // Johto Starter
    public static Pokemon createCyndaquil() {
        return new Pokemon(
            "Cyndaquil",
            139,
            65,
            52,
            createMove("Ember", 40, ElementType.FIRE),
            createMove("Tackle", 35, ElementType.NORMAL),
            "../POKEMON/cyndaquil_back2.gif",
            "../POKEMON/cyndaquil_front2.gif",
            ElementType.FIRE
        );
    }

    public static Pokemon createTotodile() {
        return new Pokemon(
            "Totodile",
            150,
            43,
            65,
            createMove("Water Gun", 40, ElementType.WATER),
            createMove("Tackle", 35, ElementType.NORMAL),
            "../POKEMON/totodile_back2.gif",
            "../POKEMON/totodile_front2.gif",
            ElementType.WATER
        );
    }

    public static Pokemon createChikorita() {
        return new Pokemon(
            "Chikorita",
            145,
            45,
            49,
            createMove("Leafage", 40, ElementType.GRASS),
            createMove("Tackle", 35, ElementType.NORMAL),
            "../POKEMON/chikorita_back2.gif",
            "../POKEMON/chikorita_front2.gif",
            ElementType.GRASS
        );
    }

    // Special 
    public static Pokemon createPikachu() {
        return new Pokemon(
            "Pikachu",
            135,
            90,
            55,
            createMove("Quick Attack", 40, ElementType.NORMAL),
            createMove("Thunderbolt", 90, ElementType.ELECTRIC),
            "../POKEMON/pikachu_back2.gif",
            "../POKEMON/pikachu_front2.gif", 
            ElementType.ELECTRIC
        );
    }

    public static Pokemon createEevee() {
        return new Pokemon(
            "Eevee",
            155,
            55,
            55,
            createMove("Quick Attack", 40, ElementType.NORMAL),
            createMove("Take Down", 90, ElementType.NORMAL),
            "../POKEMON/eevee_back2.gif",
            "../POKEMON/eevee_front2.gif", 
            ElementType.NORMAL
        );
    }
    
    // Fire
    public static Pokemon createCamerupt() {
        return new Pokemon(
            "Camerupt",
            170,
            40,
            100,
            createMove("Tackle", 35, ElementType.NORMAL),
            createMove("Ember", 35, ElementType.FIRE),
            "../POKEMON/camerupt_back2.gif",
            "../POKEMON/camerupt_front2.gif",
            ElementType.FIRE
        );
    }
    public static Pokemon createVulpix() {
        return new Pokemon(
            "Vulpix",
            138,
            65,
            41,
            createMove("Ember", 40, ElementType.FIRE),
            createMove("Quick Attack", 40, ElementType.NORMAL),
            "../POKEMON/vulpix_back2.gif",
            "../POKEMON/vulpix_front2.gif",
            ElementType.FIRE
        );
    }
    public static Pokemon createSlugma() {
        return new Pokemon(
            "Slugma",
            140,
            20,
            40,
            createMove("Ember", 40, ElementType.FIRE),
            createMove("Rock Throw", 50, ElementType.ROCK),
            "../POKEMON/slugma_back2.gif",
            "../POKEMON/slugma_front2.gif",
            ElementType.FIRE
        );
    }

    // Grass
    public static Pokemon createSwadloon() {
        return new Pokemon(
            "Swadloon",
            155,
            42,
            42,
            createMove("Tackle", 40, ElementType.NORMAL),
            createMove("Razor Leaf", 50, ElementType.GRASS),
            "../POKEMON/swadloon_back2.gif",
            "../POKEMON/swadloon_front2.gif",
            ElementType.GRASS
        );
    }
    public static Pokemon createTropius() {
        return new Pokemon(
            "Tropius",
            199,
            51,
            68,
            createMove("Stomp", 40, ElementType.NORMAL),
            createMove("Razor Leaf", 50, ElementType.GRASS),
            "../POKEMON/tropius_back2.gif",
            "../POKEMON/tropius_front2.gif",
            ElementType.GRASS
        );
    }    

    // Water
    public static Pokemon createMagikarp() {
        return new Pokemon(
            "Magikarp",
            120,
            80,
            10,
            createMove("Splash", 0, ElementType.WATER),
            createMove("Hydro Pump", 110, ElementType.WATER),
            "../POKEMON/magikarp_back2.gif",
            "../POKEMON/magikarp_front2.gif",
            ElementType.WATER
        );
    }
    public static Pokemon createMantine() {
        return new Pokemon(
            "Mantine",
            185,
            70,
            40,
            createMove("Bubble", 20, ElementType.WATER),
            createMove("BubbleBeam", 65, ElementType.WATER),
            "../POKEMON/mantine_back2.gif",
            "../POKEMON/mantine_front2.gif",
            ElementType.WATER
        );
    }
    public static Pokemon createMarill() {
        return new Pokemon(
            "Marill",
            170,
            40,
            20,
            createMove("Water Gun", 40, ElementType.WATER),
            createMove("1\tTackle", 35, ElementType.NORMAL),
            "../POKEMON/marill_back2.gif",
            "../POKEMON/marill_front2.gif",
            ElementType.WATER
        );
    }


    // Other
    public static Pokemon createChansey() {
        return new Pokemon(
            "Chansey",
            400,
            50,
            5,
            createMove("DoubleSlap", 20, ElementType.NORMAL),
            createMove("Pond", 40, ElementType.NORMAL),
            "../POKEMON/chansey_back2.gif",
            "../POKEMON/chansey_front2.gif",
            ElementType.NORMAL
        );
    }
    public static Pokemon createPidgey() {
        return new Pokemon(
            "Pidgey",
            140,
            56,
            45,
            createMove("Wing Attack", 35, ElementType.FLYING),
            createMove("Sky Attack", 50, ElementType.FLYING),
            "../POKEMON/pidgey_back2.gif",
            "../POKEMON/pidgey_front2.gif",
            ElementType.FLYING
        );
    }

    public static Pokemon createNatu() {
        return new Pokemon(
            "Natu",
            140,
            70,
            50,
            createMove("Future Sight", 40, ElementType.PSYCHIC),
            createMove("Peck", 35, ElementType.FLYING),
            "../POKEMON/natu_back2.gif",
            "../POKEMON/natu_front2.gif",
            ElementType.PSYCHIC
        );
    }

        public static Pokemon createDratini() {
        return new Pokemon(
            "Dratini",
            141,
            50,
            64,
            createMove("Twister", 40, ElementType.DRAGON),
            createMove("Dragon Rush", 100, ElementType.DRAGON),
            "../POKEMON/natu_back2.gif",
            "../POKEMON/natu_front2.gif",
            ElementType.DRAGON
        );
    }
    public static Pokemon createZangoose() {
        return new Pokemon(
            "Zangoose",
            173,
            90,
            100,
            createMove("Scratch", 40, ElementType.NORMAL),
            createMove("Water Pulse", 60, ElementType.WATER),
            "../POKEMON/zangoose_back2.gif",
            "../POKEMON/zangoose_front2.gif",
            ElementType.NORMAL
        );
    }


    // FUN!
    public static Pokemon createRegice() {
        return new Pokemon(
            "Regice",
            500,
            60,
            40,
            createMove("Icy Wind", 55, ElementType.ICE),
            createMove("Ice Beam", 95, ElementType.ICE),
            "../POKEMON/regice_back2.gif",
            "../POKEMON/regice_front2.gif",
            ElementType.ICE
        );
    }

    public static Pokemon createLatias() {
        return new Pokemon(
            "Latias",
            400,
            110,
            50,
            createMove("DragonBreath", 60, ElementType.DRAGON),
            createMove("Fly", 70, ElementType.FLYING),
            "../POKEMON/latias_back2.gif",
            "../POKEMON/latias_front2.gif",
            ElementType.DRAGON
        );
    }

    // Generator Random Wild Pokemon
    public static Pokemon createRandomWild() {
        class PokemonWeight {
            Pokemon pokemon;
            double weight;
            PokemonWeight(Pokemon p, double w) { pokemon = p; weight = w; }
        }

        PokemonWeight[] wildPokemons = {
            // Starter (jarang)
            new PokemonWeight(createCharmander(), 0.02),
            new PokemonWeight(createSquirtle(), 0.02),
            new PokemonWeight(createBulbasaur(), 0.02),
            new PokemonWeight(createCyndaquil(), 0.02),
            new PokemonWeight(createTotodile(), 0.02),
            new PokemonWeight(createChikorita(), 0.02),

            // Special
            new PokemonWeight(createPikachu(), 0.05),
            new PokemonWeight(createEevee(), 0.05),

            // Fire
            new PokemonWeight(createCamerupt(), 0.07),
            new PokemonWeight(createVulpix(), 0.07),
            new PokemonWeight(createSlugma(), 0.06),

            // Grass
            new PokemonWeight(createSwadloon(), 0.08),
            new PokemonWeight(createTropius(), 0.07),

            // Water
            new PokemonWeight(createMagikarp(), 0.08),
            new PokemonWeight(createMantine(), 0.07),
            new PokemonWeight(createMarill(), 0.07),

            // Other
            new PokemonWeight(createChansey(), 0.05),
            new PokemonWeight(createPidgey(), 0.05),
            new PokemonWeight(createNatu(), 0.05),
            new PokemonWeight(createDratini(), 0.05),
            new PokemonWeight(createZangoose(), 0.05),

            // FUN / Boss
            new PokemonWeight(createRegice(), 0.005),
            new PokemonWeight(createLatias(), 0.005)
        };

        double totalWeight = 0;
        for (PokemonWeight pw : wildPokemons) totalWeight += pw.weight;

        double r = Math.random() * totalWeight;
        for (PokemonWeight pw : wildPokemons) {
            r -= pw.weight;
            if (r <= 0) return pw.pokemon;
        }
        return wildPokemons[wildPokemons.length-1].pokemon; // fallback
    }
}