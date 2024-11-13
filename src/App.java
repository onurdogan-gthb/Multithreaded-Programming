import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class App {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            // some fancy colors
            final String ANSI_YELLOW = "\u001B[33m";
            final String ANSI_RESET = "\u001B[0m";
            final String ANSI_GREEN = "\u001B[32m";
            final String ANSI_PURPLE = "\u001B[35m";
            final String ANSI_CYAN = "\u001B[36m";
            final String ANSI_BLUE = "\u001B[34m";
            System.out.println();
            System.out.println();
            System.out.println();
            System.out
                    .println(ANSI_CYAN + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "*   " + ANSI_BLUE
                    + "T   H   R   E   A   D   -   H   O   P   P   E   R   S   " + ANSI_CYAN + "*" + ANSI_RESET);
            System.out
                    .println(ANSI_CYAN + "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *" + ANSI_RESET);
            System.out.println();
            System.out.print(ANSI_YELLOW + "R A B B I T S   =   " + ANSI_RESET);
            int numRabbits = scanner.nextInt();
            System.out.print(ANSI_YELLOW + "B O X E S   =   " + ANSI_RESET);
            int numBoxes = scanner.nextInt();
            System.out.print(ANSI_YELLOW + "S E E D I N G   =   " + ANSI_RESET);
            int seedRate = scanner.nextInt();
            System.out.print(ANSI_YELLOW + "S C O R C H I N G   =   " + ANSI_RESET);
            int scorchRate = scanner.nextInt();
            System.out.print(ANSI_YELLOW + "N A P P I N G   =   " + ANSI_RESET);
            int sleepTime = scanner.nextInt();
            System.out.println();

            CarrotField carrotField = new CarrotField(numBoxes, seedRate, scorchRate);
            Rabbit[] rabbits = new Rabbit[numRabbits];
            Thread[] threads = new Thread[numRabbits];

            for (int i = 0; i < numRabbits; i++) {
                rabbits[i] = new Rabbit(sleepTime, carrotField);
                threads[i] = new Thread(rabbits[i]);
                threads[i].start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println();
            for (Rabbit rabbit : rabbits) {

                System.out.println(
                        ANSI_PURPLE + ".*. " + ANSI_GREEN + rabbit.getName() + ANSI_RESET
                                + " their majesties finished the odyssey with "
                                + ANSI_CYAN + rabbit.getCarrots() + ANSI_RESET + " carrots... " + ANSI_PURPLE + ".*."
                                + ANSI_RESET);
            }
            System.out.println();
            System.out.println(ANSI_YELLOW + "The threads of time are joined now..." + ANSI_RESET);
        }
    }
}

class Box {
    private boolean hasCarrot = false;

    public boolean takeCarrot() {
        if (hasCarrot) {
            hasCarrot = false;
            return true;
        } else {
            return false;
        }
    }

    public void placeCarrot() {
        hasCarrot = true;
    }

    public void removeCarrot() {
        hasCarrot = false;
    }

    public boolean getCarrot() {
        return hasCarrot;
    }
}

class CarrotField {
    private Box[] boxes;
    private Random random = new Random();
    private Timer timer = new Timer();

    private int size = 0;
    private int seedRate = 0;
    private int scorchRate = 0;

    public CarrotField(int size, int seedRate, int scorchRate) {
        this.size = size;
        this.seedRate = seedRate;
        this.scorchRate = scorchRate;
        boxes = new Box[this.size];
        for (int i = 0; i < this.size; i++) {
            boxes[i] = new Box();
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                placeCarrotRandomly();
            }
        }, 0, this.seedRate);
    }

    private void placeCarrotRandomly() {
        int index = random.nextInt(boxes.length);
        boxes[index].placeCarrot();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                boxes[index].removeCarrot();
            }
        }, scorchRate);
    }

    public Box getBox(int index) {
        return boxes[index];
    }

    public int getSize() {
        return size;
    }
}

class Rabbit implements Runnable {
    private String name = "Rabbit";
    private int zzz = 0;
    private int location = 0;
    private int carrots = 0;
    private CarrotField carrotField;

    public Rabbit(int zzz, CarrotField carrotField) {
        this.name = generateName();
        this.zzz = zzz;
        this.location = 0;
        this.carrots = 0;
        this.carrotField = carrotField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZzz() {
        return zzz;
    }

    public void setZzz(int zzz) {
        this.zzz = zzz;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getCarrots() {
        return carrots;
    }

    public void setCarrots(int carrots) {
        this.carrots = carrots;
    }

    private String generateName() {
        String[] nouns = {
                "Bunny", "Rabbit", "Hare", "Cottontail", "Thumper",
                "Fluffball", "Nibbler", "Whiskers", "Flopsy", "Mopsy",
                "Cotton", "Paws", "Luna", "Binky", "Niblet",
                "Snugglepuff", "Fuzzywig", "Hopper", "Bouncington", "Twinkletoes",
                "Scooter", "Charmington", "Snugglebug", "Noodle", "Bumblebee",
                "Chuckles", "Anti-Marshmallow", "Pippin", "Anti-Muffin", "Anti-Snickerdoodle",
                "Snugglemuffin", "Anti-Cupcake", "Snickers", "Jellybean", "Bubbles",
                "Puddle", "Anti-Peaches", "Blossom", "Raindrop", "Lollipop",
                "Starlight", "Sunshine", "Moonbeam", "Anti-Pumpkin", "Peachy",
                "Honeybun", "Butterscotch", "Daisy", "Cinnamon", "Mango",
                "Peaches", "Sugarplum", "Gingersnap", "Blueberry", "Peanut",
                "Toffee", "Buttercup", "Pumpkin", "Pecan", "Anti-Cheesecake",
                "Marshmallow", "Maple", "Pancake", "Caramel", "Snickerdoodle",
                "Biscuit", "Scone", "Tartlet", "Crumpet", "Muffin",
                "Popover", "Soufflé", "Eclair", "Truffle", "Macaroon",
                "Cupcake", "Praline", "Bonbon", "Brownie", "Cheesecake",
                "Chiffon", "Marzipan", "Tiramisu", "Cannoli", "CremeBrulee",
                "Pavlova", "Sorbet", "Sherbet", "Gelato", "Granita",
                "Parfait", "Mousse", "Sundae", "Banoffee", "Pudding",
                "Ambrosia", "Nougat", "Fondant", "Frappe", "Smoothie"
        };
        String[] adjectives = {
                "World-Eater", "Dominator", "Cosmic Explorer", "Infinity Hopper", "Galactic Jumper",
                "Whiskered Wizard", "Time Traveler", "Lunar Lepus", "Quantum Bouncer", "Dimensional Dynamo",
                "Fluffinator", "Stellar Bouncer", "Nebula Nomad", "Celestial Dynamo", "Epic Hopper",
                "Space-Time Trailblazer", "Starburst Binky", "Plasma Paws", "Nebula Nibbler", "Time-Hop Tycoon",
                "Pandimensional Prancer", "Fuzzy Phenomenon", "Supernova Snuggler", "Gravity Groover",
                "Zigzag Zephyr",
                "Whiskered Wonder", "Photon Pouncer", "Infinite Imp", "Astro Acrobat", "Vortex Varmint",
                "Quantum Quasar", "Astronautic Anomaly", "Spectral Skipper", "Supersonic Stardust",
                "Astrodynamic Acrobat",
                "Furry Fusionist", "Quantum Quandary", "Astral Artisan", "Mystical Maverick", "Temporal Trickster",
                "Celestial Cuddler", "Peculiar Photon", "Galactic Giggle", "Stardust Serenade", "Spherical Skipper",
                "Cynosure Cottontail", "Enigmatic Explorer", "Dimensional Dancer", "Warp-Drive Whiskers",
                "Plutonian Prankster",
                "Radiant Riddler", "Galaxy Giggler", "Universal Uproar", "Cosmic Conundrum", "Photon Phantasm",
                "Interstellar Imp", "Quantum Quirk", "Astro Aviator", "Lunar Luminary", "Space-Time Serenader",
                "Nebula Nomad", "Solar Squirrel", "Stellar Sprite", "Galactic Gambler", "Astral Acrobat",
                "Celestial Cyclone", "Mystical Mischief", "Stellar Storyteller", "Quantum Quester",
                "Astro Arcanist",
                "Plasma Pixie", "Gravitational Gambler", "Astral Alchemist", "Ethereal Enigma",
                "Futuristic Fuzzball",
                "Celestial Conjurer", "Supernova Squire", "Whiskered Warlock", "Dimensional Dervish",
                "Nebula Nomad",
                "Spectacular Spritzer", "Luminous Lark", "Quantum Quipster", "Cosmic Charmer", "Eccentric Emissary",
                "Supersonic Stargazer", "Temporal Trickster", "Celestial Caprice", "Quantum Quandary",
                "Astral Assembler",
                "Lunar Luminary", "Spectral Sprite", "Interstellar Instigator", "Quantum Quandary",
                "Stardust Sorcerer"
        };

        Random random = new Random();
        String randomNoun = nouns[random.nextInt(nouns.length)];
        String randomAdjective = adjectives[random.nextInt(adjectives.length)];

        return randomNoun + " the " + randomAdjective;

        // using a hash set to store the used names could also be done but i think it
        // would be overkill at this point
    }

    private static final Object lock = new Object();

    @Override
    public void run() {
        // some fancy colors here too
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_PURPLE = "\u001B[35m";
        for (int i = 0; i < carrotField.getSize(); i++) {
            if (carrotField.getBox(location).getCarrot()) {
                synchronized (lock) {
                    carrotField.getBox(location).takeCarrot();
                    carrots++;
                    System.out.println(ANSI_GREEN + name + ANSI_RESET + " has rightfully " + ANSI_PURPLE + "devoured"
                            + ANSI_RESET + " the carrot at "
                            + ANSI_RED + location + ANSI_RESET);
                }
            }
            try {
                Thread.sleep(zzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                location++;
                System.out.println(ANSI_GREEN + name + ANSI_RESET + " has gracefully jumped to " + ANSI_RED + location
                        + ANSI_RESET);
            }
        }

    }
}
